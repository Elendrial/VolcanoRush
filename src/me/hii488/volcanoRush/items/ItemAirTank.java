package me.hii488.volcanoRush.items;

import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.objects.entities.VRPlayer;

public class ItemAirTank extends Item {
	
	public ItemAirTank(){
		this.name = "Air Tank";
		this.identifier = "airTank";
		this.droppable = true;
		this.cost = 100;
	}
	
	@Override
	public void onEquip() {
		((VRPlayer) EntityRegistry.player).maxBreath = 240;
	}
	
}
