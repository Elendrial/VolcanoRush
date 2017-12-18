package me.hii488.volcanoRush.additionalTickers;

import java.util.ArrayList;

import me.hii488.handlers.ContainerHandler;
import me.hii488.interfaces.ITickable;
import me.hii488.misc.Grid;
import me.hii488.volcanoRush.containers.volcanoes.Volcano;
import me.hii488.volcanoRush.misc.LightSource;
import me.hii488.volcanoRush.objects.tiles.LightTile;

public class LightHandler implements ITickable{
	
	public ArrayList<LightSource> sources = new ArrayList<LightSource>();
	
	@Override
	public void updateOnTick() {
		if(ContainerHandler.getLoadedContainer() instanceof Volcano) {
			for(LightSource l : sources) {
				
			}
		}
	}

	public static void setInitialLight() {
		System.out.println("Lighting");
		Grid g = ContainerHandler.getLoadedContainer().grid;
		for(int j = 0; j < g.dimensions.getY(); j++) {
			for(int i = 0; i < g.dimensions.getX(); i++) {
				((LightTile) g.getTile(i, j)).raiseLightTo(110 - j);
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
