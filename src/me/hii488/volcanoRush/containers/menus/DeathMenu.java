package me.hii488.volcanoRush.containers.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;

import me.hii488.controllers.GameController;
import me.hii488.graphics.GUI.GUI;
import me.hii488.graphics.GUI.GUIInputBox;
import me.hii488.graphics.GUI.GUILabel;
import me.hii488.objects.containers.BaseContainer;
import me.hii488.registries.EntityRegistry;
import me.hii488.saveSystem.FileIO;
import me.hii488.volcanoRush.VolcRush;
import me.hii488.volcanoRush.misc.Score;
import me.hii488.volcanoRush.objects.entities.VRPlayer;

public class DeathMenu extends BaseContainer{
	
	public ArrayList<Score> scores;
	
	public GUIInputBox nameBox = new GUIInputBox();
	public GUILabel pastScores = new GUILabel();
	public GUI gui = new GUI();
	
	public DeathMenu(){
		grid.setupGrid(10, 10);
		this.identifier = "deathMenu";
		gui.addElement(new GUILabel().setTextColor(Color.WHITE).setFont(Font.decode(Font.MONOSPACED + "-24")).setIdentifier("title")
							.setText("High Scores:").setDimensions(100, 40).setPosition(GameController.windows[0].width/2-50, 50));
		gui.addElement(pastScores.setDimensions(0,0).setPosition(GameController.windows[0].width/2, 120));
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
			for(int i = 0; i < scores.size(); i++) pastScores.text += scores.get(i).name + "\t:\t" + scores.get(i).getScore();
		} catch (Exception e) {
			System.err.println("Error loading scores.");
			e.printStackTrace();
		}
		
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
