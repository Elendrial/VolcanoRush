package me.hii488.volcanoRush.items;

import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.fluids.Fluid;
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
	public void inFluid(Fluid fluid) {
		if(fluid.identifier.equals("gas"))
			((VRPlayer) EntityRegistry.player).drowning.put(fluid, false);
	}
	
}
