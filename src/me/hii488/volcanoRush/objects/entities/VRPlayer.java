package me.hii488.volcanoRush.objects.entities;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import me.hii488.controllers.GameController;
import me.hii488.graphics.Camera;
import me.hii488.handlers.ContainerHandler;
import me.hii488.handlers.TextureHandler;
import me.hii488.misc.Grid;
import me.hii488.misc.Settings;
import me.hii488.misc.Vector;
import me.hii488.objects.entities.Player;
import me.hii488.objects.entities.RenderEntity;
import me.hii488.objects.tiles.BaseTile;
import me.hii488.volcanoRush.VolcRush;
import me.hii488.volcanoRush.containers.volcanoes.Volcano;
import me.hii488.volcanoRush.dataTypes.DeathCause;
import me.hii488.volcanoRush.dataTypes.LightSource;
import me.hii488.volcanoRush.fluids.Fluid;
import me.hii488.volcanoRush.objects.entities.render.VRPlayerRenderEntity;
import me.hii488.volcanoRush.objects.tiles.AirTile;
import me.hii488.volcanoRush.objects.tiles.MineralTile;
import me.hii488.volcanoRush.registers.FluidRegistry;
import me.hii488.volcanoRush.registers.ItemRegistry;

public class VRPlayer extends Player implements LightSource{
	
	public HashMap<Fluid, Boolean> drowning;
	public boolean movementAllowed = false;
	private boolean invincible = false;
	public int maxBreath = 120;
	public int breath = 120;
	
	@Override
	public void initVars() {
		super.initVars();
		this.textureName = "player.png";
		this.usesEngineMovement = false;
		this.speed = 3;
		
		for(int i = 0; i < 4; i++)
			TextureHandler.loadTexture("textures/overlays/", "breathOverlay_" + i + ".png", this, "breathOverlay_" + i);
		
		drowning = new HashMap<Fluid, Boolean>();
		for(String s : FluidRegistry.fluids.keySet())
			drowning.put(FluidRegistry.fluids.get(s), false);
	}
	
	public void onLoad() {}
	
	public void resetPlayer(){
		ItemRegistry.unequipAll();
		maxBreath = 120;
		breath = 120;
		speed = 3;
		movementAllowed = false;
		invincible = false;
	}
	
	@Override
	public void updateOnTick() {
		super.updateOnTick();
		if(movementAllowed){
			addGravity(queuedMovement);
			Vector v = allowedMovement(queuedMovement);
			previousMovement = v.getY();
			position.addToLocation(v);
			updateCamera();
			checkBreath();
		}
	}
	
	public void updateCamera(){
		Vector possiblePosition = new Vector(position.getX() - GameController.getWindow().width/2, position.getY() - GameController.getWindow().height/2);
		Grid g = ContainerHandler.getLoadedContainer().grid;
		
		if(g.dimensions.getX() * Settings.Texture.tileSize > GameController.getWindow().width){
			if(possiblePosition.getX() < 0) possiblePosition.setX(0);
			if(possiblePosition.getX() + GameController.getWindow().width > g.dimensions.getX() * Settings.Texture.tileSize) 
				possiblePosition.setX(g.dimensions.getX() * Settings.Texture.tileSize - GameController.getWindow().width);
		}

		if(g.dimensions.getY() * Settings.Texture.tileSize > GameController.getWindow().height){
			if(possiblePosition.getY() < 0) possiblePosition.setY(0);
			if(possiblePosition.getY() + GameController.getWindow().height > g.dimensions.getY() * Settings.Texture.tileSize) 
				possiblePosition.setY(g.dimensions.getY() * Settings.Texture.tileSize - GameController.getWindow().height);
		}
		
		Camera.moveTo(possiblePosition);
	}
	
	public void kill(DeathCause cause){
		System.out.println("Deathcause: " + cause.name());
		if(ItemRegistry.onDeath(cause) && !invincible) ContainerHandler.loadNewContainer("deathMenu");
	}
	
	protected float previousMovement = 0;
	protected int maxFallSpeed = 15;
	protected int jumpSpeed = -8;
	public void addGravity(Vector v){
		if(v.getY() < 0 && previousMovement == 0) v.setY(jumpSpeed);
		else v.setY(previousMovement >= maxFallSpeed ? maxFallSpeed : previousMovement + 1f);
	}
	
	
	public void dig(Vector direction){
		Grid g = ContainerHandler.getLoadedContainer().grid;
		BaseTile t;
		
		BufferedImage currentTexture = getTexture();
		
		if(Math.abs(direction.getX() + direction.getY()) == 1){ // If direction is vertical/horizontal 
			t = g.getTile(Grid.getGridPosAtVector(position.clone().addToLocation(currentTexture.getWidth()/2, currentTexture.getHeight()/2)).addToLocation(direction));
			if(t instanceof MineralTile) ((MineralTile) t).onDig();
			else if(direction.getY() == 0){ // If you're over half a tile in x direction, you can reach the next tile over.
				t = g.getTile(Grid.getGridPosAtVector(position.clone().addToLocation(currentTexture.getWidth()/2, currentTexture.getHeight()/2).addToLocation(Settings.Texture.tileSize/2, 0)).addToLocation(direction));
				if(t instanceof MineralTile) ((MineralTile) t).onDig();
			}
			return;
		}
		
		// If direction is diagonal, must have mined one of the blocks around it first
		t = g.getTile(Grid.getGridPosAtVector(position.clone().addToLocation(currentTexture.getWidth()/2, currentTexture.getHeight()/2)).addToLocation(0, direction.getY()));
		if(t instanceof MineralTile){
			t = g.getTile(Grid.getGridPosAtVector(position.clone().addToLocation(currentTexture.getWidth()/2, currentTexture.getHeight()/2)).addToLocation(direction.getX(), 0));
			if(t instanceof MineralTile){ // If neither side of diagonal tile is free, mine the one above/below it
				((MineralTile) t).onDig();
				return;
			}
		}
		
		t =  g.getTile(Grid.getGridPosAtVector(position.clone().addToLocation(currentTexture.getWidth()/2, currentTexture.getHeight()/2)).addToLocation(direction));
		if(t instanceof MineralTile) ((MineralTile) t).onDig();
	}
	
	public void checkBreath(){
		BaseTile t = ContainerHandler.getLoadedContainer().grid.getTileAtVector(position);
		for(Fluid f : drowning.keySet()) drowning.put(f, false);
		
		if(t instanceof AirTile){
			for(Fluid f : ((AirTile) t).fluidContent.keySet()){
				if(((AirTile) t).fluidContent.get(f) > 0){
					if(((AirTile) t).fluidContent.get(f) > 50 && !f.breathable) drowning.put(f, true);
					
					ItemRegistry.inFluid(f);
				}
			}
		}
		
		boolean isDrowning = false;
		for(boolean b : drowning.values()) if(b) isDrowning = true;
		
		if(isDrowning) breath--;
		else breath = maxBreath;
		
		if(breath <= 0) this.kill(DeathCause.DROWN);
	}
	
	public void pause(){
		if(ContainerHandler.getLoadedContainer() instanceof Volcano){
			GameController.isPaused = true;
			ContainerHandler.getLoadedContainer().getGui("pauseMenu").showAll();
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
		case KeyEvent.VK_P:
			pause();
			break;
		}
		
		// Debug commands
		if(Settings.Logging.debug || VolcRush.debugCommands) {
			switch(arg0.getKeyCode()) {
			case KeyEvent.VK_K:
				this.kill(DeathCause.OTHER);
				break;
			case KeyEvent.VK_I:
				this.invincible = !this.invincible;
				break;
			}
		}
	}

	@Override
	public int getLightIntensity(int depth) {
		return 90;
	}

	@Override
	public int getRadius(int depth) {
		return 6;
	}

	@Override
	public float getDropOff(int depth) {
		return 6;
	}

	@Override
	public Vector getPosition() {
		return Grid.getGridPosAtVector(position);
	}
	
	public RenderEntity createRenderEntity(){
		return new VRPlayerRenderEntity(this);
	}
	
}
