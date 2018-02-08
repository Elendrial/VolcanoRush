package me.hii488.volcanoRush.dataTypes;

import me.hii488.misc.Vector;

public interface LightSource {
	
	public int getLightIntensity(int depth);
	public int getRadius(int depth);
	public float getDropOff(int depth);
	public Vector getPosition();
	
}
