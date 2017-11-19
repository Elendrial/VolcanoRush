package me.hii488.volcanoRush.tileExtras;

public enum FluidType {
	GAS(-1),
	WATER(1);
	
	public int flowDir;
	
	FluidType(int flowDir){
		this.flowDir = flowDir;
	}
}
