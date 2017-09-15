package me.hii488.volcanoRush;

import me.hii488.controllers.GameController;
import me.hii488.misc.Settings;

public class VolcRush {
	
	public static void main(String[] args){
		GameController.setupEngine();
		GameController.loadWindow("Volcano Rush", 1000, 800);
		
		Settings.Texture.tileSize = 32;
		
		Initilisation.setup();
		
		GameController.startGame();
	}
	
	
}
