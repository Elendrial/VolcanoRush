package me.hii488.volcanoRush.fluids;

import java.awt.Graphics;

import me.hii488.handlers.TextureHandler;
import me.hii488.objects.entities.BaseEntity;
import me.hii488.objects.tiles.BaseTile;

public abstract class Fluid{
	
	public FlowDirection flowDir;
	public String identifier, textureName;
	public boolean breathable;
	
	public Fluid(){
		flowDir = FlowDirection.NONE;
		identifier = "";
		textureName = "";
		breathable = false;
	}
	
	public void addOverlay(Graphics g, int amount){
		if(amount != 0)	g.drawImage(TextureHandler.getTexture(identifier + "_" + (amount == 100 ? 3 : (int) (amount/25))), 0, 0, null);
	}
	
	
	public abstract void onEnterTile(BaseTile t);
	public abstract void onLeaveTile(BaseTile t);
	public abstract void onContactWith(BaseEntity e, int level);
	public abstract void updateOnTick(int x, int y, int level);
	public abstract void updateOnSec(int x, int y, int level);
	
	public enum FlowDirection{
		DOWN(1), 
		UP(-1), 
		NONE(0);
		
		private int deltaY;
		
		FlowDirection(int deltaY){
			this.deltaY = deltaY;
		}
		
		public int getDeltaY(){
			return deltaY;
		}
		
	}

}
