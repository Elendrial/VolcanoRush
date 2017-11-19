package me.hii488.volcanoRush.items;

import java.awt.image.BufferedImage;

import me.hii488.interfaces.IInputUser;
import me.hii488.volcanoRush.tileExtras.FluidType;

public abstract class Item implements IInputUser{
	
	public String name = "";
	public String identifier = "";
	public int scoreMultiplier = 1;
	public int priority = 10; // lower = higher priority.
	public boolean droppable = false;
	public int cost = -1;
	
	public BufferedImage texture;
	
	public abstract void onEquip();
	
	public abstract void inFluid(FluidType fluid);
	
	// Return whether to also use existing movement code
	public abstract boolean onMovement();
	
	public abstract void onGeneration();
	
}
