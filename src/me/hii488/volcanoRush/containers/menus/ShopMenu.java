package me.hii488.volcanoRush.containers.menus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import me.hii488.controllers.GameController;
import me.hii488.graphics.GUI.GUI;
import me.hii488.graphics.GUI.GUILabel;
import me.hii488.graphics.GUI.GUIPicture;
import me.hii488.handlers.ContainerHandler;
import me.hii488.objects.containers.BaseContainer;
import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.items.Item;
import me.hii488.volcanoRush.items.ItemList;
import me.hii488.volcanoRush.objects.entities.VRPlayer;

public class ShopMenu extends BaseContainer {
	
	ArrayList<GUIPicture> itemPictures = new ArrayList<GUIPicture>();
	final int maxSpend = 500;
	int currentFunds = 500;
	
	public ShopMenu(){
		grid.setupGrid(10, 10);
		this.identifier = "shop";
		
		GUI shop = new GUI();
		
		HashMap<Item, Boolean> itemList = ItemList.getItemList();
		
		int row = 0, col = 0;
		for(Item i : itemList.keySet()) {
			GUIPicture item = (GUIPicture) new GUIPicture() {
				@Override
				public void onClick(MouseEvent e) {
					if(!itemList.get(i)) {
						if(currentFunds - i.cost > 0) {
							itemList.put(i, true);
							currentFunds -= i.cost;
							this.textureName = "bought.png";
							this.setupTextures();
						}
					}
					else {
						currentFunds += i.cost;
						itemList.put(i, false);
						this.textureName = i.identifier + ".png";
						this.setupTextures();
					}
				}
			}.setStates(0).setTextureName(i.identifier + ".png").setDimensions(64, 64).setIdentifier(i.identifier + "Picture").setPosition(col * 70 + 20, row * 70 + 120);
			
			item.setupTextures();
			itemPictures.add(item);
			
			shop.addElement(item);
			
			//TODO Check this is vaguely correct once enough items have been added.
			col++;
			if(col > 6) {
				col = 0;
				row++;
			}
		}
		
		GUILabel playButton = (GUILabel) new GUILabel(){
			@Override
			public void onClick(MouseEvent e){
				if(e.getButton() != MouseEvent.BUTTON1) return;
				ContainerHandler.loadNewContainer("standardVolcano"); // TODO: Change this to allow for multiple volcanoes
			}
		}.setFill(false).setTextColor(Color.white).setOutlineColor(Color.white).setText("Start Game")
				.setDimensions(70, 30).setPosition(GameController.windows[0].width/2+15, 700);
		
		shop.addElement(playButton);
		
		guis.add(shop);
		
	}
	
	@Override
	public void onLoad(){
		super.onLoad();
		currentFunds = maxSpend;
		((VRPlayer) EntityRegistry.player).resetPlayer();
	}
	
	public void render(Graphics g) {
		super.render(g);
		
		
		
	}
	
}
