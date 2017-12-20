package me.hii488.volcanoRush.additionalTickers;

import java.util.ArrayList;

import me.hii488.handlers.ContainerHandler;
import me.hii488.interfaces.ITickable;
import me.hii488.misc.Grid;
import me.hii488.misc.Vector;
import me.hii488.objects.tiles.BaseTile;
import me.hii488.volcanoRush.containers.volcanoes.Volcano;
import me.hii488.volcanoRush.misc.LightSource;
import me.hii488.volcanoRush.objects.tiles.LightTile;

public class LightHandler implements ITickable{
	
	public ArrayList<LightSource> sources = new ArrayList<LightSource>();
	
	@Override
	public void updateOnTick() {
		if(ContainerHandler.getLoadedContainer() instanceof Volcano) {
			Grid g = ContainerHandler.getLoadedContainer().grid;
			
			for(LightSource l : sources) {
				Vector v = l.getPosition().clone();
				int r = l.getRadius(v.getY());
				
				int jStart = v.getY()-r < 0 ? -v.getY() : -r;
				int iEnd = v.getX() + r > g.dimensions.getX() ? g.dimensions.getX()-v.getX() : r;
				int jEnd = v.getY()+r > g.dimensions.getY() ? g.dimensions.getY()-v.getY() : r;
				
				for(int i = v.getX()-r < 0 ? -v.getX() : -r; i < iEnd; i++) {
					int isq = i*i; // Ever so slightly reduce amount of computations needed
					
					for(int j = jStart; j < jEnd; j++) {
						int distanceSq = isq + j*j;
						
						if(distanceSq < r*r) { // TODO: Test whether this implementation is faster than sqrt.
							int absI = Math.abs(i), absJ = Math.abs(j);
							double appDist = 0;
							if(absI + absJ != 0) appDist= (distanceSq + absI*absJ)/(absI + absJ); // This gives similar results to sqrt, especially for low i or low j 
							
							BaseTile t = g.getTile(v.getX() + i, v.getY() + j);
							if(t instanceof LightTile) {
								((LightTile) t).raiseLightTo((int) (l.getLightIntensity(v.getY()) - (appDist * l.getDropOff(v.getY()))));
							}
						}
					}
				}
			}
		}
	}

	public static void setInitialLight() {
		Grid g = ContainerHandler.getLoadedContainer().grid;
		for(int j = 0; j < g.dimensions.getY(); j++) {
			for(int i = 0; i < g.dimensions.getX(); i++) {
				((LightTile) g.getTile(i, j)).setLowestLight(j< 90 ? 110 - j : 20);
			}
		}
	}
	
	@Override
	public void updateOnSec() {}

	@Override
	public void updateOnRandTick() {}

	@Override
	public float randTickChance() {return 0;}
	
}
