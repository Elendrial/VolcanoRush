package me.hii488.volcanoRush.fluids;

import me.hii488.objects.tiles.BaseTile;

public class FluidWater extends Fluid{

	public FluidWater(){
		this.identifier = "water";
		this.flowDir = FlowDirection.DOWN;
	}

	public void updateOnTick(int x, int y, int level) {}
	public void updateOnSec(int x, int y, int level) {}
	public void onEnterTile(BaseTile t) {}
	public void onLeaveTile(BaseTile t) {}
	
}
