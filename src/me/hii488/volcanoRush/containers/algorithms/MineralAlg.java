package me.hii488.volcanoRush.containers.algorithms;

import me.hii488.misc.Grid;
import me.hii488.misc.Vector;
import me.hii488.volcanoRush.objects.OreType;

public abstract class MineralAlg {
	
	public abstract void populate(Grid g);
	
	public OreType getOreType(Vector v){
		return getOreType(v.getX(), v.getY());
	}
	
	public abstract OreType getOreType(int x, int y);
	
}
