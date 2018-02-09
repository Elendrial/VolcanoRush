package me.hii488.volcanoRush.fluids;

import me.hii488.handlers.ContainerHandler;
import me.hii488.objects.tiles.BaseTile;
import me.hii488.volcanoRush.objects.tiles.AirTile;
import me.hii488.volcanoRush.objects.tiles.MineralTile;

public class FluidLava extends Fluid{

	public FluidLava(){
		this.identifier = "lava";
		this.flowDir = FlowDirection.DOWN;
	}

	public void updateOnTick(int x, int y, int level) {
		if(level > 50 && y > 3){
			BaseTile t = ContainerHandler.getLoadedContainer().grid.getTile(x, y-1);
			if(t instanceof MineralTile){
				double lowestLight = ((MineralTile) t).lowestLight;
				ContainerHandler.getLoadedContainer().grid.setTile("airTile", x, y-1);
				((AirTile) ContainerHandler.getLoadedContainer().grid.getTile(x, y-1)).setLowestLight(lowestLight);
			}
		}
	}
	
	public void updateOnSec(int x, int y, int level) {}
	public void onEnterTile(BaseTile t) {}
	public void onLeaveTile(BaseTile t) {}
	
}
