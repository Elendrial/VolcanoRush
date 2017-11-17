package me.hii488.volcanoRush.items;

import java.awt.Graphics;

import me.hii488.volcanoRush.objects.FluidType;

public class ItemScoreTriple extends Item {

	public ItemScoreTriple(){
		this.name = "3x Multipler";
		this.identifier = "scoreTriple";
		this.scoreMultiplier = 3;
		this.droppable = false;
		this.cost = 200;
	}
	
	@Override
	public void inFluid(FluidType fluid) {}

	@Override
	public boolean onMovement() {return true;}

	@Override
	public void onGeneration() {}

	@Override
	public void render(Graphics g) {}

	@Override
	public void onEquip() {}

}