package me.hii488.volcanoRush.objects.entities;

import me.hii488.misc.Vector;
import me.hii488.objects.TexturedObject;
import me.hii488.objects.entities.BaseEntity;
import me.hii488.volcanoRush.dataTypes.LightSource;

public class LavaLightEntity extends BaseEntity implements LightSource{

	public LavaLightEntity() {}
	public LavaLightEntity(LavaLightEntity e) {
		super(e);
	}
	
	@Override
	public void initVars() {
		this.identifier = "lavaLightEntity";
		this.currentState = 0;
		this.states = 0;
		this.textureName = "NONE";
	}
	
	@Override
	public int getLightIntensity(int depth) {
		return 99;
	}

	@Override
	public int getRadius(int depth) {
		return 7;
	}

	@Override
	public float getDropOff(int depth) {
		return 10;
	}
	
	@Override
	public Vector getPosition() {
		return this.position;
	}
	
	public float randTickChance() {return 0;}
	public void updateOnSec() {}
	public void updateOnRandTick() {}

	@Override
	public void onLoad() {}

	@Override
	public void onDestroy() {}

	@Override
	public TexturedObject clone() {
		return null;
	}

}
