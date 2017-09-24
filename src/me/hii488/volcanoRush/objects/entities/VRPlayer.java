package me.hii488.volcanoRush.objects.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import me.hii488.controllers.GameController;
import me.hii488.graphics.Camera;
import me.hii488.handlers.ContainerHandler;
import me.hii488.handlers.TextureHandler;
import me.hii488.misc.Grid;
import me.hii488.misc.Settings;
import me.hii488.misc.Vector;
import me.hii488.objects.entities.Player;
import me.hii488.objects.tiles.BaseTile;
import me.hii488.volcanoRush.containers.menus.MainMenu;
import me.hii488.volcanoRush.objects.tiles.AirTile;
import me.hii488.volcanoRush.objects.tiles.MineralTile;

public class VRPlayer extends Player{
	
	public BufferedImage[] breathOverlay;
	public boolean movementAllowed = false;
	public int breath = 120;
	
	@Override
	public void initVars() {
		super.initVars();
		this.textureName = "player.png";
		this.usesEngineMovement = false;
		this.speed = 3;
		
		breathOverlay = new BufferedImage[4];
		for(int i = 0; i < breathOverlay.length; i++)
			breathOverlay[i] = TextureHandler.loadTexture("textures/overlays/", "breathOverlay_" + i + ".png", this);
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
			checkBreath();
		}
	}
	
	public void kill(){
		
	}
	
	protected float previousMovement = 0;
	protected int maxFallSpeed = 15;
	protected int jumpSpeed = -8;
	public void addGravity(Vector v){
		if(v.getY() < 0 && previousMovement == 0) v.setY(jumpSpeed);
		else{
			v.setY(previousMovement >= maxFallSpeed ? maxFallSpeed : previousMovement + 1f);
		}
	}
	
	
	public void dig(Vector direction){
		Grid g = ContainerHandler.getLoadedContainer().grid;
		BaseTile t;
		
		if(Math.abs(direction.getX() + direction.getY()) == 1){ // If direction is vertical/horizontal 
			t = g.getTile(g.getGridPosAtVector(position.clone().addToLocation(currentTexture.getWidth()/2, currentTexture.getHeight()/2)).addToLocation(direction));
			if(t instanceof MineralTile) ((MineralTile) t).onDig();
			else if(direction.getY() == 0){ // If you're over half a tile in x direction, you can reach the next tile over.
				t = g.getTile(g.getGridPosAtVector(position.clone().addToLocation(currentTexture.getWidth()/2, currentTexture.getHeight()/2).addToLocation(Settings.Texture.tileSize/2, 0)).addToLocation(direction));
				if(t instanceof MineralTile) ((MineralTile) t).onDig();
			}
			return;
		}
		
		// If direction is diagonal, must have mined one of the blocks around it first
		t = g.getTile(g.getGridPosAtVector(position.clone().addToLocation(currentTexture.getWidth()/2, currentTexture.getHeight()/2)).addToLocation(0, direction.getY()));
		if(t instanceof MineralTile){
			t = g.getTile(g.getGridPosAtVector(position.clone().addToLocation(currentTexture.getWidth()/2, currentTexture.getHeight()/2)).addToLocation(direction.getX(), 0));
			if(t instanceof MineralTile){ // If neither side of diagonal tile is free, mine the one above/below it
				((MineralTile) t).onDig();
				return;
			}
		}
		
		t =  g.getTile(g.getGridPosAtVector(position.clone().addToLocation(currentTexture.getWidth()/2, currentTexture.getHeight()/2)).addToLocation(direction));
		if(t instanceof MineralTile) ((MineralTile) t).onDig();
	}
	
	public void checkBreath(){
		BaseTile t = ContainerHandler.getLoadedContainer().grid.getTileAtVector(position);
		boolean drowning = false;
		if(t instanceof AirTile){
			for(int i = 0; i < ((AirTile) t).fluidContent.length; i++){
				if(((AirTile) t).fluidContent[i] > 50) drowning = true;
			}
		}
		
		if(drowning) breath--;
		else breath = 120;
		
		if(breath <= 0) this.kill();
	}
	
	public void render(Graphics g){
		if(!(ContainerHandler.getLoadedContainer() instanceof MainMenu)){
			super.render(g);
			
			if(breath != 120){
				if(breath > 90) g.drawImage(breathOverlay[0], position.getX() - Camera.cameraPosition.getX() + 16, position.getY() - Camera.cameraPosition.getY() - 5, null);
				else if(breath > 60) g.drawImage(breathOverlay[1], position.getX() - Camera.cameraPosition.getX() + 16, position.getY() - Camera.cameraPosition.getY() - 5, null);
				else if(breath > 30) g.drawImage(breathOverlay[2], position.getX() - Camera.cameraPosition.getX() + 16, position.getY() - Camera.cameraPosition.getY() - 5, null);
				else if(breath > 0) g.drawImage(breathOverlay[3], position.getX() - Camera.cameraPosition.getX() + 16, position.getY() - Camera.cameraPosition.getY() - 5, null);
			}
			
			g.setColor(Color.white);
			g.fillRect(0, 0, 1000, 15);
			g.setColor(Color.black);
			g.drawString("player: " + position + ";;   camera: " + Camera.cameraPosition + ";;  tile: " + ContainerHandler.getLoadedContainer().grid.getTileAtVector(position).identifier + ";;   breath: " + breath, 2, 12);
		}
	}
	
	public void keyDown(KeyEvent arg0){
		switch (arg0.getKeyCode()) {
		case 37:
			queuedMovement.setX(-speed);
			break;
		case 38:
			queuedMovement.setY(-speed);
			break;
		case 39:
			queuedMovement.setX(speed);
			break;
		}
	}
	
	public void keyUp(KeyEvent arg0){
		switch (arg0.getKeyCode()) {
		case 37:
			queuedMovement.setX(0);
			break;
		case 38:
			queuedMovement.setY(0);
			break;
		case 39:
			queuedMovement.setX(0);
			break;
		}
	}	

	@Override
	public void keyPressed(KeyEvent arg0) {
		keyDown(arg0);
		switch(arg0.getKeyCode()){
		case KeyEvent.VK_A:
			dig(new Vector(-1,0));
			break;
		case KeyEvent.VK_Q:
			dig(new Vector(-1,-1));
			break;
		case KeyEvent.VK_W:
			dig(new Vector(0,-1));
			break;
		case KeyEvent.VK_E:
			dig(new Vector(1,-1));
			break;
		case KeyEvent.VK_D:
			dig(new Vector(1,0));
			break;
		case KeyEvent.VK_C:
			dig(new Vector(1,1));
			break;
		case KeyEvent.VK_X:
			dig(new Vector(0,1));
			break;
		case KeyEvent.VK_Z:
			dig(new Vector(-1,1));
			break;
		}
	}
	
}
