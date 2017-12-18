package me.hii488.volcanoRush.objects.tiles;

import me.hii488.objects.tiles.BaseTile;

public class UnbreakableTile extends LightTile{

	public UnbreakableTile(){}
	public UnbreakableTile(UnbreakableTile t){super(t);}

	@Override
	public void initVars() {
		this.states = 0;
		this.identifier = "unbreakableTile";
		this.textureName = "unbreakableTile.png";
		this.isCollidable = true;
	}
	
	@Override
	public float randTickChance() {return 0;}

	@Override
	public void updateOnTick() {}

	@Override
	public void updateOnSec() {}

	@Override
	public void updateOnRandTick() {}

	@Override
	public BaseTile clone() {
		return new UnbreakableTile(this);
	}

	@Override
	public void onLoad() {}
	@Override
	public void onDestroy() {}

}
