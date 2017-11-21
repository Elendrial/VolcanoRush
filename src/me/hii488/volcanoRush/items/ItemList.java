package me.hii488.volcanoRush.items;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import me.hii488.handlers.TextureHandler;
import me.hii488.interfaces.IInputUser;
import me.hii488.interfaces.ITickable;
import me.hii488.volcanoRush.dataTypes.DeathCause;
import me.hii488.volcanoRush.dataTypes.FluidType;

public class ItemList implements IInputUser, ITickable{
	
	// The bool is whether the player already has it or not.
	protected static HashMap<Item, Boolean> itemList = new HashMap<Item, Boolean>();
	protected static HashMap<String, BufferedImage> textureList = new HashMap<String, BufferedImage>();
	
	// Don't use this to edit the list, only iterate through it.
	public static HashMap<Item, Boolean> getItemList() {
		return itemList;
	}
	
	public static void registerItem(Item i){
		itemList.put(i, false);
		textureList.put(i.identifier, TextureHandler.loadTexture("textures/items/", i.identifier + ".png", i));
	}
	
	public static void registerItems(){
		registerItem(new ItemScoreDouble());
		registerItem(new ItemScoreTriple());
		registerItem(new ItemGasMask());
		registerItem(new ItemHardHat());
		registerItem(new ItemDynamite());
	}

	public static BufferedImage getTexture(Item i) {
		return textureList.get(i.identifier);
	}
	
	public static BufferedImage getTexture(String s) {
		return textureList.get(s);
	}
	
	public static void unequipAll(){
		for(Item i : itemList.keySet()) itemList.put(i, false);
	}
	
	public static float getItemMultiplier(){
		float mult = 1;
		for(Item i : itemList.keySet()){
			if(itemList.get(i)) mult *= i.scoreMultiplier;
		}
		return mult;
	}
	
	public static void inFluid(FluidType f){
		for(Item i : itemList.keySet()){
			if(itemList.get(i)) i.inFluid(f);
		}
	}
	
	public static boolean onDeath(DeathCause cause){
		boolean b = true;
		for(Item i : itemList.keySet()){
			if(itemList.get(i)){
				b = i.onDeath(cause);
				if(!b) return b;
			}
		}
		return true;
	}

	public void updateOnTick(){for(Item i : itemList.keySet()){if(itemList.get(i)) i.updateOnTick();}}
	public void updateOnSec() {for(Item i : itemList.keySet()){if(itemList.get(i)) i.updateOnSec();}}	

	public void keyPressed(KeyEvent arg0)     {for(Item i : itemList.keySet()) if(itemList.get(i)) i.keyPressed(arg0);}
	public void keyReleased(KeyEvent arg0)    {for(Item i : itemList.keySet()) if(itemList.get(i)) i.keyReleased(arg0);}
	public void keyTyped(KeyEvent arg0)       {for(Item i : itemList.keySet()) if(itemList.get(i)) i.keyTyped(arg0);}
	public void mouseClicked(MouseEvent arg0) {for(Item i : itemList.keySet()) if(itemList.get(i)) i.mouseClicked(arg0);}
	public void mouseEntered(MouseEvent arg0) {for(Item i : itemList.keySet()) if(itemList.get(i)) i.mouseEntered(arg0);}
	public void mouseExited(MouseEvent arg0)  {for(Item i : itemList.keySet()) if(itemList.get(i)) i.mouseExited(arg0);}
	public void mousePressed(MouseEvent arg0) {for(Item i : itemList.keySet()) if(itemList.get(i)) i.mousePressed(arg0);}
	public void mouseReleased(MouseEvent arg0){for(Item i : itemList.keySet()) if(itemList.get(i)) i.mouseReleased(arg0);}
	
	public float randTickChance() {return 0;}
	public void updateOnRandTick() {}
}
