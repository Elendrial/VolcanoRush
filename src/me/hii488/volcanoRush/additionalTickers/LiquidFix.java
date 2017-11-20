package me.hii488.volcanoRush.additionalTickers;

import java.util.ArrayList;

import me.hii488.controllers.GameController;
import me.hii488.handlers.ContainerHandler;
import me.hii488.interfaces.ITickable;
import me.hii488.misc.Grid;
import me.hii488.volcanoRush.containers.volcanoes.Volcano;
import me.hii488.volcanoRush.dataTypes.FluidType;
import me.hii488.volcanoRush.objects.tiles.AirTile;

public class LiquidFix implements ITickable{
	
	@Override
	public void updateOnSec() {
		if(ContainerHandler.getLoadedContainer() instanceof Volcano && !GameController.isPaused){
			
			Volcano container = (Volcano) ContainerHandler.getLoadedContainer();
			ArrayList<AirTile> toUpdate = new ArrayList<AirTile>();			
			
			for(FluidType f : FluidType.values()){ // For each fluid type
				for(int y = 1; y < container.grid.dimensions.getY() - 1; y++){ // Go down row by row
					for(int x = 0; x < container.grid.dimensions.getX(); x++){ // Seeing if the water needs fixing.
						if(container.grid.getTile(x, y + f.flowDir) instanceof AirTile){		// (this is before the next if so updateWater() can easily determine whether it's flattening or pushing down a hole.)
							updateWater(toUpdate, f, true);
							toUpdate.clear();
						}
						if(container.grid.getTile(x, y) instanceof AirTile) toUpdate.add((AirTile) container.grid.getTile(x, y));
						else{
							updateWater(toUpdate, f, false);
							toUpdate.clear();
						}
					}
					toUpdate.clear();
				}
			}
		}
	}

	
	public void updateWater(ArrayList<AirTile> toUpdate, FluidType f, boolean knownGap){
		if(toUpdate.size() == 0) return;
		if(!knownGap && toUpdate.size() == 1) return; // If we know there's not a gap and there's only 1 tile, skip it, it'll be handled by regular water rules.
		
		AirTile lowestPoint = null;
		Grid g = ContainerHandler.getLoadedContainer().grid;
		
		if(knownGap){ // If we're told there's a gap, it must be here:
			lowestPoint = (AirTile) g.getTile(toUpdate.get(toUpdate.size()-1).gridPosition.clone().addToLocation(1, f.flowDir));
		}
		
		// If there is a gap we're not told about, it must be here:
		if(!knownGap && g.getTile(toUpdate.get(0).gridPosition.clone().addToLocation(0, f.flowDir)) instanceof AirTile){
			knownGap = true;
			lowestPoint = (AirTile) g.getTile(toUpdate.get(0).gridPosition.clone().addToLocation(0, f.flowDir));
		}
		
		int unplacedFluid = 0;
		
		if(knownGap){ 										 // If there's a gap, put everything that can fit down it.
			for(AirTile t : toUpdate){ 						 // Go through each tile above it
				if(t.fluidContent[f.ordinal()] > 0){  		 // If tile has fluid in it
					unplacedFluid++;				 		 // Move some fluid from it into temp storage.
					t.fluidContent[f.ordinal()]--;
				}
			}
			while(lowestPoint.fluidContent[f.ordinal()] < 100 && unplacedFluid > 0){ // While the store isn't empty and the tile below isn't full
				lowestPoint.fluidContent[f.ordinal()]++;  // Move fluid from the store to the tile below.
				unplacedFluid--;
			}
			
		}
		
		// If there's any water left / not a gap, just flatten it all:
		
		for(AirTile t : toUpdate){
			unplacedFluid += t.fluidContent[f.ordinal()];
			t.fluidContent[f.ordinal()] = 0;
		}
		
		for(AirTile t : toUpdate){
			t.fluidContent[f.ordinal()] = unplacedFluid / toUpdate.size();
		}
		
		unplacedFluid %= toUpdate.size();
		for(int i = 0; i < unplacedFluid; i++) toUpdate.get(i).fluidContent[f.ordinal()] += 1;
		
	}

	@Override
	public void updateOnRandTick() {}
	
	@Override
	public float randTickChance() {return 0;}
	
	@Override
	public void updateOnTick() {}

}
