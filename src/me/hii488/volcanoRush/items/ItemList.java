package me.hii488.volcanoRush.items;

import java.util.HashMap;

import me.hii488.volcanoRush.objects.FluidType;

public class ItemList {
	
	// The bool is whether the player already has it or not.
	protected static HashMap<Item, Boolean> itemList = new HashMap<Item, Boolean>();
	
	public static void registerItem(Item i){
		itemList.put(i, false);
	}
	
	public static void registerItems(){
		registerItem(new ItemScoreDouble());
		registerItem(new ItemScoreTriple());
		registerItem(new ItemGasMask());
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
