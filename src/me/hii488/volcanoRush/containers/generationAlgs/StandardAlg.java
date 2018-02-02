package me.hii488.volcanoRush.containers.generationAlgs;

import java.util.Random;

import me.hii488.controllers.GameController;
import me.hii488.misc.Grid;
import me.hii488.misc.Vector;
import me.hii488.objects.tiles.BaseTile;
import me.hii488.volcanoRush.dataTypes.FluidType;
import me.hii488.volcanoRush.dataTypes.OreType;
import me.hii488.volcanoRush.objects.tiles.AirTile;
import me.hii488.volcanoRush.objects.tiles.MineralTile;

public class StandardAlg extends GenerationAlg{

	@Override
	public void populate(Grid g) {
		g.wallRectWithTile("unbreakableTile", 0, 0, g.dimensions.getX(), g.dimensions.getY());
		g.fillRectWithTile("dirtTile", 1, 1, g.dimensions.getX()-1, g.dimensions.getY()-1);
		
		g.fillRectWithTile("airTile", 0, g.dimensions.getX()/2-1, 1, g.dimensions.getX()/2+1, 3);
		
		// The nice slope thing on the outside
		for(int i = 0 ; i < g.dimensions.getX()/4; i++){
			g.fillRectWithTile("airTile", 1, 0, i, g.dimensions.getX()/2-(2*i), i+1);
			g.fillRectWithTile("airTile", 1, g.dimensions.getX()/2+(i*2), i, g.dimensions.getX(), i+1);
			g.fillRectWithTile("unbreakableTile", g.dimensions.getX()/2 - (2*i + 2), i, g.dimensions.getX()/2 - (2*i), i+1);
			g.fillRectWithTile("unbreakableTile", g.dimensions.getX()/2 + (2*i), i, g.dimensions.getX()/2 + (2*i+2), i+1);
		}
		
		// Escape rope
		g.setTile("ropeTile", g.dimensions.getX()/2-1, 0);
		g.setTile("ropeTile", g.dimensions.getX()/2-1, 1);
		
		double rand, rockChance, smallCaveChance = 0.005, largeCaveChance = 0.0008, cavernChance = 0.0002, liquidRand, waterChance = 0.5, gasChance = 0.2;
		int xoffset, yoffset;
		int fluid;
		Vector tilePos = new Vector(0,0);
		
		Random random = new Random();
		
		for(int j = 0; j < g.dimensions.getY(); j++) { // x and y 'wrong' way around to go down by rows so rockchance can be updated easily.
			rockChance = Math.tanh(j * 0.01) * 0.01;
			liquidRand = random.nextDouble();
			
			if(liquidRand < gasChance) {
				fluid = FluidType.GAS.ordinal();
				liquidRand = liquidRand/(gasChance * 1.2) * 100; // Gets a random number
			}
			else if(liquidRand - gasChance < waterChance) {
				fluid = FluidType.WATER.ordinal();
				liquidRand = liquidRand/(waterChance * 1.2) * 100;
			}
			else fluid = -1;
			
			for(int i = 0; i < g.dimensions.getX(); i++) {
				// Replacing dirt with rock
				rand = random.nextDouble();
				if(rand < rockChance && g.getTile(i, j) instanceof MineralTile) g.setTile("rockTile", i, j);
				
				// Small cave/hole generation
				rand = random.nextDouble();
				if(rand < smallCaveChance && g.getTile(i, j) instanceof MineralTile) {
					xoffset = 0; yoffset = 0;
					g.setTile("airTile", 0, i, j);
					if(random.nextBoolean()) {
						if(random.nextBoolean()) xoffset += Math.round(random.nextDouble()) * 2 - 1;
						else yoffset += Math.round(random.nextDouble()) * 2 - 1;
						tilePos.setLocation(i +xoffset < g.dimensions.getX()-1 && i + xoffset > 0 ? i + xoffset : i, j +yoffset < g.dimensions.getY()-1 && j + yoffset > 0 ? j + yoffset: j);
						
						if(g.getTile(tilePos) instanceof MineralTile) {
							g.setTile("airTile", 0, tilePos);
							((AirTile) g.getTile(tilePos)).fillWithFluid(fluid, (int) liquidRand);
						}
					}
					if(random.nextDouble() < 0.4) {
						if(random.nextInt(2)-1 == 0) xoffset += Math.round(random.nextDouble()) * 2 - 1;
						else yoffset += Math.round(random.nextDouble()) * 2 - 1;
					
						tilePos.setLocation(i +xoffset < g.dimensions.getX()-1 && i + xoffset > 0 ? i + xoffset : i, j +yoffset < g.dimensions.getY()-1 && j + yoffset > 0 ? j + yoffset: j);
						
						if(g.getTile(tilePos) instanceof MineralTile) {
							g.setTile("airTile", 0, tilePos);
							((AirTile) g.getTile(tilePos)).fillWithFluid(fluid, (int) liquidRand);
						}
					}
					if(random.nextDouble() < 0.2) {
						if(random.nextInt(2)-1 == 0) xoffset += Math.round(random.nextDouble()) * 2 - 1;
						else yoffset += Math.round(random.nextDouble()) * 2 - 1;
								
						tilePos.setLocation(i +xoffset < g.dimensions.getX()-1 && i + xoffset > 0 ? i + xoffset : i, j +yoffset < g.dimensions.getY()-1 && j + yoffset > 0 ? j + yoffset: j);
						
						if(g.getTile(tilePos) instanceof MineralTile) {
							g.setTile("airTile", 0, tilePos);
							((AirTile) g.getTile(tilePos)).fillWithFluid(fluid, (int) liquidRand);
						}
					}
				}
				
				// Large cave/hole generation
				// TODO: Maybe somehow add a marker that this is where monsters should go?
				rand = random.nextDouble();
				if(rand < largeCaveChance && j > 25) {
					g.setTile("airTile", 0, i, j);
					rand = random.nextInt(5) + 5;
					for(int k = 0; k < rand + 1; k++) {
						xoffset = 0; yoffset = 0;
						
						for(int l = 0; l < rand; l++) {
							if(random.nextInt(2)-1 == 0) xoffset += Math.round(random.nextDouble()) * 2 - 1;
							else yoffset += Math.round(random.nextDouble()) * 2 - 1;
									
							tilePos.setLocation(i +xoffset < g.dimensions.getX()-1 && i + xoffset > 0 ? i + xoffset : i, j +yoffset < g.dimensions.getY()-1 && j + yoffset > 0 ? j + yoffset: j);
							
							if(g.getTile(tilePos) instanceof MineralTile) {
								g.setTile("airTile", 0, tilePos);
								((AirTile) g.getTile(tilePos)).fillWithFluid(fluid, (int) liquidRand);
							}
						}
					}
				}
				
				// Cavern generation
				rand = random.nextDouble();
				if(rand < cavernChance && j > 75) {
					int x1 = i - 21 > 1 ? i - 21 : 1, x2 = i + 21 < g.dimensions.getX()-1 ? i + 21 : g.dimensions.getX()-2;
					int y = j + 17 < g.dimensions.getY() ? j + 17 : g.dimensions.getY() - 1;
					
					g.fillRectWithTile("airTile", 0, x1, j, x2, y);
					
					for(int k = x1; k < x2; k++) {
						for(int l = j; l < y; l+=y-j-2) {
							for(int repeats = 0; repeats < 3; repeats++) {
								xoffset = 0; yoffset = 0;
								for(int m = 0; m < 7; m++) {
									if(random.nextInt(2)-1 == 0) xoffset += Math.round(random.nextDouble()) * 2 - 1;
									else yoffset += Math.round(random.nextDouble()) * 2 - 1;
											
									tilePos.setLocation(k + xoffset < g.dimensions.getX()-1 && k + xoffset > 0 ? k + xoffset : k, l + yoffset < g.dimensions.getY()-1 && l + yoffset > 0 ? l + yoffset: l);
									
									if(g.getTile(tilePos) instanceof MineralTile) {
										g.setTile("airTile", 0, tilePos);
										((AirTile) g.getTile(tilePos)).fillWithFluid(fluid, (int) liquidRand);
									}

								}
							}
						}
					}
					
					for(int l = j; l < y; l++) {
						for(int k = x1; k < x2; k += x2-x1-1) {
							for(int repeats = 0; repeats < 3; repeats++) {
								xoffset = 0; yoffset = 0;
								for(int m = 0; m < 7; m++) {
									if(random.nextInt(2)-1 == 0) xoffset += Math.round(random.nextDouble()) * 2 - 1;
									else yoffset += Math.round(random.nextDouble()) * 2 - 1;
									
									tilePos.setLocation(k + xoffset < g.dimensions.getX()-1 && k + xoffset > 0 ? k + xoffset : k, l + yoffset < g.dimensions.getY()-1 && l + yoffset > 0 ? l + yoffset: l);
									
									if(g.getTile(tilePos) instanceof MineralTile) {
										g.setTile("airTile", 0, tilePos);
										((AirTile) g.getTile(tilePos)).fillWithFluid(fluid, (int) liquidRand);
									}
								}
							}
						}
					}
				}
			}
		}
		
		// Adding ores
		for(BaseTile[] b : g.grid) for(BaseTile t : b){
			if(t instanceof MineralTile) ((MineralTile) t).setOreType(getOreType(t.gridPosition));
		}
	}
	
	public OreType getOreType(int x, int y) {
		double rand = GameController.rand.nextDouble();
		if(rand > 0.10) return OreType.NONE;
		
		double[] chances = new double[OreType.values().length];
		double total = 0;
		
		for(OreType o : OreType.values()) {
			chances[o.ordinal()] = o.util.getSpawnChance(x, y);
			total += chances[o.ordinal()];
		}
		
		rand = GameController.rand.nextDouble() * total;
		
		for(int i = 0; i < chances.length; i++) {
			rand -= chances[i];
			if(rand <= 0) return OreType.values()[i];
		}
		return OreType.NONE;
	}
}
