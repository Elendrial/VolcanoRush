package me.hii488.volcanoRush;

import java.awt.Color;
import java.awt.event.MouseEvent;

import me.hii488.controllers.GameController;
import me.hii488.controllers.InitilisationController;
import me.hii488.graphics.GUI.GUI;
import me.hii488.graphics.GUI.GUILabel;
import me.hii488.handlers.ContainerHandler;
import me.hii488.interfaces.IInitiliser;
import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.containers.menus.MainMenu;
import me.hii488.volcanoRush.containers.volcanoes.StandardVolcano;
import me.hii488.volcanoRush.objects.entities.FallingDirt;
import me.hii488.volcanoRush.objects.entities.MineralItem;
import me.hii488.volcanoRush.objects.entities.VRPlayer;
import me.hii488.volcanoRush.objects.tiles.AirTile;
import me.hii488.volcanoRush.objects.tiles.DirtTile;
import me.hii488.volcanoRush.objects.tiles.UnbreakableTile;

public class Initilisation implements IInitiliser{
	
	public static Initilisation instance = new Initilisation();
	
	public static void setup(){
		InitilisationController.initList.add(instance);
	}

	public static MainMenu menuContainer = new MainMenu();
	public static StandardVolcano standardVolc = new StandardVolcano();
	
	@Override
	public void preInit() {
		ContainerHandler.addContainer(menuContainer);
		ContainerHandler.addContainer(standardVolc);
		
		new DirtTile();
		new AirTile();
		new UnbreakableTile();
		
		new MineralItem();
		new FallingDirt();
		
		EntityRegistry.player = new VRPlayer();
	}

	@Override
	public void init() {
		ContainerHandler.loadNewContainer(menuContainer);
	}
	

	@Override
	public void postInit() {
		GUI main = new GUI();
		GUILabel startButton = (GUILabel) new GUILabel(){
			@Override
			public void onClick(MouseEvent e){
				if(e.getButton() != MouseEvent.BUTTON1) return;
				ContainerHandler.loadNewContainer(standardVolc);			
			}
		}.setFill(false).setTextColor(Color.white).setOutlineColor(Color.white).setText("Start Game").setDimensions(100, 40).setPosition(GameController.windows[0].width/2-50, GameController.windows[0].height/2-20);
		
		main.addElement(startButton);
		menuContainer.guis.add(main);
		
	}
	
}
