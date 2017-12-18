package me.hii488.volcanoRush.objects.tiles;

import java.awt.Color;
import java.awt.Graphics;

import me.hii488.controllers.GameController;
import me.hii488.graphics.Camera;
import me.hii488.misc.Settings;
import me.hii488.objects.tiles.BaseTile;

public class LightTile extends BaseTile{

	public int lightPercent = 1;
	
	public LightTile() {
		super();
	}
	
	public LightTile(LightTile l) {
		super(l);
		this.lightPercent = l.lightPercent;
	}
	
	public void render(Graphics g) {
		super.render(g);
		if(renderPosA.getX() < GameController.windows[0].width && renderPosB.getX() > 0){
			if(renderPosA.getY() < GameController.windows[0].height && renderPosB.getY() > 0){
				renderLight(g);
			}
		}
	}
	
	public void renderLight(Graphics g) {
		try{
			g.setColor(new Color(0,0,0,(int) ((100-lightPercent)*2.55)));
		}
		catch(Exception e) {
			System.out.println((int)((100-lightPercent)*2.55) + "::" + lightPercent);
			
		}
		//g.setColor(new Color(0,0,0, 105));
		//g.fillRect(renderPosA.getX(), renderPosA.getY(), (int)(Settings.Texture.tileSize * Camera.scale), (int)(Settings.Texture.tileSize * Camera.scale));
	}
	
	public void raiseLightTo(int light) {
		if(lightPercent < light) lightPercent = light;
		if(lightPercent > 100) lightPercent = 100;
		if(lightPercent < 1) lightPercent = 1;
	}
	
	public void lowerLightTo(int light) {
		if(lightPercent > light) lightPercent = light;
		if(lightPercent > 100) lightPercent = 100;
		if(lightPercent < 1) lightPercent = 1;
	}
	
	@Override
	public BaseTile clone() {return new LightTile(this);}
	
	@Override
	public float randTickChance() {return 0;}

	@Override
	public void updateOnTick() {}

	@Override
	public void updateOnSec() {}

	@Override
	public void updateOnRandTick() {}

	@Override
	public void initVars() {}

	@Override
	public void onLoad() {}

}
