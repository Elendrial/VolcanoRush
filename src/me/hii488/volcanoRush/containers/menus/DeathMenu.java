package me.hii488.volcanoRush.containers.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

import me.hii488.controllers.GameController;
import me.hii488.graphics.GUI.GUI;
import me.hii488.graphics.GUI.GUIInputBox;
import me.hii488.graphics.GUI.GUILabel;
import me.hii488.handlers.ContainerHandler;
import me.hii488.objects.containers.BaseContainer;
import me.hii488.registries.EntityRegistry;
import me.hii488.saveSystem.FileIO;
import me.hii488.volcanoRush.VolcRush;
import me.hii488.volcanoRush.misc.Score;
import me.hii488.volcanoRush.objects.entities.VRPlayer;

public class DeathMenu extends BaseContainer{
	
	public ArrayList<Score> scores;
	
	public GUIInputBox nameBox = new GUIInputBox();
	public GUILabel pastScores = new GUILabel(), currentScore = new GUILabel();
	public GUILabel menuButton, playButton;
	public GUI gui = new GUI();
	
	public DeathMenu(){
		this.identifier = "deathMenu";
		this.showEntities = false;
		grid.setupGrid(10, 10);
		
		gui.addElement(new GUILabel().setTextColor(Color.WHITE).setFont(Font.decode(Font.MONOSPACED + "-24")).setIdentifier("title")
							.setText("High Scores:").setDimensions(100, 40).setPosition(GameController.windows[0].width/2-50, 50));
		
		gui.addElement(pastScores.setTextColor(Color.WHITE).setDimensions(0,0).setPosition(GameController.windows[0].width/2-60, 120));
		gui.addElement(currentScore.setTextColor(Color.WHITE).setDimensions(200, 40).setPosition(GameController.windows[0].width/2-100, 650));
		
		menuButton = (GUILabel) new GUILabel(){
			@Override
			public void onClick(MouseEvent e){
				if(e.getButton() != MouseEvent.BUTTON1) return;
				ContainerHandler.loadNewContainer("mainmenu");
			}
		}.setFill(false).setTextColor(Color.white).setOutlineColor(Color.white).setText("Menu")
				.setDimensions(70, 30).setPosition(GameController.windows[0].width/2-85, 700);
		
		playButton = (GUILabel) new GUILabel(){
			@Override
			public void onClick(MouseEvent e){
				if(e.getButton() != MouseEvent.BUTTON1) return;
				ContainerHandler.loadNewContainer("standardVolcano"); // TODO: Change this to allow for more types of volcano.
			}
		}.setFill(false).setTextColor(Color.white).setOutlineColor(Color.white).setText("Start Game")
				.setDimensions(70, 30).setPosition(GameController.windows[0].width/2+15, 700);
		
		gui.addElement(menuButton);
		gui.addElement(playButton);
		
		guis.add(gui);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onLoad(){
		super.onLoad();
		((VRPlayer) EntityRegistry.player).movementAllowed = false;
		
		try {
			scores = (ArrayList<Score>) FileIO.deserialize("score.vr");
			pastScores.text = "";
			for(int i = 0; i < scores.size(); i++) pastScores.text += scores.get(i).name + "\t:\t" + scores.get(i).getScore() + "\n";
		} catch (Exception e) {
			System.err.println("Error loading scores.");
			e.printStackTrace();
		}
		
		currentScore.text = "Score achieved: " + VolcRush.score.getScore();
	}
	
	@Override
	public void keyTyped(KeyEvent e){
		super.keyTyped(e);
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			VolcRush.score.name = nameBox.text;
			scores.add(VolcRush.score);
			scores.sort(new Score());
			Collections.reverse(scores);
		}
	}
	
}
