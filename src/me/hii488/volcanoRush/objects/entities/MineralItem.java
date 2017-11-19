package me.hii488.volcanoRush.objects.entities;

import me.hii488.handlers.EntityHandler;
import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.VolcRush;
import me.hii488.volcanoRush.tileExtras.OreType;

public class MineralItem extends GravityEntity {

	public OreType oreType;
	
	public MineralItem(){super();}
	public MineralItem(MineralItem i){
		if(i.oreType != OreType.NONE) this.setOreType(i.oreType);
		else this.oreType = OreType.NONE;
	}
	
	@Override
	public void initVars() {
		this.states = 0;
		this.identifier = "mineralItem";
		this.textureName = "mineral_" + oreType + ".png";
	}
	
	public void updateOnTick(){
		super.updateOnTick();
		if(EntityHandler.getCollidingEntities(this).contains(EntityRegistry.player)){
			VolcRush.score.addToScore(oreType.score);
			this.destroy();
		}
	}
	
	public void setOreType(OreType o){
		if(o == OreType.NONE) this.destroy();
		oreType = o;
		this.identifier = "mineralItem" + this.oreType;
		this.textureName = "mineral_" + oreType + ".png";
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
	public MineralItem clone() {return new MineralItem(this);}

}
