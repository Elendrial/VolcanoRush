package me.hii488.volcanoRush.registers;

import java.util.HashMap;

import me.hii488.handlers.TextureHandler;
import me.hii488.volcanoRush.fluids.Fluid;
import me.hii488.volcanoRush.fluids.FluidGas;
import me.hii488.volcanoRush.fluids.FluidLava;
import me.hii488.volcanoRush.fluids.FluidWater;

public class FluidRegistry {
	
	// Unsure whether this should be a hashmap or arraylist, may change at some point.
	public static HashMap<String, Fluid> fluids = new HashMap<String, Fluid>();
	
	public static void registerFluid(Fluid f){
		if(!fluids.containsValue(f)){
			fluids.put(f.identifier, f);
			TextureHandler.loadTextureSet("textures/overlays/fluids/", f.textureName + ".png", f, f.identifier, 4);
		}
	}
	
	public static void registerFluid(Fluid f, int states){
		if(!fluids.containsValue(f)){
			fluids.put(f.identifier, f);
			TextureHandler.loadTextureSet("textures/overlays/fluids/", f.textureName + ".png", f, f.identifier, states);
		}
	}
	
	public static Fluid getFluid(String identifier){
		return (Fluid) fluids.get(identifier);
	}
	
	public static void registerFluids(){
		registerFluid(new FluidWater());
		registerFluid(new FluidLava());
		registerFluid(new FluidGas());
	}
	
}
