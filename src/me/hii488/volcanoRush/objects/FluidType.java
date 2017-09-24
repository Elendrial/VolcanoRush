package me.hii488.volcanoRush.objects;

public enum FluidType {
	GAS(-1),
	WATER(1);
	
	public int flowDir;
	
	FluidType(int flowDir){
		this.flowDir = flowDir;
	}
}
