package me.hii488.volcanoRush.objects.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import me.hii488.handlers.ContainerHandler;
import me.hii488.handlers.TextureHandler;
import me.hii488.objects.tiles.BaseTile;
import me.hii488.volcanoRush.fluids.Fluid;
import me.hii488.volcanoRush.registers.FluidRegistry;

public class AirTile extends LightTile{

	public HashMap<Fluid, Integer> fluidContent;
	
	public AirTile(){super();}
	public AirTile(AirTile t){
		super(t);
		this.fluidContent = new HashMap<Fluid, Integer>(t.fluidContent);
		
		for(Fluid f : FluidRegistry.fluids.values()) fluidContent.put(f, t.fluidContent.get(f));
	}
	
	
	@Override
	public void initVars() {
		this.states = 2; // Underground, and above ground
		this.textureName = "air.png";
		this.identifier = "airTile";
		
		fluidContent = new HashMap<Fluid, Integer>();
		for(String s : FluidRegistry.fluids.keySet())
			fluidContent.put(FluidRegistry.fluids.get(s), 0);
	}
	
	public void fillWithFluid(Fluid fluid, int amount) {
		if(amount > 100) amount = 100;
		if(amount < 0) amount = 0;
		fluidContent.put(fluid, amount);
	}
	
	public void fillWithFluid(String fluid, int amount) {
		if(amount > 100) amount = 100;
		if(amount < 0) amount = 0;
		fluidContent.put(FluidRegistry.getFluid(fluid), amount);
	}
	
	
	
	@Override
	public void updateOnTick(){
		super.updateOnTick();
		liquidGravity();
		liquidUpdate();
	}
	
	public void liquidGravity(){
		boolean belowFull;
		int flAmount;
		
		for(Fluid fluid : fluidContent.keySet()){
			if(fluidContent.get(fluid) != 0){
				belowFull = false;
				flAmount = fluidContent.get(fluid);
				BaseTile t = ContainerHandler.getLoadedContainer().grid.getTile(gridPosition.clone().addToLocation(0, fluid.flowDir.getDeltaY()));
				
				if(t instanceof AirTile){
					if(((AirTile) t).fluidContent.get(fluid) < 100){
						int j = 100 - ((AirTile) t).fluidContent.get(fluid);
						j = j > 10 ? 10 : j;
						
						if(flAmount - j < 0) j = flAmount;
						
						if(flAmount - j >= 0){
							((AirTile) t).fluidContent.put(fluid, ((AirTile) t).fluidContent.get(fluid) + j);
							flAmount -= j;
						}
					}
					else belowFull = true;
				}
				else belowFull = true;
				
				if(belowFull){
					for(int loc = 1; loc > -2; loc -=2){
						t = ContainerHandler.getLoadedContainer().grid.getTile(gridPosition.clone().addToLocation(loc, 0));
						if(t instanceof AirTile){
							if(((AirTile) t).fluidContent.get(fluid) < flAmount){
								int j = (int) Math.ceil((flAmount - ((AirTile) t).fluidContent.get(fluid))/2);
								j = j > 10 ? 10 : j;
								
								if(flAmount - j >= 0){
									((AirTile) t).fluidContent.put(fluid, ((AirTile) t).fluidContent.get(fluid) + j);
									flAmount -= j;
								}
							}
							
							t = ContainerHandler.getLoadedContainer().grid.getTile(gridPosition.clone().addToLocation(loc*2, 0));
							if(t instanceof AirTile){
								if(((AirTile) t).fluidContent.get(fluid) < flAmount){
									int j = (int) Math.ceil((flAmount - ((AirTile) t).fluidContent.get(fluid))/2);
									j = j > 10 ? 10 : j;
									
									if(flAmount - j >= 0){
										((AirTile) t).fluidContent.put(fluid, ((AirTile) t).fluidContent.get(fluid) + j);
										flAmount -= j;
									}
								}
							}
						}
					}
				}
				
				fluidContent.put(fluid, flAmount);
			}
		}
	}
	
	public void liquidUpdate(){
		for(Fluid f : fluidContent.keySet()){
			if(fluidContent.get(f) > 0) f.updateOnTick(this.gridPosition.getX(), this.gridPosition.getY(), fluidContent.get(f));
		}
	}
	
	public void updateOnSec() {
		// Fluids within the top 3 y coords have fluids drain "out" to simulate them leaving the volcano.
		if(this.gridPosition.getY() < 3) {
			for(Fluid fluid : fluidContent.keySet()){
				if(fluidContent.get(fluid) > 0) fluidContent.put(fluid, 0);
			}
		}
		
		for(Fluid f : fluidContent.keySet()){
			if(fluidContent.get(f) > 0) f.updateOnSec(this.gridPosition.getX(), this.gridPosition.getY(), fluidContent.get(f));
		}
	}
	
	public void render(Graphics g){
		String fluidAsString = "";
		int flAmount;
		for(Fluid fluid : fluidContent.keySet()){
			flAmount = fluidContent.get(fluid);
			if(flAmount == 0)      fluidAsString += "-1:";
			else if(flAmount < 25) fluidAsString += "0:";
			else if(flAmount < 50) fluidAsString += "1:";
			else if(flAmount < 75) fluidAsString += "2:";
			else                   fluidAsString += "3:";
		}
		
		textureName = sanitizedName + "_" + currentState + "_" + lightPercent + "_" + fluidAsString;
		
		if(!TextureHandler.containsTexture(textureName)) addLightToImage(addFluidOverlay(TextureHandler.getTexture(sanitizedName + "_" + currentState)));
		g.drawImage(getTexture(), renderPosA.getX(), renderPosA.getY(), null);
	}
	
	public BufferedImage addFluidOverlay(BufferedImage im) {
		BufferedImage img = TextureHandler.cloneTexture(im);
		Graphics g = img.createGraphics();
		
		for(Fluid fluid : fluidContent.keySet()){
			fluid.addOverlay(g, fluidContent.get(fluid));
		}
		g.dispose();
		
		return img;
	}
	
	public AirTile clone(){
		return new AirTile(this);
	}
	
}
