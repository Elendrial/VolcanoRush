package me.hii488.volcanoRush.items;

import java.awt.Graphics;

import me.hii488.volcanoRush.objects.FluidType;

public class ItemScoreDouble extends Item {

	public ItemScoreDouble(){
		this.name = "2x Multipler";
		this.scoreMultiplier = 2;
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