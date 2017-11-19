package me.hii488.volcanoRush.objects.tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.hii488.controllers.GameController;
import me.hii488.graphics.Camera;
import me.hii488.handlers.ContainerHandler;
import me.hii488.handlers.TextureHandler;
import me.hii488.misc.Settings;
import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.objects.entities.FallingDirt;
import me.hii488.volcanoRush.tileExtras.OreType;

public class DirtTile extends MineralTile{
	
	public int damageValue = 0;
	public BufferedImage[] overlay;
	
	public DirtTile(){super();}
	public DirtTile(DirtTile t){
		super(t);
		this.damageValue = t.damageValue;
		this.overlay = t.overlay;
	}
	
	@Override
	public void initVars() {
		oreType = OreType.NONE;
		this.identifier = "dirtTile";
		this.textureName = "dirtTile_" + this.oreType + ".png";
		this.isCollidable = true;
		
		overlay = new BufferedImage[3];
		for(int i = 0; i < overlay.length; i++)
			overlay[i] = TextureHandler.loadTexture("textures/overlays/", "dirtOverlay_" + i + ".png", this);
	}
	
	@Override
	public void updateOnTick() {
		super.updateOnTick();
		if(damageValue >= 5){
			this.onDestroy();
			ContainerHandler.getLoadedContainer().grid.setTile("airTile", gridPosition);
		}
	}
	
	@Override
	public void updateOnSec() {
		if(damageValue >= 1){
			if(ContainerHandler.getLoadedContainer().grid.getTile(gridPosition.getX(), gridPosition.getY() + 1) instanceof AirTile)	damageValue++;
			else if(damageValue >= 2) damageValue--;
		}
		if(damageValue == 4){
			FallingDirt d = (FallingDirt) EntityRegistry.getEntity("fallingDirt");
			d.setOreType(this.oreType);
			d.position.setLocation(gridPosition.getX() * Settings.Texture.tileSize, gridPosition.getY() * Settings.Texture.tileSize);
			ContainerHandler.getLoadedContainer().grid.setTile("airTile", gridPosition);
			ContainerHandler.getLoadedContainer().addEntity(d);
		}
	}
	
	public void onDig(){
		if(damageValue == 0) damageValue = 2;
		else damageValue = 5;
	}
	

	@Override
	public void setOreType(OreType o) {
		oreType = o;
		this.identifier = "dirtTile" + this.oreType;
		this.textureName = "dirtTile_" + oreType + ".png";
		this.setupTextures();
	}
	
	public void render(Graphics g){
		
		renderPosA.setX(gridPosition.getAbsX() * Camera.scale * Settings.Texture.tileSize - Camera.cameraPosition.getAbsX());
		renderPosA.setY(gridPosition.getAbsY() * Camera.scale * Settings.Texture.tileSize - Camera.cameraPosition.getAbsY());
		renderPosB.setX(renderPosA.getAbsX() + (Settings.Texture.tileSize * Camera.scale));
		renderPosB.setY(renderPosA.getAbsY() + (Settings.Texture.tileSize * Camera.scale));
		
		if(renderPosA.getX() < GameController.windows[0].width && renderPosB.getX() > 0){
			if(renderPosA.getY() < GameController.windows[0].height && renderPosB.getY() > 0){
				g.drawImage(currentTexture, renderPosA.getX(), renderPosA.getY(), null);
				if(damageValue > 0 && damageValue < 4) g.drawImage(overlay[damageValue-1], renderPosA.getX(), renderPosA.getY(), null);
				if(Settings.Logging.debug && isCollidable){
					g.setColor(Color.red);
					g.drawRect(renderPosA.getX(), renderPosA.getY(), (int)(Settings.Texture.tileSize * Camera.scale), (int)(Settings.Texture.tileSize * Camera.scale));
				}
			}
		}
	}
	
	@Override
	public DirtTile clone() {
		return new DirtTile(this);
	}

}
