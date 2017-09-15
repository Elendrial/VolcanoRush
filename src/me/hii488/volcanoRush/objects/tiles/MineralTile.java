package me.hii488.volcanoRush.objects.tiles;

import me.hii488.objects.tiles.BaseTile;

public class MineralTile extends BaseTile {

	public MineralTile(){super();}
	public MineralTile(MineralTile t){super(t);}
	
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
		return null;
	}

	@Override
	public void initVars() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoad() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

	}

}
