package me.hii488.volcanoRush.containers.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import me.hii488.controllers.GameController;
import me.hii488.graphics.GUI.GUI;
import me.hii488.graphics.GUI.GUIInputBox;
import me.hii488.graphics.GUI.GUILabel;
import me.hii488.handlers.ContainerHandler;
import me.hii488.objects.containers.BaseContainer;
import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.VolcRush;
import me.hii488.volcanoRush.items.ItemList;
import me.hii488.volcanoRush.misc.Score;
import me.hii488.volcanoRush.objects.entities.VRPlayer;

public class DeathMenu extends BaseContainer{
	
	public ArrayList<Score> scores;
	
	public GUIInputBox nameBox = new GUIInputBox();
	public GUILabel pastScores = new GUILabel(), pastNames = new GUILabel(), currentScore = new GUILabel(), nameHere = new GUILabel();
	public GUILabel menuButton, playButton;
	public GUI gui = new GUI();
	
	public DeathMenu(){
		this.identifier = "deathMenu";
		this.showEntities = false;
		grid.setupGrid(10, 10);
		
		menuButton = (GUILabel) new GUILabel(){
			@Override
			public void onClick(MouseEvent e){
				if(e.getButton() != MouseEvent.BUTTON1) return;
				saveScore();
				ContainerHandler.loadNewContainer("mainmenu");
			}
		}.setFill(false).setTextColor(Color.white).setOutlineColor(Color.white).setText("Menu")
				.setDimensions(70, 30).setPosition(GameController.windows[0].width/2-85, 700);
		
		playButton = (GUILabel) new GUILabel(){
			@Override
			public void onClick(MouseEvent e){
				if(e.getButton() != MouseEvent.BUTTON1) return;
				saveScore();
				ContainerHandler.loadNewContainer("shop");
			}
		}.setFill(false).setTextColor(Color.white).setOutlineColor(Color.white).setText("Start Game")
				.setDimensions(70, 30).setPosition(GameController.windows[0].width/2+15, 700);
		
		
		gui.addElement(new GUILabel().setTextColor(Color.WHITE).setFont(Font.decode(Font.MONOSPACED + "-24")).setIdentifier("title")
				.setText("High Scores:").setDimensions(100, 40).setPosition(GameController.windows[0].width/2-50, 50));
		gui.addElement(pastScores.setTextColor(Color.WHITE).setVerticalJustificaton(1).setHorizontalJustification(-1).setDimensions(0,0).setPosition(GameController.windows[0].width/2+20, 130));
		gui.addElement(pastNames.setTextColor(Color.WHITE).setVerticalJustificaton(1).setHorizontalJustification(1).setDimensions(0,0).setPosition(GameController.windows[0].width/2, 130));
		gui.addElement(currentScore.setTextColor(Color.WHITE).setDimensions(200, 40).setPosition(GameController.windows[0].width/2-100, 600));
		gui.addElement(menuButton);
		gui.addElement(playButton);
		gui.addElement(nameHere.setTextColor(Color.WHITE).setHorizontalJustification(1).setText("Enter your name:").setDimensions(100, 40).setPosition(GameController.windows[0].width/2-105, 650));
		gui.addElement(nameBox.setTextColor(Color.WHITE).setHorizontalJustification(-1).setDimensions(200, 40).setPosition(GameController.windows[0].width/2 + 6, 650));
		
		guis.add(gui);
	}
	
	@Override
	public void onLoad(){
		super.onLoad();
		((VRPlayer) EntityRegistry.player).movementAllowed = false;
		nameBox.text = nameBox.text.replace("\n", "").replace("\t", "").trim();
		
		try {
			scores = Score.loadScores("scores.vr");
			pastScores.text = "";
			pastNames.text = "";
			for(int i = 0; i < scores.size() && i < 10; i++){
				pastScores.text += scores.get(i).getScore() + "\n";
				pastNames.text  += scores.get(i).name + "\t\t:\n";
			}
		} catch (Exception e) {
			System.err.println("Error loading scores.");
			e.printStackTrace();
		}
		
		VolcRush.score.multiplier *= ItemList.getItemMultiplier();
		currentScore.text = "Score achieved: " + VolcRush.score.getScore();
	}
	
	public void saveScore(){
		VolcRush.score.name = nameBox.text.replace("\n", "").replace("\t", "").trim();
		VolcRush.score.saveScore("scores.vr");
	}
}
