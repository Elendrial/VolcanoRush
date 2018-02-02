package me.hii488.volcanoRush.containers.volcanoes;

import me.hii488.volcanoRush.containers.generationAlgs.TestingAlg;

public class StandardVolcano extends Volcano{
	
	public StandardVolcano(){
		super();
		this.identifier = "standardVolcano";
		this.grid.setupGrid(100, 1000);
		this.mineralSpawner = new TestingAlg();
	}
	
}
