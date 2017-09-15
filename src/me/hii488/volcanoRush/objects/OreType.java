package me.hii488.volcanoRush.objects;

public enum OreType {
	NONE(0),
	COPPER(200),
	IRON(600),
	SILVER(1000),
	GOLD(1400),
	DIAMOND(2500);
	
	public int score;
	
	OreType(int value){
		this.score = value;
	}
}
