package me.hii488.volcanoRush.containers.volcanoes;

import java.awt.Color;
import java.awt.event.MouseEvent;

import me.hii488.controllers.GameController;
import me.hii488.graphics.GUI.GUI;
import me.hii488.graphics.GUI.GUILabel;
import me.hii488.handlers.ContainerHandler;
import me.hii488.misc.Settings;
import me.hii488.objects.containers.BaseContainer;
import me.hii488.objects.tiles.BaseTile;
import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.additionalTickers.LightHandler;
import me.hii488.volcanoRush.containers.generationAlgs.GenerationAlg;
import me.hii488.volcanoRush.dataTypes.LightSource;
import me.hii488.volcanoRush.dataTypes.Seismometer;
import me.hii488.volcanoRush.objects.entities.VRPlayer;
import me.hii488.volcanoRush.objects.tiles.AirTile;
import me.hii488.volcanoRush.registers.ItemRegistry;

public abstract class Volcano extends BaseContainer{
	private GUILabel seismomLabel;
	public LightHandler lightHandler;
	public GenerationAlg mineralSpawner;
	public Seismometer seismometer;
	public boolean erupting;
	
	public Volcano(){
		super();
		seismometer = new Seismometer();
		lightHandler = new LightHandler();
		erupting = false;
		
		GUI pauseMenu = new GUI().setIdentifier("pauseMenu");

		GUILabel continueButton = (GUILabel) new GUILabel(){
			@Override
			public void onClick(MouseEvent e){
				if(e.getButton() != MouseEvent.BUTTON1) return;
				GameController.isPaused = false;
				pauseMenu.hideAll();
			}
		}.setFill(false).setTextColor(Color.white).setOutlineColor(Color.white).setText("Continue")
				.setDimensions(70, 30).setPosition(GameController.getWindow().width/2-35, 300);
		
		GUILabel menuButton = (GUILabel) new GUILabel(){
			@Override
			public void onClick(MouseEvent e){
				if(e.getButton() != MouseEvent.BUTTON1) return;
				ContainerHandler.loadNewContainer("mainmenu");
			}
		}.setFill(false).setTextColor(Color.white).setOutlineColor(Color.white).setText("To Menu")
				.setDimensions(70, 30).setPosition(GameController.getWindow().width/2-35, 400);
		
		GUILabel backgroundShade = (GUILabel) new GUILabel().setFill(true).setOutlineColor(new Color(0f,0f,0f,0.5f))
				.setPosition(0, 0).setDimensions(GameController.getWindow().width, GameController.getWindow().height);
		
		pauseMenu.addElement(backgroundShade);
		pauseMenu.addElement(continueButton);
		pauseMenu.addElement(menuButton);
		pauseMenu.hideAll();
		
		guis.add(pauseMenu);
		
		
		GUI seismometerGUI = new GUI();
		seismomLabel = (GUILabel) new GUILabel().setHorizontalJustification(-1).setTextColor(Color.black).setIdentifier("seismometer").setPosition(5, GameController.getWindow().height - 20);
		seismometerGUI.addElement(seismomLabel);
		guis.add(seismometerGUI);
	}
	
	public void onLoad(){
		super.onLoad();
		((VRPlayer) EntityRegistry.player).movementAllowed = true;
		EntityRegistry.player.position.setLocation(grid.dimensions.getX()/2 * Settings.Texture.tileSize, 60);
		ItemRegistry.doEquips();
		mineralSpawner.populate(grid);
		
		lightHandler.setInitialLight();
		if(!lightHandler.sources.contains(EntityRegistry.player) && EntityRegistry.player instanceof LightSource) lightHandler.addSource((LightSource) EntityRegistry.player);
		
		seismometer.setCurrentActivity(0);
	}
	
	protected int eruptY;
	public void updateOnSec(){
		super.updateOnSec();
		lightHandler.updateOnSec();
		if(erupting){ 
			if(eruptY > 5){
				BaseTile t;
				for(int i = 1; i < grid.dimensions.getX()-1; i++){
					t = grid.getTile(i, eruptY);
					if(!(t instanceof AirTile)){
						grid.setTile("airTile", i, eruptY);
						t = grid.getTile(i, eruptY);
					}
					
					((AirTile) t).fluidContent.empty();
					((AirTile) t).fluidContent.put("lava", 100);
					
					if(eruptY < grid.dimensions.getY() - 13){
						t = grid.getTile(i, eruptY + 13);
						if(t instanceof AirTile){
							((AirTile) t).fluidContent.empty();
							grid.setTile("dirtTile", i, eruptY + 13);
						}
					}
				}
				
				eruptY--;
			}
		}
		else if(seismometer.isEnabled() && seismometer.getCurrentActivity() > seismometer.getMaxActivity()) erupt();
	}
	
	public void updateOnTick(){
		super.updateOnTick();
		lightHandler.updateOnTick();
		seismomLabel.setText(seismometer.getCurrentActivity() + "/" + seismometer.getMaxActivity());
	}
	
	public void erupt(){
		System.out.println("Erupting");
		erupting = true;
		eruptY = grid.dimensions.getY() - 2;
	}
	
	public boolean isErupting(){
		return erupting;
	}
	
}
