package me.hii488.volcanoRush.objects.tiles;

import me.hii488.controllers.GameController;
import me.hii488.handlers.ContainerHandler;
import me.hii488.misc.Settings;
import me.hii488.objects.tiles.BaseTile;
import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.dataTypes.OreType;
import me.hii488.volcanoRush.objects.entities.MineralItem;

public abstract class MineralTile extends BaseTile {

	public OreType oreType;
	
	public MineralTile(){super();}
	public MineralTile(MineralTile t){
		super(t);
		this.oreType = t.oreType;
	}
	
	@Override
	public void initVars() {
		this.states = 0;
		this.isCollidable = true;
	}
	
	@Override
	public void updateOnTick() {
		oreType.util.updateOnTick(this.gridPosition.getX(), this.gridPosition.getY());
	}
	
	@Override
	public void updateOnSec() {
		oreType.util.updateOnSec(this.gridPosition.getX(), this.gridPosition.getY());
	}
	
	@Override
	public float randTickChance() {return 0;}

	@Override
	public void updateOnRandTick() {}

	@Override
	public abstract MineralTile clone();
	
	@Override
	public void onLoad() {}

	@Override
	public void onDestroy() {
		//super.onDestroy();
		MineralItem i = (MineralItem) EntityRegistry.getEntity("mineralItem");
		i.containerIdentifier = ContainerHandler.currentContainerIndentifier;
		i.setOreType(oreType);
		i.position.setLocation(gridPosition.getX() * Settings.Texture.tileSize + currentTexture.getWidth()/2 - 5, gridPosition.getY() * Settings.Texture.tileSize + currentTexture.getHeight()/2 - 5);
		i.previousMovement.setLocation(GameController.rand.nextFloat()*5-2.5f, GameController.rand.nextFloat()*-10);
		ContainerHandler.getLoadedContainer().addEntity(i);
	}

	public abstract void onDig();
	public abstract void setOreType(OreType o);
}
