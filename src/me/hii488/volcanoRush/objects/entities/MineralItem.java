package me.hii488.volcanoRush.objects.entities;

import me.hii488.handlers.EntityHandler;
import me.hii488.objects.TexturedObject;
import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.VolcRush;
import me.hii488.volcanoRush.objects.OreType;

public class MineralItem extends GravityEntity {

	public OreType oreType;
	
	@Override
	public void initVars() {
		this.identifier = "mineralItem";
		this.textureName = "mineral_" + oreType;
	}
	
	public void updateOnTick(){
		super.updateOnTick();
		if(EntityHandler.getCollidingEntities(this).contains(EntityRegistry.player)) VolcRush.score.addToScore(oreType.score);
	}
	
	public void setOreType(OreType o){
		if(o == OreType.NONE) this.destroy();
		oreType = o;
		this.identifier = "mineralItem" + this.oreType;
		this.textureName = "mineral_" + oreType;
		this.setupTextures();
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
	public TexturedObject clone() {return null;}

}
