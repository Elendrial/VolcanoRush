package me.hii488.volcanoRush.objects.tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.hii488.handlers.TextureHandler;
import me.hii488.objects.tiles.BaseTile;

public class LightTile extends BaseTile{

	public int lightPercent = 1;
	
	public LightTile() {super();}
	
	public LightTile(LightTile l) {
		super(l);
		this.lightPercent = l.lightPercent;
	}
	
	public void render(Graphics g) {
		textureName = sanitizedName + "_" + currentState + "_" + lightPercent;
		
		if(!TextureHandler.containsTexture(textureName)) addLightToImage(TextureHandler.getTexture(sanitizedName + "_" + currentState));
		
		g.drawImage(getTexture(), renderPosA.getX(), renderPosA.getY(), null);				
	}
	
	public void addLightToImage(BufferedImage i) {
		BufferedImage img = TextureHandler.cloneTexture(i);
		Graphics g = img.createGraphics();
		g.setColor(new Color(0,0,0,(int) ((100-lightPercent)*2.55)));
		g.fillRect(0, 0, img.getWidth(), img.getHeight());
		g.dispose();
		
		TextureHandler.addTexture(img, textureName);
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
