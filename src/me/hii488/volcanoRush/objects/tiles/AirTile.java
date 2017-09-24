package me.hii488.volcanoRush.objects.tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.hii488.controllers.GameController;
import me.hii488.graphics.Camera;
import me.hii488.handlers.ContainerHandler;
import me.hii488.handlers.TextureHandler;
import me.hii488.misc.Settings;
import me.hii488.objects.tiles.BaseTile;
import me.hii488.objects.tiles.BlankTile;
import me.hii488.volcanoRush.objects.FluidType;

public class AirTile extends BlankTile{
	
	public AirTile(){super();}
	public AirTile(AirTile t){
		super(t);
		overlays = t.overlays;
	}
	
	public int[] fluidContent = new int[FluidType.values().length];
	public BufferedImage[][] overlays;
	
	@Override
	public void initVars() {
		this.states = 1; // Underground, and above ground
		this.textureName = "air.png";
		this.identifier = "airTile";
		
		this.overlays = new BufferedImage[FluidType.values().length][4];
		
		for(int i = 0; i < overlays.length; i++)
			for(int j = 0; j < overlays[i].length; j++)
				overlays[i][j] = TextureHandler.loadTexture("textures/overlays/", FluidType.values()[i].toString().toLowerCase() + "Overlay_" + j + ".png", this);
	}
	
	protected boolean belowFull;
	@Override
	public void updateOnTick(){
		for(int i = 0; i < fluidContent.length; i++){
			if(fluidContent[i] != 0){
				belowFull = false;
				BaseTile t = ContainerHandler.getLoadedContainer().grid.getTile(gridPosition.clone().addToLocation(0, FluidType.values()[i].flowDir));
				if(t instanceof AirTile){
					if(((AirTile) t).fluidContent[i] < 100){
						int j = 100 - ((AirTile) t).fluidContent[i];
						j = j > 10 ? 10 : j;
						
						if(fluidContent[i] - j < 0) j = fluidContent[i];
						
						if(fluidContent[i] - j >= 0){
							((AirTile) t).fluidContent[i] += j;
							fluidContent[i] -= j;
						}
					}
					else belowFull = true;
				}
				else belowFull = true;
				
				if(belowFull){
					t = ContainerHandler.getLoadedContainer().grid.getTile(gridPosition.clone().addToLocation(1, 0));
					if(t instanceof AirTile){
						if(((AirTile) t).fluidContent[i] < fluidContent[i]){
							int j = (fluidContent[i] - ((AirTile) t).fluidContent[i])/2;
							j = j > 10 ? 10 : j;
							
							if(fluidContent[i] - j >= 0){
								((AirTile) t).fluidContent[i] += j;
								fluidContent[i] -= j;
							}
						}
					}
					
					t = ContainerHandler.getLoadedContainer().grid.getTile(gridPosition.clone().addToLocation(-1, 0));
					if(t instanceof AirTile){
						if(((AirTile) t).fluidContent[i] < fluidContent[i]){
							int j = (fluidContent[i] - ((AirTile) t).fluidContent[i])/2;
							j = j > 10 ? 10 : j;
							
							if(fluidContent[i] - j >= 0){
								((AirTile) t).fluidContent[i] += j;
								fluidContent[i] -= j;
							}
						}
					}
				}
			}
		}
	}
	
	public void render(Graphics g){
		if(this.states > 0) currentTexture = textureImages[currentState];
		
		renderPosA.setX(gridPosition.getAbsX() * Camera.scale * Settings.Texture.tileSize - Camera.cameraPosition.getAbsX());
		renderPosA.setY(gridPosition.getAbsY() * Camera.scale * Settings.Texture.tileSize - Camera.cameraPosition.getAbsY());
		renderPosB.setX(renderPosA.getAbsX() + (Settings.Texture.tileSize * Camera.scale));
		renderPosB.setY(renderPosA.getAbsY() + (Settings.Texture.tileSize * Camera.scale));
		
		if(renderPosA.getX() < GameController.windows[0].width && renderPosB.getX() > 0){
			if(renderPosA.getY() < GameController.windows[0].height && renderPosB.getY() > 0){
				g.drawImage(currentTexture, renderPosA.getX(), renderPosA.getY(), null);
				
				for(int i = 0; i < fluidContent.length; i++){
					if(fluidContent[i] != 0){
						if(fluidContent[i] < 25) g.drawImage(overlays[i][0], renderPosA.getX(), renderPosA.getY(), null);
						else if(fluidContent[i] < 50) g.drawImage(overlays[i][1], renderPosA.getX(), renderPosA.getY(), null);
						else if(fluidContent[i] < 75) g.drawImage(overlays[i][2], renderPosA.getX(), renderPosA.getY(), null);
						else g.drawImage(overlays[i][3], renderPosA.getX(), renderPosA.getY(), null);
					}
				}
				
				if(Settings.Logging.debug && isCollidable){
					g.setColor(Color.red);
					g.drawRect(renderPosA.getX(), renderPosA.getY(), (int)(Settings.Texture.tileSize * Camera.scale), (int)(Settings.Texture.tileSize * Camera.scale));
				}
			}
		}
	}
	
	public AirTile clone(){
		return new AirTile(this);
	}
	
}
