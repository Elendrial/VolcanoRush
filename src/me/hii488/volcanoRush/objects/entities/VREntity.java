package me.hii488.volcanoRush.objects.entities;

import me.hii488.handlers.ContainerHandler;
import me.hii488.misc.Grid;
import me.hii488.objects.entities.BaseEntity;
import me.hii488.objects.tiles.BaseTile;
import me.hii488.volcanoRush.objects.tiles.AirTile;

// This exists so checking for entering liquids can be done "entity-side" rather than "tile-side"
abstract public class VREntity extends BaseEntity{

	public VREntity(){super();}
	protected VREntity(VREntity e){super(e);}
	
	public void updateOnTick(){
		super.updateOnTick();
		BaseTile t = ContainerHandler.getContainer(containerIdentifier).grid.getTile(Grid.getGridPosAtVector(position));
		if(t instanceof AirTile){
			((AirTile) t).fluidContent.inContactWith(this);
		}
	}
	
}
