package me.hii488.volcanoRush.objects.tiles;

import me.hii488.objects.tiles.BlankTile;

public class AirTile extends BlankTile{

	public AirTile(){super();}
	public AirTile(AirTile t){super(t);}
	
	@Override
	public void initVars() {
		this.states = 1; // Underground, and above ground
		this.textureName = "air.png";
		this.identifier = "airTile";
	}
	
	public AirTile clone(){
		return new AirTile(this);
	}

}
