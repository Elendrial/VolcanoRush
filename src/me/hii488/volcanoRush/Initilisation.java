package me.hii488.volcanoRush;

import java.awt.event.MouseEvent;

import me.hii488.controllers.InitilisationController;
import me.hii488.graphics.GUI.GUI;
import me.hii488.graphics.GUI.GUILabel;
import me.hii488.handlers.ContainerHandler;
import me.hii488.interfaces.IInitiliser;
import me.hii488.volcanoRush.containers.menus.MainMenu;

public class Initilisation implements IInitiliser{
	
	public static Initilisation instance = new Initilisation();
	
	public static void setup(){
		InitilisationController.initList.add(instance);
	}

	public static MainMenu menuContainer = new MainMenu();
	
	@Override
	public void preInit() {
		ContainerHandler.addContainer(menuContainer);
	//	ContainerHandler.addContainer(container);
	}

	@Override
	public void init() {
		ContainerHandler.loadNewContainer(menuContainer);
	}
	

	@Override
	public void postInit() {
		GUI main = new GUI();
		GUILabel startButton = new GUILabel(){
			@Override
			public void onClick(MouseEvent e){
				if(e.getButton() != MouseEvent.BUTTON1) return;
				
				
			}
		};
		
		main.addElement(startButton);
		menuContainer.guis.add(main);
		
	}
	
}
