package me.hii488.volcanoRush.misc;

public interface LightSource {
	
	public int getLightIntensity(int depth);
	public int getRadius(int depth);
	public float getDropOff(int depth);
	
}
