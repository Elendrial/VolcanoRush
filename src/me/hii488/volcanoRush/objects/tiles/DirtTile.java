package me.hii488.volcanoRush.objects.tiles;

import me.hii488.handlers.ContainerHandler;
import me.hii488.volcanoRush.containers.volcanoes.Volcano;
import me.hii488.volcanoRush.objects.OreType;

public class DirtTile extends MineralTile{
	
	public DirtTile(){super();}
	public DirtTile(DirtTile t){super(t);}
	
	@Override
	public void initVars() {
		if(ContainerHandler.getLoadedContainer() instanceof Volcano) this.oreType = ((Volcano) ContainerHandler.getLoadedContainer()).mineralSpawner.getOreType(gridPosition);
		else oreType = OreType.NONE;
		this.identifier = "dirtTile";
		this.textureName = "dirtTile_" + this.oreType + ".png";
		this.isCollidable = true;
	}

	@Override
	public DirtTile clone() {
		return new DirtTile(this);
	}

}
