package me.hii488.volcanoRush.objects.tiles;

import java.awt.Color;
import java.awt.Graphics;

import me.hii488.graphics.Camera;
import me.hii488.handlers.ContainerHandler;
import me.hii488.handlers.TextureHandler;
import me.hii488.misc.Settings;
import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.dataTypes.OreType;
import me.hii488.volcanoRush.objects.entities.FallingDirt;

public class DirtTile extends MineralTile{
	
	public int damageValue = 0;
	
	public DirtTile(){super();}
	public DirtTile(DirtTile t){
		super(t);
		this.damageValue = t.damageValue;
	}
	
	@Override
	public void initVars() {
		oreType = OreType.NONE;
		this.identifier = "dirtTile";
		this.textureName = "dirtTile_" + this.oreType + ".png";
		this.isCollidable = true;
		
		for(int i = 0; i < 3; i++)
			TextureHandler.loadTexture("textures/overlays/", "dirtOverlay_" + i + ".png", this, "dirtOverlay_" + i);
	}
	
	@Override
	public void updateOnTick() {
		super.updateOnTick();
		if(damageValue >= 5){
			this.onDestroy();
			ContainerHandler.getLoadedContainer().grid.setTile("airTile", gridPosition);
			((LightTile) ContainerHandler.getLoadedContainer().grid.getTile(gridPosition)).raiseLightTo(lightPercent);
			((LightTile) ContainerHandler.getLoadedContainer().grid.getTile(gridPosition)).setLowestLight(lowestLight);
		}
	}
	
	@Override
	public void updateOnSec() {
		if(damageValue >= 1){
			if(ContainerHandler.getLoadedContainer().grid.getTile(gridPosition.getX(), gridPosition.getY() + 1) instanceof AirTile)	damageValue++;
			else if(damageValue >= 2) damageValue--;
		}
		if(damageValue == 4 && ContainerHandler.getLoadedContainer().grid.getTile(gridPosition.getX(), gridPosition.getY() + 1) instanceof AirTile){
			FallingDirt d = ((FallingDirt) EntityRegistry.getEntity("fallingDirt")).clone();
			d.setOreType(this.oreType);
			d.position.setLocation(gridPosition.getX() * Settings.Texture.tileSize, gridPosition.getY() * Settings.Texture.tileSize);
			ContainerHandler.getLoadedContainer().grid.setTile("airTile", gridPosition);
			((LightTile) ContainerHandler.getLoadedContainer().grid.getTile(gridPosition)).raiseLightTo(lightPercent);
			((LightTile) ContainerHandler.getLoadedContainer().grid.getTile(gridPosition)).setLowestLight(lowestLight);
			
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
		super.render(g);
		
		// Adding overlay like this as it is unlikely that this will need to be done for many tiles at once.
		if(damageValue > 0 && damageValue < 4) g.drawImage(TextureHandler.getTexture("dirtOverlay_" + (damageValue-1)), renderPosA.getX(), renderPosA.getY(), null);
		
		if(Settings.Logging.debug && isCollidable){
			g.setColor(Color.red);
			g.drawRect(renderPosA.getX(), renderPosA.getY(), (int)(Settings.Texture.tileSize * Camera.scale), (int)(Settings.Texture.tileSize * Camera.scale));
		}
	}
	
	@Override
	public DirtTile clone() {
		return new DirtTile(this);
	}

}
