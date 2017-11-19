package me.hii488.volcanoRush.containers.algorithms;

import me.hii488.controllers.GameController;
import me.hii488.misc.Grid;
import me.hii488.objects.tiles.BaseTile;
import me.hii488.volcanoRush.objects.tiles.MineralTile;
import me.hii488.volcanoRush.tileExtras.FluidType;
import me.hii488.volcanoRush.tileExtras.OreType;

public class StandardAlg extends MineralAlg{

	@Override
	public void populate(Grid g) {
		g.wallRectWithTile("unbreakableTile", 0, 0, g.dimensions.getX(), g.dimensions.getY());
		g.fillRectWithTile("dirtTile", 1, 1, g.dimensions.getX()-1, g.dimensions.getY()-1);
		
		g.fillRectWithTile("airTile", 0, g.dimensions.getX()/2-1, 1, g.dimensions.getX()/2+1, 3);
		
		for(int i = 0 ; i < g.dimensions.getX()/4; i++){
			g.fillRectWithTile("airTile", 1, 0, i, g.dimensions.getX()/2-(2*i), i+1);
			g.fillRectWithTile("airTile", 1, g.dimensions.getX()/2+(i*2), i, g.dimensions.getX(), i+1);
			g.fillRectWithTile("unbreakableTile", g.dimensions.getX()/2 - (2*i + 2), i, g.dimensions.getX()/2 - (2*i), i+1);
			g.fillRectWithTile("unbreakableTile", g.dimensions.getX()/2 + (2*i), i, g.dimensions.getX()/2 + (2*i+2), i+1);
		}
		
		g.setTile("ropeTile", g.dimensions.getX()/2-1, 0);
		g.setTile("ropeTile", g.dimensions.getX()/2-1, 1);
		
		double rand;
		double rockChance; 
		for(int j = 0; j < g.dimensions.getY(); j++) { // x and y 'wrong' way around to go down by rows so rockchance can be updated easily.
			rockChance = Math.tanh(j * 0.01) * 0.01;
			for(int i = 0; i < g.dimensions.getX(); i++) {
				rand = GameController.rand.nextDouble();
				if(rand < rockChance) g.setTile("rockTile", i, j);
			}
		}
		
		for(BaseTile[] b : g.grid) for(BaseTile t : b){
			if(t instanceof MineralTile) ((MineralTile) t).setOreType(getOreType(t.gridPosition));
		}
	}

	@Override
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

	@Override
	public int getFluidAmount(FluidType t, int x, int y) {return 0;}

}
