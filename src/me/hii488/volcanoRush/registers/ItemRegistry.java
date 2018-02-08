package me.hii488.volcanoRush.registers;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import me.hii488.handlers.TextureHandler;
import me.hii488.interfaces.IInputUser;
import me.hii488.interfaces.ITickable;
import me.hii488.volcanoRush.dataTypes.DeathCause;
import me.hii488.volcanoRush.fluids.Fluid;
import me.hii488.volcanoRush.items.Item;
import me.hii488.volcanoRush.items.ItemAirTank;
import me.hii488.volcanoRush.items.ItemCharge;
import me.hii488.volcanoRush.items.ItemDynamite;
import me.hii488.volcanoRush.items.ItemGasMask;
import me.hii488.volcanoRush.items.ItemHardHat;
import me.hii488.volcanoRush.items.ItemScoreDouble;
import me.hii488.volcanoRush.items.ItemScoreTriple;

public class ItemRegistry implements IInputUser, ITickable{
	
	// The bool is whether the player already has it or not.
	protected static HashMap<Item, Boolean> itemList = new HashMap<Item, Boolean>();
	protected static HashMap<String, Item> identifierList = new HashMap<String, Item>();
	
	// Don't use this to edit the list, only iterate through it.
	public static HashMap<Item, Boolean> getItemList() {
		return itemList;
	}
	
	public static void registerItem(Item i){
		itemList.put(i, false);
		identifierList.put(i.identifier, i);
		TextureHandler.loadTexture("textures/items/", i.identifier + ".png", i, "item_" + i.identifier + "_0");
	}
	
	public static void registerItems(){
		registerItem(new ItemScoreDouble());
		registerItem(new ItemScoreTriple());
		registerItem(new ItemGasMask());
		registerItem(new ItemHardHat());
		registerItem(new ItemDynamite());
		registerItem(new ItemAirTank());
		registerItem(new ItemCharge());
	}

	public static BufferedImage getTexture(Item i) {
		return TextureHandler.getTexture("item_" + i.identifier);
	}
	
	public static BufferedImage getTexture(String s) {
		return TextureHandler.getTexture("item_" + s);
	}
	
	public static void unequipAll(){
		for(Item i : itemList.keySet()) itemList.put(i, false);
	}
	
	public static void equipItem(String identifier) {
		itemList.put(identifierList.get(identifier), true);
		identifierList.get(identifier).onEquip();
	}
	
	public static void equipItem(Item i) {
		itemList.put(identifierList.get(i.identifier), true); //ensures it is the item already in the list that is equipped.
		identifierList.get(i.identifier).onEquip();
	}
	
	public static void unequipItem(Item i) {
		itemList.put(identifierList.get(i.identifier), false);
	}
	
	public static void unequipItem(String s) {
		itemList.put(identifierList.get(s), false);
	}
	
	// This should only be invoked once, by the Volcano as it loads, to equip items bought in the shop.
	public static void doEquips() {
		for(Item i : itemList.keySet()){
			if(itemList.get(i)) i.onEquip();
		}
	}
	
	public static float getItemMultiplier(){
		float mult = 1;
		for(Item i : itemList.keySet()){
			if(itemList.get(i)) mult *= i.scoreMultiplier;
		}
		return mult;
	}
	
	public static void inFluid(Fluid f){
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
