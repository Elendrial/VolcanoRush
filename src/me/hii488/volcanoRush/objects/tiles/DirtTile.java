package me.hii488.volcanoRush.objects.tiles;

import me.hii488.volcanoRush.objects.OreType;

public class DirtTile extends MineralTile{
	
	public DirtTile(){super();}
	public DirtTile(DirtTile t){super(t);}
	
	@Override
	public void initVars() {
		oreType = OreType.NONE;
		this.identifier = "dirtTile";
		this.textureName = "dirtTile_" + this.oreType + ".png";
		this.isCollidable = true;
	}

	public void onDig(){
		if(damageValue == 0) damageValue = 2;
		else damageValue = 5;
	}
	

	@Override
	public void setOreType(OreType o) {
		oreType = o;
		this.identifier = "dirtTile" + this.oreType;
		this.textureName = "dirtTile_" + oreType + ".png";
		this.setupTextures();
	}
	
	@Override
	public DirtTile clone() {
		return new DirtTile(this);
	}

}
