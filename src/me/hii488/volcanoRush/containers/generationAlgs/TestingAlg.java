package me.hii488.volcanoRush.containers.generationAlgs;

import me.hii488.misc.Grid;
import me.hii488.misc.Vector;
import me.hii488.objects.tiles.BaseTile;
import me.hii488.volcanoRush.dataTypes.OreType;
import me.hii488.volcanoRush.fluids.Fluid;
import me.hii488.volcanoRush.objects.tiles.AirTile;
import me.hii488.volcanoRush.objects.tiles.MineralTile;
import me.hii488.volcanoRush.registers.FluidRegistry;

// Note this is just an alg for testing, it wont be the final one etc etc
public class TestingAlg extends GenerationAlg{

	public void populate(Grid g, long seed){
		populate(g);
	}
	
	@Override
	public void populate(Grid g){
		g.fillRectWithTile("dirtTile", 0, 0, g.dimensions.getX(), g.dimensions.getY());
		g.wallRectWithTile("unbreakableTile", 0, 0, g.dimensions.getX(), g.dimensions.getY());
		g.fillRectWithTile("airTile", 1, 1, 0, g.dimensions.getX() - 1, 3);
		g.fillRectWithTile("airTile", 30, 4, g.dimensions.getX() - 30, 5);
		
		for(BaseTile[] b : g.grid) for(BaseTile t : b){
			if(t instanceof MineralTile) ((MineralTile) t).setOreType(getOreType(t.gridPosition));
			else if(t instanceof AirTile){
				for(Fluid f : FluidRegistry.fluids.values())
					((AirTile) t).fluidContent.put(f, getFluidAmount(f, t.gridPosition));
				
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
	
	public int getFluidAmount(Fluid t, Vector v) { return getFluidAmount(t, v.getX(), v.getY());}
	
	public int getFluidAmount(Fluid t, int x, int y) {
		if(t.identifier.equals("water") && y >= 4) return 50;
		return 0;
	}
	
}
