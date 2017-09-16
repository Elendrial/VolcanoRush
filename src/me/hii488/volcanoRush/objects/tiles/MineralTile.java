package me.hii488.volcanoRush.objects.tiles;

import me.hii488.controllers.GameController;
import me.hii488.handlers.ContainerHandler;
import me.hii488.misc.Settings;
import me.hii488.objects.tiles.BaseTile;
import me.hii488.volcanoRush.objects.OreType;
import me.hii488.volcanoRush.objects.entities.MineralItem;

public abstract class MineralTile extends BaseTile {

	public OreType oreType;
	public int damageValue = 0;
	
	public MineralTile(){super();}
	public MineralTile(MineralTile t){
		super(t);
		this.oreType = t.oreType;
		this.damageValue = t.damageValue;
	}
	
	@Override
	public void initVars() {
		this.states = 0;
		this.isCollidable = true;
	}
	
	@Override
	public float randTickChance() {return 0;}

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
			// MineralTile should be replaced with AirTile, a FallingTileEntity should be created at it's position.
		}
	}

	@Override
	public void updateOnRandTick() {}

	@Override
	public abstract MineralTile clone();
	
	@Override
	public void onLoad() {}

	@Override
	public void onDestroy() {
		//super.onDestroy();
		MineralItem i = new MineralItem();
		i.position.setLocation(gridPosition.getX() * Settings.Texture.tileSize + currentTexture.getWidth()/2 - 5, gridPosition.getY() * Settings.Texture.tileSize + currentTexture.getHeight()/2 - 5);
		i.previousMovement.setLocation(GameController.rand.nextFloat()*5-2.5f, GameController.rand.nextFloat()*-10);
		ContainerHandler.getLoadedContainer().addEntity(i);
		i.setOreType(oreType);
	}

	public abstract void onDig();
	public abstract void setOreType(OreType o);
}
