package me.hii488.volcanoRush.items;

import me.hii488.handlers.ContainerHandler;
import me.hii488.misc.Grid;
import me.hii488.misc.Settings;
import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.dataTypes.DeathCause;
import me.hii488.volcanoRush.objects.tiles.AirTile;

public class ItemHardHat extends Item {
	
	public ItemHardHat() {
		this.name = "Hard Hat";
		this.identifier = "hardHat";
		this.droppable = true;
		this.cost = 100;
		this.priority = 1;
	}
	
	@Override
	public boolean onDeath(DeathCause cause) {
		if(cause == DeathCause.CRUSH) {
			Grid g = ContainerHandler.getLoadedContainer().grid;
			if(g.getTileAtVector(EntityRegistry.player.position.clone().addToLocation(Settings.Texture.tileSize, 0)) instanceof AirTile) {
				EntityRegistry.player.position.setLocation(
						Grid.getVectorAtGridPos(Grid.getGridPosAtVector(EntityRegistry.player.position.clone().addToLocation(Settings.Texture.tileSize, 0))) // Top left corner of tile on right
						.addToLocation(EntityRegistry.player.currentTexture.getWidth(), Settings.Texture.tileSize - EntityRegistry.player.currentTexture.getHeight() - 1) // move player to suitable position in the tile
				);
			}
			else if(g.getTileAtVector(EntityRegistry.player.position.clone().addToLocation(-Settings.Texture.tileSize, 0)) instanceof AirTile) {
				EntityRegistry.player.position.setLocation(
						Grid.getVectorAtGridPos(Grid.getGridPosAtVector(EntityRegistry.player.position.clone().addToLocation(-Settings.Texture.tileSize, 0))) // Top left corner of tile on right
						.addToLocation(Settings.Texture.tileSize - EntityRegistry.player.currentTexture.getWidth() - 1, Settings.Texture.tileSize - EntityRegistry.player.currentTexture.getHeight() - 1) // move player to suitable position in the tile
				);
			}
			
			return false;
		}
		
		return true;
	}
	
}
