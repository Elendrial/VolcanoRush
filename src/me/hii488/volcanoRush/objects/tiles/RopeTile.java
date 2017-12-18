package me.hii488.volcanoRush.objects.tiles;

import me.hii488.misc.Grid;
import me.hii488.objects.tiles.BaseTile;
import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.VolcRush;
import me.hii488.volcanoRush.dataTypes.DeathCause;
import me.hii488.volcanoRush.objects.entities.VRPlayer;

public class RopeTile extends LightTile{

	public RopeTile() {super();}
	public RopeTile(RopeTile t) {super(t);}
	
	@Override
	public void initVars() {
		this.states = 0;
		this.currentState = 0;
		this.isCollidable = false;
		this.textureName = "ropeTile.png";
		this.identifier = "ropeTile";
	}
	
	@Override
	public float randTickChance() {return 0;}

	@Override
	public void updateOnTick() {
		if(Grid.getGridPosAtVector(EntityRegistry.player.position).equals(this.gridPosition)) {
			VolcRush.score.multiplier *= 2;
			((VRPlayer) EntityRegistry.player).kill(DeathCause.ESCAPE);
		}
	}

	@Override
	public void updateOnSec() {}

	@Override
	public void updateOnRandTick() {}

	@Override
	public BaseTile clone() {return new RopeTile(this);}
	
	@Override
	public void onLoad() {}

}
