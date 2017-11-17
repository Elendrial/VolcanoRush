package me.hii488.volcanoRush.containers.menus;

import me.hii488.objects.containers.BaseContainer;
import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.objects.entities.VRPlayer;

public class ShopMenu extends BaseContainer {
	
	public ShopMenu(){
		grid.setupGrid(10, 10);
		this.identifier = "mainmenu";
	}
	
	@Override
	public void onLoad(){
		super.onLoad();
		((VRPlayer) EntityRegistry.player).resetPlayer();
	}

}
