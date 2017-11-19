package me.hii488.volcanoRush.items;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import me.hii488.handlers.TextureHandler;
import me.hii488.volcanoRush.tileExtras.FluidType;

public class ItemList {
	
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
	
	
}
