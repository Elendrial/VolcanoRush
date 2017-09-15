package me.hii488.volcanoRush.containers.algorithms;

import me.hii488.misc.Grid;
import me.hii488.volcanoRush.objects.OreType;

public class AllDirtAlg extends MineralAlg{

	@Override
	public void populate(Grid g){
		g.fillRectWithTile("dirtTile", 0, 0, g.dimensions.getX(), g.dimensions.getY());
	}
	
	@Override
	public OreType getOreType(int x, int y) {
		return null;
	}

}
