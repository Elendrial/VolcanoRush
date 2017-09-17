package me.hii488.volcanoRush.objects.entities;

import me.hii488.handlers.ContainerHandler;
import me.hii488.misc.Grid;
import me.hii488.misc.Settings;
import me.hii488.misc.Vector;
import me.hii488.objects.entities.BaseEntity;

public abstract class GravityEntity extends BaseEntity{
	
	public GravityEntity(){
		super();
		maxFallSpeed = 13;
		acceleration = 1;
	}
	public GravityEntity(GravityEntity e){super(e);}
	
	public void updateOnTick(){
		super.updateOnTick();
		position.addToLocation(addGravity());
	}
	
	public Vector previousMovement = new Vector(0, 0);
	protected int maxFallSpeed;
	protected float acceleration;
	public Vector addGravity(){ // Mostly code is copied from VRPlayer and PLayer
		Vector v = new Vector(0,0);
		
		v.setY(previousMovement.getY() >= maxFallSpeed ? maxFallSpeed : previousMovement.getY() + acceleration);
		v.setX((previousMovement.getAbsX() * 0.7f));
		
		Grid g = ContainerHandler.getLoadedContainer().grid;
		Vector p = position.clone(), a;
		
		if(v.getAbsY() < 0){
			a = p.clone().addToLocation(0, v.getAbsY()); // Test -ve y movement
			if(g.getTileAtVector(a).isCollidable || g.getTileAtVector(a.clone().addToLocation(collisionBox.width-1, 0)).isCollidable){
				v.setY(position.getY() %Settings.Texture.tileSize); // Get dist between top of player and above tile.
			}
		}
		else if(v.getAbsY() > 0){
			a = p.clone().addToLocation(0, v.getAbsY()); // Test +ve y movement
			if(g.getTileAtVector(a.clone().addToLocation(0, collisionBox.height-1)).isCollidable || g.getTileAtVector(a.clone().addToLocation(collisionBox.width-1, collisionBox.height-1)).isCollidable){
				v.setY(Settings.Texture.tileSize - collisionBox.height - position.getY() % Settings.Texture.tileSize); // Get dist between bottom of player and tile below.
			}
		}
		
		if(v.getAbsX() < 0){
			a = p.clone().addToLocation(v.getAbsX(), 0); // Test -ve x movement
			if(g.getTileAtVector(a).isCollidable || g.getTileAtVector(a.clone().addToLocation(0, collisionBox.height-1)).isCollidable){
				v.setX(position.getX() % Settings.Texture.tileSize); // Get dist between left of player and left tile.
			}
		}
		else if(v.getAbsX() > 0){
			a = p.clone().addToLocation(v.getAbsX(), 0); // Test +ve x movement
			if(g.getTileAtVector(a.clone().addToLocation(collisionBox.width-1, 0)).isCollidable || g.getTileAtVector(a.clone().addToLocation(collisionBox.width-1, collisionBox.height-1)).isCollidable){
				v.setX(Settings.Texture.tileSize - collisionBox.width - position.getX() % Settings.Texture.tileSize); // Get dist between right of player and right tile.
			}
		}
		
		previousMovement = v.clone();
		
		return v;
		
	}
	
}
