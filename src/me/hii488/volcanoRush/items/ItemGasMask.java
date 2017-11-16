package me.hii488.volcanoRush.items;

import java.awt.Graphics;

import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.objects.FluidType;
import me.hii488.volcanoRush.objects.entities.VRPlayer;

public class ItemGasMask extends Item{

	@Override
	public void inFluid(FluidType fluid) {
		if(fluid.name() == "GAS"){
			((VRPlayer) EntityRegistry.player).drowning[FluidType.GAS.ordinal()] = false;
		}
	}

	@Override
	public boolean onMovement() {return true;}

	@Override
	public void onGeneration() {}

	@Override
	public void render(Graphics g) {}

	@Override
	public void onEquip() {}

}
