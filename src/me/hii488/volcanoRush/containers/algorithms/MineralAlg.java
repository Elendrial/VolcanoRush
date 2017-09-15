package me.hii488.volcanoRush.containers.algorithms;

import me.hii488.misc.Vector;
import me.hii488.volcanoRush.objects.OreType;

public abstract class MineralAlg {
	
	public OreType getOreType(Vector v){
		return getOreType(v.getX(), v.getY());
	}
	
	public abstract OreType getOreType(int x, int y);
	
}
