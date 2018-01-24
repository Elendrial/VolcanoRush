package me.hii488.volcanoRush.objects.entities;

import java.awt.Color;
import java.awt.Graphics;

import me.hii488.graphics.Camera;
import me.hii488.handlers.ContainerHandler;
import me.hii488.handlers.TextureHandler;
import me.hii488.misc.Grid;
import me.hii488.misc.Settings;
import me.hii488.objects.entities.RenderEntity;
import me.hii488.volcanoRush.VolcRush;
import me.hii488.volcanoRush.containers.volcanoes.Volcano;
import me.hii488.volcanoRush.objects.tiles.LightTile;

public class VRPlayerRenderEntity extends RenderEntity{

	float breathPercent;
	
	public VRPlayerRenderEntity(VRPlayer e) {
		super(e);
		breathPercent = ((float)e.breath)/((float)e.maxBreath);
	}
	
	public void render(Graphics g) {
		if((ContainerHandler.getLoadedContainer() instanceof Volcano)){
			super.render(g);
			
			if(breathPercent < 1){
				if(breathPercent > 0.75)      g.drawImage(TextureHandler.getTexture("breathOverlay_" + 0), position.getX() - Camera.getPosition().getX() + 16, position.getY() - Camera.getPosition().getY() - 5, null);
				else if(breathPercent > 0.5) g.drawImage(TextureHandler.getTexture("breathOverlay_" + 1), position.getX() - Camera.getPosition().getX() + 16, position.getY() - Camera.getPosition().getY() - 5, null);
				else if(breathPercent > 0.25) g.drawImage(TextureHandler.getTexture("breathOverlay_" + 2), position.getX() - Camera.getPosition().getX() + 16, position.getY() - Camera.getPosition().getY() - 5, null);
				else                g.drawImage(TextureHandler.getTexture("breathOverlay_" + 3), position.getX() - Camera.getPosition().getX() + 16, position.getY() - Camera.getPosition().getY() - 5, null);
			}
	
			if((Settings.Logging.debug || VolcRush.debugCommands) && ContainerHandler.getLoadedContainer() instanceof Volcano){
				g.setColor(Color.white);
				g.fillRect(0, 0, 1000, 15);
				g.setColor(Color.black);
				g.drawString("player: " + position + ";;  tilePos: " + Grid.getGridPosAtVector(position).toString() + ";;   camera: " + Camera.getPosition() + ";;  tile: " + ContainerHandler.getLoadedContainer().grid.getTileAtVector(position).identifier 
						+ ";; light: " + ((LightTile) ContainerHandler.getLoadedContainer().grid.getTileAtVector(position)).lightPercent+ ";; score: " + VolcRush.score.getScore(), 2, 12);
			}
		}
	}
	
	
	
}
