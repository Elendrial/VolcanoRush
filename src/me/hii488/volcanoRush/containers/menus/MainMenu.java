package me.hii488.volcanoRush.containers.menus;

import me.hii488.objects.containers.BaseContainer;
import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.objects.entities.VRPlayer;

public class MainMenu extends BaseContainer {
	
	public MainMenu(){
		grid.setupGrid(10, 10);
	}
	
	@Override
	public void onLoad(){
		super.onLoad();
		((VRPlayer) EntityRegistry.player).movementAllowed = false;
	}
	
}
