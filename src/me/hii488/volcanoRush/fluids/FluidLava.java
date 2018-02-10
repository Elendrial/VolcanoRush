package me.hii488.volcanoRush.fluids;

import java.util.HashMap;

import me.hii488.handlers.ContainerHandler;
import me.hii488.misc.Grid;
import me.hii488.misc.Vector;
import me.hii488.objects.entities.BaseEntity;
import me.hii488.objects.tiles.BaseTile;
import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.Initilisation;
import me.hii488.volcanoRush.dataTypes.DeathCause;
import me.hii488.volcanoRush.objects.entities.LavaLightEntity;
import me.hii488.volcanoRush.objects.entities.MineralItem;
import me.hii488.volcanoRush.objects.entities.VRPlayer;
import me.hii488.volcanoRush.objects.tiles.AirTile;
import me.hii488.volcanoRush.objects.tiles.MineralTile;

public class FluidLava extends Fluid{

	public HashMap<Vector, LavaLightEntity> lavaLights = new HashMap<Vector, LavaLightEntity>();
	
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
	
	public void onEnterTile(BaseTile t) {
		LavaLightEntity e = (LavaLightEntity) EntityRegistry.getEntity("lavaLightEntity").clone();
		e.position.setLocation(Grid.getVectorAtGridPos(t.gridPosition));
		Initilisation.lightHandler.sources.add(e);
		lavaLights.put(t.gridPosition, e);
	}
	
	public void onLeaveTile(BaseTile t) {
		Initilisation.lightHandler.sources.remove(lavaLights.get(t.gridPosition));
		lavaLights.get(t.gridPosition).destroy();
		lavaLights.remove(t.gridPosition);
	}
	
	public void onContactWith(BaseEntity e) {
		if(e instanceof VRPlayer) {
			((VRPlayer) e).kill(DeathCause.LAVA);
		}
		else if(e instanceof MineralItem) {
			e.destroy();
		}
	}
	
	public void updateOnSec(int x, int y, int level) {}
	
}
