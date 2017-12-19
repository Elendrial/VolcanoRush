package me.hii488.volcanoRush.objects.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.hii488.handlers.ContainerHandler;
import me.hii488.handlers.TextureHandler;
import me.hii488.misc.Settings;
import me.hii488.objects.tiles.BaseTile;
import me.hii488.volcanoRush.dataTypes.FluidType;

public class AirTile extends LightTile{
	
	public AirTile(){super();}
	public AirTile(AirTile t){super(t);}
	
	public int[] fluidContent = new int[FluidType.values().length];
	
	@Override
	public void initVars() {
		this.states = 2; // Underground, and above ground
		this.textureName = "air.png";
		this.identifier = "airTile";
		
		for(int i = 0; i < FluidType.values().length; i++)
			for(int j = 0; j < 4; j++)
				TextureHandler.loadTexture("textures/overlays/", FluidType.values()[i].toString().toLowerCase() + "Overlay_" + j + ".png", this, FluidType.values()[i].toString().toLowerCase() + "Overlay_" + j);
	}
	
	public void fillWithFluid(FluidType fluid, int amount) {
		if(amount > 100) amount = 100;
		fluidContent[fluid.ordinal()] = amount;
	}
	
	public void fillWithFluid(int fluid, int amount) {
		if(amount > 100) amount = 100;
		if(fluid < 0 || fluid > FluidType.values().length) return;
		fluidContent[fluid] = amount;
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
							int j = (int) Math.ceil((fluidContent[i] - ((AirTile) t).fluidContent[i])/2);
							j = j > 10 ? 10 : j;
							
							if(fluidContent[i] - j >= 0){
								((AirTile) t).fluidContent[i] += j;
								fluidContent[i] -= j;
							}
						}
						
						t = ContainerHandler.getLoadedContainer().grid.getTile(gridPosition.clone().addToLocation(2, 0));
						if(t instanceof AirTile){
							if(((AirTile) t).fluidContent[i] < fluidContent[i]){
								int j = (int) Math.ceil((fluidContent[i] - ((AirTile) t).fluidContent[i])/2);
								j = j > 10 ? 10 : j;
								
								if(fluidContent[i] - j >= 0){
									((AirTile) t).fluidContent[i] += j;
									fluidContent[i] -= j;
								}
							}
						}
					}
					
					t = ContainerHandler.getLoadedContainer().grid.getTile(gridPosition.clone().addToLocation(-1, 0));
					if(t instanceof AirTile){
						if(((AirTile) t).fluidContent[i] < fluidContent[i]){
							int j = (int) Math.ceil((fluidContent[i] - ((AirTile) t).fluidContent[i])/2);
							j = j > 10 ? 10 : j;
							
							if(fluidContent[i] - j >= 0){
								((AirTile) t).fluidContent[i] += j;
								fluidContent[i] -= j;
							}
						}
						
						t = ContainerHandler.getLoadedContainer().grid.getTile(gridPosition.clone().addToLocation(-2, 0));
						if(t instanceof AirTile){
							if(((AirTile) t).fluidContent[i] < fluidContent[i]){
								int j = (int) Math.ceil((fluidContent[i] - ((AirTile) t).fluidContent[i])/2);
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
	}
	
	public void updateOnSec() {
		// Fluids within the top 3 y coords have fluids drain "out" to simulate them leaving the volcano.
		if(this.gridPosition.getY() < 3) {
			for(int i = 0; i < fluidContent.length; i++) {
				fluidContent[i] = fluidContent[i] > 50 ? fluidContent[i] - 50 : 0;
			}
		}
	}
	
	public void render(Graphics g){
		String fluidAsString = "";
		for(int i = 0; i < fluidContent.length; i++){
			if(fluidContent[i] == 0)      fluidAsString += "-1:";
			else if(fluidContent[i] < 25) fluidAsString += "0:";
			else if(fluidContent[i] < 50) fluidAsString += "1:";
			else if(fluidContent[i] < 75) fluidAsString += "2:";
			else                          fluidAsString += "3:";
			
		}
		
		textureName = sanitizedName + "_" + currentState + "_" + lightPercent + "_" + fluidAsString;

		if(!TextureHandler.containsTexture(textureName)) addLightToImage(addFluidOverlay(TextureHandler.getTexture(sanitizedName + "_" + currentState)));
		
		if(Settings.Logging.debug) g.drawString(fluidContent[1] + "", renderPosA.getX(), renderPosA.getY());
	}
	
	public BufferedImage addFluidOverlay(BufferedImage im) {
		BufferedImage img = TextureHandler.cloneTexture(im);
		Graphics g = img.createGraphics();
		
		for(int i = 0; i < fluidContent.length; i++){
			if(fluidContent[i] != 0){ 
				if(fluidContent[i] < 25)      g.drawImage(TextureHandler.getTexture(FluidType.values()[i].toString().toLowerCase() + "Overlay_" + 0), renderPosA.getX(), renderPosA.getY(), null); 
				else if(fluidContent[i] < 50) g.drawImage(TextureHandler.getTexture(FluidType.values()[i].toString().toLowerCase() + "Overlay_" + 1), renderPosA.getX(), renderPosA.getY(), null); 
	            else if(fluidContent[i] < 75) g.drawImage(TextureHandler.getTexture(FluidType.values()[i].toString().toLowerCase() + "Overlay_" + 2), renderPosA.getX(), renderPosA.getY(), null); 
	            else                          g.drawImage(TextureHandler.getTexture(FluidType.values()[i].toString().toLowerCase() + "Overlay_" + 3), renderPosA.getX(), renderPosA.getY(), null); 
			} 
		} 
		
		g.dispose();
		
		return img;
	}
	
	public AirTile clone(){
		return new AirTile(this);
	}
	
}
