package me.hii488.volcanoRush.objects;

public enum OreType {
	NONE(0),
	COPPER(100),
	IRON(250),
	SILVER(500),
	GOLD(1000),
	DIAMOND(2500);
	
	public int score;
	
	OreType(int value){
		this.score = value;
	}
}
