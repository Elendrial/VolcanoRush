package me.hii488.volcanoRush.items;

import java.awt.event.KeyEvent;

import me.hii488.handlers.ContainerHandler;
import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.objects.entities.DynamiteEntity;

public class ItemDynamite extends Item {
	
	public int delay = 45, delayAmount = 45;
	
	public ItemDynamite(){
		this.name = "Dynamite";
		this.identifier = "dynamite";
		this.droppable = true;
		this.cost = 100;
	}
	
	@Override
	public void updateOnTick() {
		if(delay > 0) delay--;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_CONTROL) {
			if(delay <= 0) {
				spawnDynamite();		
				delay = delayAmount;
			}
		}
	}
	
	public void spawnDynamite() {
		DynamiteEntity d = (DynamiteEntity) EntityRegistry.getEntity("dynamite").clone();
		d.position.setLocation(EntityRegistry.player.position.clone());
		ContainerHandler.getLoadedContainer().addEntity(d);
	}
	
}
