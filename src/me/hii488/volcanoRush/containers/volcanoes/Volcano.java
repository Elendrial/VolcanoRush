package me.hii488.volcanoRush.containers.volcanoes;

import me.hii488.objects.containers.BaseContainer;
import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.containers.algorithms.MineralAlg;
import me.hii488.volcanoRush.objects.entities.VRPlayer;

public abstract class Volcano extends BaseContainer{
	public MineralAlg mineralSpawner;
	
	public Volcano(){super();}
	
	public void onLoad(){
		super.onLoad();
		mineralSpawner.populate(grid);
		((VRPlayer) EntityRegistry.player).movementAllowed = true;
//		EntityRegistry.player.position = grid.
	}
}
