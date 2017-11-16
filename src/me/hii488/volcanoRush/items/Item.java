package me.hii488.volcanoRush.items;

import java.awt.Graphics;

import me.hii488.interfaces.IInputUser;
import me.hii488.volcanoRush.objects.FluidType;

public abstract class Item implements IInputUser{
	
	public String name;
	public int scoreMultiplier;
	public int priority;
	public boolean droppable;
	public int cost;
	
	public abstract void onEquip();
	
	public abstract void inFluid(FluidType fluid);
	
	// Return whether to also use existing movement code
	public abstract boolean onMovement();
	
	public abstract void onGeneration();
	
	public abstract void render(Graphics g);
	
}
