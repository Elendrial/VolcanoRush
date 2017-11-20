package me.hii488.volcanoRush.dataTypes;

public enum OreType {
	// TODO: Balance these, they're temporary.
	
	NONE(0, new OreUtil() {}),
	
	COPPER(100, new OreUtil() {
		public double getSpawnChance(int x, int y) {
			return (float) Math.exp(-y * 0.05);
		}
	}),
	
	IRON(250, new OreUtil() {
		public double getSpawnChance(int x, int y) {
			return (y <= 24 ? Math.pow(y * 0.03, 2)*2 : Math.exp(-y * 0.03)*2);
		}
	}),
	
	SILVER(500, new OreUtil() {
		public double getSpawnChance(int x, int y) {
			if(y < 20) return 0;
			return (y < 45 ? Math.pow(y * 0.02, 2) * 1.2 : Math.exp(-y*0.02) * 2.5);
		}
	}),
	
	GOLD(1000, new OreUtil() {
		public double getSpawnChance(int x, int y) {
			if(y < 40) return 0;
			return (y < 90 ? Math.pow(y * 0.01, 2) : Math.exp(-y * 0.01) * 2);
		}
	}),
	
	DIAMOND(2500, new OreUtil() {
		public double getSpawnChance(int x, int y) {
			if(y < 70) return 0;
			if(y < 100) return 0.01;
			return 0.02;
		}
	});
	
	public int score;
	public OreUtil util;
	
	OreType(int value, OreUtil info){
		this.score = value;
		this.util = info;
	}
	
}