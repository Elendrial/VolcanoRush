package me.hii488.volcanoRush.objects.entities;

import me.hii488.handlers.ContainerHandler;
import me.hii488.handlers.EntityHandler;
import me.hii488.misc.Grid;
import me.hii488.misc.Settings;
import me.hii488.objects.entities.BaseEntity;
import me.hii488.registries.EntityRegistry;
import me.hii488.registries.TileRegistry;
import me.hii488.volcanoRush.objects.tiles.DirtTile;
import me.hii488.volcanoRush.tileExtras.OreType;

public class FallingDirt extends BaseEntity{

	public OreType oreType;
	
	public FallingDirt(){
		super();
	}
	public FallingDirt(FallingDirt f){
		super(f);
		this.oreType = f.oreType;
	}
	
	@Override
	public void initVars() {
		this.identifier = "fallingDirt";
		this.textureName = "dirtTile_NONE.png";
	}
	
	public void updateOnTick(){
		super.updateOnTick();
		Grid g = ContainerHandler.getLoadedContainer().grid;
		do{
			this.position.addToLocation(0, Settings.Texture.tileSize);
			if(EntityHandler.getCollidingEntities(this).contains(EntityRegistry.player)) ((VRPlayer) EntityRegistry.player).kill();
		}while(!g.getTileAtVector(position.clone().addToLocation(0, Settings.Texture.tileSize)).isCollidable);
		this.destroy();
		DirtTile d = (DirtTile) TileRegistry.getTile("dirtTile");
		d.setOreType(oreType);
		d.damageValue = 3;
		ContainerHandler.getLoadedContainer().grid.setTile(d, ContainerHandler.getLoadedContainer().grid.getGridPosAtVector(position));
	}
	
	public void setOreType(OreType o) {
		oreType = o;
		this.identifier = "fallingDirt" + this.oreType;
		this.textureName = "dirtTile_" + oreType + ".png";
		this.setupTextures("tiles");
	}
	
	@Override
	public float randTickChance() {return 0;}

	@Override
	public void updateOnSec() {}

	@Override
	public void updateOnRandTick() {}

	@Override
	public void onLoad() {}

	@Override
	public void onDestroy() {}

	@Override
	public FallingDirt clone() {
		return new FallingDirt(this);
	}

}
