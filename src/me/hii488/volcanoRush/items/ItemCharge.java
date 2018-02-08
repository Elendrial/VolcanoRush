package me.hii488.volcanoRush.items;

import me.hii488.handlers.ContainerHandler;
import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.objects.entities.ChargeEntity;
import me.hii488.volcanoRush.registers.ItemRegistry;

public class ItemCharge extends ItemDynamite {
	
	public ItemCharge() {
		this.name = "charge";
		this.identifier = "charge";
		this.droppable = true;
		this.cost = 200;
	}
	
	public void onEquip() {
		ItemRegistry.unequipItem("dynamite");
	}
	
	@Override
	public void spawnDynamite() {
		ChargeEntity e = (ChargeEntity) EntityRegistry.getEntity("charge");
		e.position.setLocation(EntityRegistry.player.position.clone());
		ContainerHandler.getLoadedContainer().addEntity(e);
	}
	
}
