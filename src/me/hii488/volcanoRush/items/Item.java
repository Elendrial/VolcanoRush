package me.hii488.volcanoRush.items;

import java.awt.image.BufferedImage;

import me.hii488.interfaces.IInputUser;
import me.hii488.interfaces.ITickable;
import me.hii488.volcanoRush.dataTypes.DeathCause;
import me.hii488.volcanoRush.fluids.Fluid;

public class Item implements IInputUser, ITickable{
	
	public String name = "";
	public String identifier = "";
	public int scoreMultiplier = 1;
	public int priority = 10; // lower = higher priority.
	public boolean droppable = false;
	public int cost = -1;
	
	public BufferedImage texture;
	
	// Override-able methods
	public void onEquip(){}
	public void inFluid(Fluid fluid){}
	public boolean onDeath(DeathCause cause){return true;} // Return whether player still dies
	public boolean onMovement(){return true;} // Return whether to also use existing movement code
	public void onGeneration(){}
	public void updateOnTick() {}
	public void updateOnSec() {}
	
	public String getName() {
		return name;
	}
	public Item setName(String name) {
		this.name = name;
		return this;
	}
	public String getIdentifier() {
		return identifier;
	}
	public Item setIdentifier(String identifier) {
		this.identifier = "item_" + identifier;
		return this;
	}
	public int getScoreMultiplier() {
		return scoreMultiplier;
	}
	public Item setScoreMultiplier(int scoreMultiplier) {
		this.scoreMultiplier = scoreMultiplier;
		return this;
	}
	public int getPriority() {
		return priority;
	}
	public Item setPriority(int priority) {
		this.priority = priority;
		return this;
	}
	public boolean isDroppable() {
		return droppable;
	}
	public Item setDroppable(boolean droppable) {
		this.droppable = droppable;
		return this;
	}
	public int getCost() {
		return cost;
	}
	public Item setCost(int cost) {
		this.cost = cost;
		return this;
	}
	public BufferedImage getTexture() {
		return texture;
	}
	public Item setTexture(BufferedImage texture) {
		this.texture = texture;
		return this;
	}
	@Override
	public float randTickChance() {return 0;}
	@Override
	public void updateOnRandTick() {}
}
