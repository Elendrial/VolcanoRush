package me.hii488.volcanoRush.objects.tiles;

import me.hii488.volcanoRush.tileExtras.OreType;

public class RockTile extends MineralTile{
	
	public RockTile() {super();}
	public RockTile(RockTile t) {super(t);}
	
	public void initVars() {
		super.initVars();
		this.setOreType(OreType.NONE);
		this.identifier = "rockTile";
	}
	
	@Override
	public MineralTile clone() {
		return new RockTile(this);
	}

	@Override
	public void onDig() {} // Rocks can't be mined.

	@Override
	public void setOreType(OreType o) {
		oreType = o;
		this.identifier = "rockTile" + this.oreType;
		this.textureName = "rockTile_" + oreType + ".png";
		this.setupTextures();
	}

}
