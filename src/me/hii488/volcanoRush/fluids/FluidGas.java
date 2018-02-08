package me.hii488.volcanoRush.fluids;

import me.hii488.objects.tiles.BaseTile;

public class FluidGas extends Fluid{

	public FluidGas(){
		this.identifier = "gas";
		this.flowDir = FlowDirection.UP;
	}

	public void updateOnTick(int x, int y, int level) {}
	public void updateOnSec(int x, int y, int level) {}
	public void onEnterTile(BaseTile t) {}
	public void onLeaveTile(BaseTile t) {}

}
