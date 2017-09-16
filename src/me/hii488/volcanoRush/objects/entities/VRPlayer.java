package me.hii488.volcanoRush.objects.entities;

import java.awt.Color;
import java.awt.Graphics;

import me.hii488.controllers.GameController;
import me.hii488.graphics.Camera;
import me.hii488.misc.Vector;
import me.hii488.objects.entities.Player;

public class VRPlayer extends Player{
	
	public boolean movementAllowed = false;
	
	@Override
	public void initVars() {
		super.initVars();
		this.textureName = "player.png";
		this.usesEngineMovement = false;
		this.speed = 3;
	}
	
	@Override
	public void updateOnTick() {
		super.updateOnTick();
		if(movementAllowed){
			addGravity(queuedMovement);
			Vector v = allowedMovement(queuedMovement);
			previousMovement = v.getY();
			position.addToLocation(v);
			Camera.cameraPosition = new Vector(position.getX() - GameController.windows[0].width/2, position.getY() - GameController.windows[0].height/2);
		}
	}
	
	// Rather than completely rewrite the collision and movement, we just intercept the inputs so we get what we want.
	protected float previousMovement = 0;
	protected int maxFallSpeed = 15;
	protected int jumpSpeed = -6;
	public void addGravity(Vector v){
		if(v.getY() < 0 && previousMovement == 0) v.setY(jumpSpeed);
		else{
			v.setY(previousMovement >= maxFallSpeed ? maxFallSpeed : previousMovement + 1f);
		}
	}
	
}
