package me.hii488.volcanoRush;

import me.hii488.controllers.GameController;
import me.hii488.misc.Settings;
import me.hii488.volcanoRush.misc.Score;

public class VolcRush {
	
	public static Score score = new Score();
	
	public static void main(String[] args){
		GameController.setupEngine();
		GameController.loadWindow("Volcano Rush", 1000, 800);
		
		Settings.Texture.tileSize = 32;
		Settings.Logging.debug = true;
		
		Initilisation.setup();
		
		GameController.startGame();
	}
	
	
}
