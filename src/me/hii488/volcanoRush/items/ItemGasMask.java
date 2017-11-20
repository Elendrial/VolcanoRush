package me.hii488.volcanoRush.items;

import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.dataTypes.FluidType;
import me.hii488.volcanoRush.objects.entities.VRPlayer;

public class ItemGasMask extends Item{

	public ItemGasMask(){
		this.name = "Gas mask";
		this.identifier = "gasmask";
		this.droppable = true;
		this.priority = 1;
		this.cost = 200;
		this.scoreMultiplier = 1;
	}
	
	@Override
	public void inFluid(FluidType fluid) {
		if(fluid.name() == "GAS"){
			((VRPlayer) EntityRegistry.player).drowning[FluidType.GAS.ordinal()] = false;
		}
	}
	
}
