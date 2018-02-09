package me.hii488.volcanoRush.dataTypes;

public class Seismometer {
	private boolean enabled;
	private int currentActivity, maxActivity;
	
	public Seismometer(){
		enabled = true;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public Seismometer setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	public int getCurrentActivity() {
		return currentActivity;
	}

	public Seismometer setCurrentActivity(int currentActivity) {
		this.currentActivity = currentActivity;
		return this;
	}
	
	public Seismometer addToCurrentActivity(int currentActivity) {
		this.currentActivity += currentActivity;
		return this;
	}

	public int getMaxActivity() {
		return maxActivity;
	}

	public Seismometer setMaxActivity(int maxActivity) {
		this.maxActivity = maxActivity;
		return this;
	}
	
	// TODO: Probably some render code to have a nice little seismometer in the corner.
	
}
