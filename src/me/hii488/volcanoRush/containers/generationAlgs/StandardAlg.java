package me.hii488.volcanoRush.containers.generationAlgs;

import java.util.Random;

import me.hii488.controllers.GameController;
import me.hii488.misc.Grid;
import me.hii488.misc.Vector;
import me.hii488.objects.tiles.BaseTile;
import me.hii488.volcanoRush.dataTypes.OreType;
import me.hii488.volcanoRush.fluids.Fluid;
import me.hii488.volcanoRush.objects.tiles.AirTile;
import me.hii488.volcanoRush.objects.tiles.MineralTile;
import me.hii488.volcanoRush.registers.FluidRegistry;

public class StandardAlg extends GenerationAlg{

	private Random random = new Random();
	
	@Override
	public void populate(Grid g){
		populate(g, random.nextLong());
	}
	
	@Override
	public void populate(Grid g, long seed) {
		random.setSeed(seed);
		System.out.println("Seed:" + seed);
		
		setupBaseMap(g);
		
		double rand, liquidRand, rockChance;
		Fluid fluid;
		
		for(int j = 10; j < g.dimensions.getY(); j++) { // x and y 'wrong' way around to go down by rows so rockchance can be updated easily.
			rockChance = getRockChance(0, j);
			liquidRand = random.nextDouble();
			
			if(liquidRand < getGasChance(0,j)) {
				fluid = FluidRegistry.getFluid("gas");
				liquidRand = liquidRand/(getGasChance(0,j) * 1.2) * 100;
			}
			else if(liquidRand - getGasChance(0,j) < getWaterChance(0,j)) {
				fluid = FluidRegistry.getFluid("water");
				liquidRand = liquidRand/(getWaterChance(0,j) * 1.2) * 100;
			}
			else fluid = null;
			
			for(int i = 0; i < g.dimensions.getX(); i++) {
				// Replacing dirt with rock
				rand = random.nextDouble();
				if(rand < rockChance && g.getTile(i, j) instanceof MineralTile) g.setTile("rockTile", i, j);
				
				// Small cave/hole generation
				rand = random.nextDouble();
				if(rand < getSmallCaveChance(i, j) && g.getTile(i, j) instanceof MineralTile) {
					makeSmallCave(g, i, j, fluid, liquidRand);
				}
				
				// Large cave/hole generation
				// TODO: Maybe somehow add a marker that this is where monsters should go?
				rand = random.nextDouble();
				if(rand < getLargeCaveChance(i,j) && j > 25) {
					makeLargeCave(g, i, j, fluid, liquidRand);
				}
				
				// Cavern generation
				rand = random.nextDouble();
				if(rand < getCavernChance(i,j) && j > 75) {
					makeCavern(g, i, j, fluid, liquidRand);
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

	// I put these into methods rather than attributes so they can be made functions on (x,y) at some point easily if needed.
	public double getRockChance(int x, int y) {
		return Math.tanh(y * 0.01) * 0.01;
	}

	public double getSmallCaveChance(int x, int y) {
		return 0.005;
	}

	public double getLargeCaveChance(int x, int y) {
		return y > 100 ? 0.0016 : 0.0008;
	}

	public double getCavernChance(int x, int y) {
		return 0.00002;
	}
	public double getWaterChance(int x, int y) {
		return 0.5;
	}

	public double getGasChance(int x, int y) {
		return 0.2;
	}

	private void setupBaseMap(Grid g){
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
	}
	
	private void makeSmallCave(Grid g, int i, int j, Fluid fluid, double liquidRand){
		int xoffset = 0, yoffset = 0;
		
		g.setTile("airTile", 0, i, j);
		if(random.nextBoolean()) {
			if(random.nextBoolean()) xoffset += Math.round(random.nextDouble()) * 2 - 1;
			else yoffset += Math.round(random.nextDouble()) * 2 - 1;
			
			safeSetAir(g, i + xoffset, j + yoffset, fluid, liquidRand);
		}
		if(random.nextDouble() < 0.4) {
			if(random.nextInt(2)-1 == 0) xoffset += Math.round(random.nextDouble()) * 2 - 1;
			else yoffset += Math.round(random.nextDouble()) * 2 - 1;
		
			safeSetAir(g, i + xoffset, j + yoffset, fluid, liquidRand);
		}
		if(random.nextDouble() < 0.2) {
			if(random.nextInt(2)-1 == 0) xoffset += Math.round(random.nextDouble()) * 2 - 1;
			else yoffset += Math.round(random.nextDouble()) * 2 - 1;
					
			safeSetAir(g, i + xoffset, j + yoffset, fluid, liquidRand);
		}
	}
	
	private void makeLargeCave(Grid g, int i, int j, Fluid fluid, double liquidRand){
		double rand = random.nextInt(5) + 5;
		
		g.setTile("airTile", 0, i, j);
		
		for(int k = 0; k < rand + 1; k++) {
			int xoffset = 0, yoffset = 0;
			
			for(int l = 0; l < rand; l++) {
				if(random.nextInt(2)-1 == 0) xoffset += Math.round(random.nextDouble()) * 2 - 1;
				else yoffset += Math.round(random.nextDouble()) * 2 - 1;
						
				safeSetAir(g, i + xoffset, j + yoffset, fluid, liquidRand);
			}
		}
	}
	
	private void makeCavern(Grid g, int i, int j, Fluid fluid, double liquidRand){
		int xoffset = 0, yoffset = 0;
		
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
								
						safeSetAir(g, i + xoffset, j + yoffset, fluid, liquidRand);
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
												
						safeSetAir(g, i + xoffset, j + yoffset, fluid, liquidRand);
					}
				}
			}
		}
	}
	
	
	private void safeSetAir(Grid g, int x, int y, Fluid fluid, double fluidAmount){
		if(x > g.dimensions.getX() || y > g.dimensions.getY() || x < 0 || y < 0) return;

		Vector tilePos = new Vector(x,y);
		
		if(g.getTile(tilePos) instanceof MineralTile) {
			g.setTile("airTile", 0, tilePos);
			if(fluid != null) ((AirTile) g.getTile(tilePos)).fillWithFluid(fluid, (int) fluidAmount);
		}
	}
	
}
