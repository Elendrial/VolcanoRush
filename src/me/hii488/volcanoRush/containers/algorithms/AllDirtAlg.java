package me.hii488.volcanoRush.containers.algorithms;

import me.hii488.misc.Grid;
import me.hii488.misc.Vector;
import me.hii488.objects.tiles.BaseTile;
import me.hii488.volcanoRush.objects.tiles.AirTile;
import me.hii488.volcanoRush.objects.tiles.MineralTile;
import me.hii488.volcanoRush.tileExtras.FluidType;
import me.hii488.volcanoRush.tileExtras.OreType;

// Note this is just an alg for testing, it wont be the final one etc etc
public class AllDirtAlg extends MineralAlg{

	@Override
	public void populate(Grid g){
		g.fillRectWithTile("dirtTile", 0, 0, g.dimensions.getX(), g.dimensions.getY());
		g.wallRectWithTile("unbreakableTile", 0, 0, g.dimensions.getX(), g.dimensions.getY());
		g.fillRectWithTile("airTile", 1, 1, 0, g.dimensions.getX() - 1, 3);
		g.fillRectWithTile("airTile", 1, 34, g.dimensions.getX() - 1, 35);
		
		for(BaseTile[] b : g.grid) for(BaseTile t : b){
			if(t instanceof MineralTile) ((MineralTile) t).setOreType(getOreType(t.gridPosition));
			else if(t instanceof AirTile){
				((AirTile) t).fluidContent[0] = getFluidAmount(FluidType.GAS, t.gridPosition);
				((AirTile) t).fluidContent[1] = getFluidAmount(FluidType.WATER, t.gridPosition);
			}
		}
	}
	
	@Override
	public OreType getOreType(int x, int y) {
		if(y == 10) return OreType.COPPER;
		if(y == 15) return OreType.IRON;
		if(y == 20) return OreType.SILVER;
		if(y == 25) return OreType.GOLD;
		if(y == 30) return OreType.DIAMOND;
		return OreType.NONE;
	}
	
	public int getFluidAmount(FluidType t, Vector v) { return getFluidAmount(t, v.getX(), v.getY());}
	
	public int getFluidAmount(FluidType t, int x, int y) {
		if(t == FluidType.WATER && y == 34) return 100;
		return 0;
	}
	
}
