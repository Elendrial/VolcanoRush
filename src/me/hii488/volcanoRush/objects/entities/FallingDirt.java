package me.hii488.volcanoRush.objects.entities;

import me.hii488.handlers.ContainerHandler;
import me.hii488.misc.Settings;
import me.hii488.registries.TileRegistry;
import me.hii488.volcanoRush.objects.OreType;
import me.hii488.volcanoRush.objects.tiles.DirtTile;

public class FallingDirt extends GravityEntity{

	public OreType oreType;
	
	public FallingDirt(){super();}
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
		if(previousMovement.getY() == 0){
			if(ContainerHandler.getLoadedContainer().grid.getTileAtVector(position.clone().addToLocation(0, Settings.Texture.tileSize)).isCollidable){
				this.destroy();
				DirtTile d = (DirtTile) TileRegistry.getTile("dirtTile");
				d.setOreType(oreType);
				d.damageValue = 3;
				ContainerHandler.getLoadedContainer().grid.setTile(d, ContainerHandler.getLoadedContainer().grid.getGridPosAtVector(position));
			}
		}
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
