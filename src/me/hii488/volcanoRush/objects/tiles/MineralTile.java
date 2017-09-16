package me.hii488.volcanoRush.objects.tiles;

import me.hii488.controllers.GameController;
import me.hii488.handlers.ContainerHandler;
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
		this.states = 3; // 0: Undamaged, 1: barely damaged, 2: moderately damaged
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
		i.previousMovement.setLocation(GameController.rand.nextFloat() * 3, GameController.rand.nextFloat() * 5);
		i.setOreType(oreType);
		ContainerHandler.getLoadedContainer().addEntity(i);
	}

	public abstract void onDig();
	
}
