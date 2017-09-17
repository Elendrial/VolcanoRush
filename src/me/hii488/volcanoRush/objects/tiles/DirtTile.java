package me.hii488.volcanoRush.objects.tiles;

import me.hii488.handlers.ContainerHandler;
import me.hii488.misc.Settings;
import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.objects.OreType;
import me.hii488.volcanoRush.objects.entities.FallingDirt;

public class DirtTile extends MineralTile{
	
	public int damageValue = 0;
	
	public DirtTile(){super();}
	public DirtTile(DirtTile t){
		super(t);
		this.damageValue = t.damageValue;
	}
	
	@Override
	public void initVars() {
		oreType = OreType.NONE;
		this.identifier = "dirtTile";
		this.textureName = "dirtTile_" + this.oreType + ".png";
		this.isCollidable = true;
	}
	
	@Override
	public void updateOnTick() {
		if(damageValue >= 5){
			this.onDestroy();
			ContainerHandler.getLoadedContainer().grid.setTile("airTile", gridPosition);
		}
	}
	
	@Override
	public void updateOnSec() {
		if(damageValue >= 1){
			if(ContainerHandler.getLoadedContainer().grid.getTile(gridPosition.getX(), gridPosition.getY() + 1) instanceof AirTile)	damageValue++;
			else if(damageValue >= 2) damageValue--;
		}
		if(damageValue == 4){
			FallingDirt d = (FallingDirt) EntityRegistry.getEntity("fallingDirt");
			d.setOreType(this.oreType);
			d.position.setLocation(gridPosition.getX() * Settings.Texture.tileSize, gridPosition.getY() * Settings.Texture.tileSize);
			ContainerHandler.getLoadedContainer().grid.setTile("airTile", gridPosition);
			ContainerHandler.getLoadedContainer().addEntity(d);
		}
	}
	
	public void onDig(){
		if(damageValue == 0) damageValue = 2;
		else damageValue = 5;
	}
	

	@Override
	public void setOreType(OreType o) {
		oreType = o;
		this.identifier = "dirtTile" + this.oreType;
		this.textureName = "dirtTile_" + oreType + ".png";
		this.setupTextures();
	}
	
	@Override
	public DirtTile clone() {
		return new DirtTile(this);
	}

}
