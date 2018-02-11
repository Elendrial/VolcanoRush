package me.hii488.volcanoRush.objects.entities;

import me.hii488.misc.Grid;
import me.hii488.misc.Vector;
import me.hii488.objects.entities.BaseEntity;
import me.hii488.volcanoRush.dataTypes.LightSource;

public class LavaLightEntity extends BaseEntity implements LightSource{

	public LavaLightEntity() {super();}
	public LavaLightEntity(LavaLightEntity e) {super(e);}
	
	@Override
	public void initVars() {
		this.identifier = "lavaLight";
		this.currentState = 0;
		this.states = 0;
		this.textureName = "lavaLight.png";
	}
	
	@Override
	public int getLightIntensity(int depth) {
		return 75;
	}

	@Override
	public int getRadius(int depth) {
		return 5;
	}

	@Override
	public float getDropOff(int depth) {
		return 10;
	}
	
	@Override
	public Vector getPosition() {
		return Grid.getGridPosAtVector(position);
	}
	
	public float randTickChance() {return 0;}
	public void updateOnSec() {}
	public void updateOnRandTick() {}

	@Override
	public void onLoad() {}

	@Override
	public void onDestroy() {}

	@Override
	public LavaLightEntity clone() {
		return new LavaLightEntity(this);
	}

}
