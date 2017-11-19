package me.hii488.volcanoRush.containers.volcanoes;

import me.hii488.volcanoRush.containers.algorithms.StandardAlg;

public class StandardVolcano extends Volcano{
	
	public StandardVolcano(){
		super();
		this.identifier = "standardVolcano";
		this.grid.setupGrid(100, 1000);
		this.mineralSpawner = new StandardAlg();
	}
	
}
