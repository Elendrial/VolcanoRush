package me.hii488.volcanoRush.objects.tiles;

import me.hii488.handlers.ContainerHandler;
import me.hii488.volcanoRush.containers.volcanoes.Volcano;

public class DirtTile extends MineralTile{
	
	public DirtTile(){super();}
	public DirtTile(DirtTile t){super(t);}
	
	@Override
	public void initVars() {
		this.oreType = ((Volcano) ContainerHandler.getLoadedContainer()).mineralSpawner.getOreType(gridPosition);
		
		this.identifier = "dirtTile" + this.oreType;
		this.textureName = "dirtTile_" + this.oreType.toString().toLowerCase();
	}

	@Override
	public DirtTile clone() {
		return new DirtTile(this);
	}

}
