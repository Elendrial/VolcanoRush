package me.hii488.volcanoRush.containers.algorithms;

import me.hii488.controllers.GameController;
import me.hii488.misc.Grid;
import me.hii488.volcanoRush.objects.FluidType;
import me.hii488.volcanoRush.objects.OreType;

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
		
		// TODO: Make rope tile that you can use to escape volcano and place instead of this air.
		g.setTile("airTile", 0, g.dimensions.getX()/2-1, 0);
	}

	@Override
	public OreType getOreType(int x, int y) {
		double rand = GameController.rand.nextDouble();
		// TODO: Come up with a better algorithm than this
		
		
		return OreType.NONE;
	}

	@Override
	public int getFluidAmount(FluidType t, int x, int y) {return 0;}

}
