package me.hii488.volcanoRush.containers.volcanoes;

import java.awt.Color;
import java.awt.event.MouseEvent;

import me.hii488.controllers.GameController;
import me.hii488.graphics.GUI.GUI;
import me.hii488.graphics.GUI.GUILabel;
import me.hii488.handlers.ContainerHandler;
import me.hii488.misc.Settings;
import me.hii488.objects.containers.BaseContainer;
import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.additionalTickers.LightHandler;
import me.hii488.volcanoRush.containers.algorithms.MineralAlg;
import me.hii488.volcanoRush.items.ItemList;
import me.hii488.volcanoRush.objects.entities.VRPlayer;

public abstract class Volcano extends BaseContainer{
	public MineralAlg mineralSpawner;
	
	public Volcano(){
		super();
		
		GUI pauseMenu = new GUI().setIdentifier("pauseMenu");

		GUILabel continueButton = (GUILabel) new GUILabel(){
			@Override
			public void onClick(MouseEvent e){
				if(e.getButton() != MouseEvent.BUTTON1) return;
				GameController.isPaused = false;
				pauseMenu.hideAll();
			}
		}.setFill(false).setTextColor(Color.white).setOutlineColor(Color.white).setText("Continue")
				.setDimensions(70, 30).setPosition(GameController.windows[0].width/2-35, 300);
		
		GUILabel menuButton = (GUILabel) new GUILabel(){
			@Override
			public void onClick(MouseEvent e){
				if(e.getButton() != MouseEvent.BUTTON1) return;
				ContainerHandler.loadNewContainer("mainmenu");
			}
		}.setFill(false).setTextColor(Color.white).setOutlineColor(Color.white).setText("To Menu")
				.setDimensions(70, 30).setPosition(GameController.windows[0].width/2-35, 400);
		
		GUILabel backgroundShade = (GUILabel) new GUILabel().setFill(true).setOutlineColor(new Color(0f,0f,0f,0.5f))
				.setPosition(0, 0).setDimensions(GameController.windows[0].width, GameController.windows[0].height);
		
		pauseMenu.addElement(backgroundShade);
		pauseMenu.addElement(continueButton);
		pauseMenu.addElement(menuButton);
		pauseMenu.hideAll();
		
		guis.add(pauseMenu);
		
	}
	
	public void onLoad(){
		super.onLoad();
		((VRPlayer) EntityRegistry.player).movementAllowed = true;
		EntityRegistry.player.position.setLocation(grid.dimensions.getX()/2 * Settings.Texture.tileSize, 60);
		ItemList.doEquips();
		mineralSpawner.populate(grid);
		LightHandler.setInitialLight();
	}
	
}
