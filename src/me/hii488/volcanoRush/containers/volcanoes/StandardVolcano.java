package me.hii488.volcanoRush.containers.volcanoes;

import me.hii488.volcanoRush.containers.algorithms.AllDirtAlg;

public class StandardVolcano extends Volcano{
	
	public StandardVolcano(){
		super();
		this.identifier = "standardVolcano";
		this.grid.setupGrid(20, 40);
		this.mineralSpawner = new AllDirtAlg();
	}
	
}
