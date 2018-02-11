package me.hii488.volcanoRush;

import me.hii488.controllers.InitialisationController;
import me.hii488.controllers.TickController;
import me.hii488.handlers.ContainerHandler;
import me.hii488.handlers.InputHandler;
import me.hii488.interfaces.IInitialiser;
import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.additionalTickers.LiquidFix;
import me.hii488.volcanoRush.containers.menus.DeathMenu;
import me.hii488.volcanoRush.containers.menus.MainMenu;
import me.hii488.volcanoRush.containers.menus.ShopMenu;
import me.hii488.volcanoRush.containers.volcanoes.StandardVolcano;
import me.hii488.volcanoRush.objects.entities.ChargeEntity;
import me.hii488.volcanoRush.objects.entities.DynamiteEntity;
import me.hii488.volcanoRush.objects.entities.FallingDirt;
import me.hii488.volcanoRush.objects.entities.LavaLightEntity;
import me.hii488.volcanoRush.objects.entities.MineralItem;
import me.hii488.volcanoRush.objects.entities.VRPlayer;
import me.hii488.volcanoRush.objects.tiles.AirTile;
import me.hii488.volcanoRush.objects.tiles.DirtTile;
import me.hii488.volcanoRush.objects.tiles.RockTile;
import me.hii488.volcanoRush.objects.tiles.RopeTile;
import me.hii488.volcanoRush.objects.tiles.UnbreakableTile;
import me.hii488.volcanoRush.registers.FluidRegistry;
import me.hii488.volcanoRush.registers.ItemRegistry;

public class Initilisation implements IInitialiser{
	
	public static Initilisation instance = new Initilisation();
	
	public static void setup(){
		InitialisationController.addInitialiser(instance);
		InputHandler.inputUsers.add(new ItemRegistry());
		TickController.addEarlyTicker(new ItemRegistry());
		TickController.addLateTicker(new LiquidFix());
	}

	public static MainMenu menuContainer = new MainMenu();
	public static ShopMenu shopContainer = new ShopMenu();
	public static DeathMenu deathContainer = new DeathMenu();
	public static StandardVolcano standardVolc = new StandardVolcano();
	
	@Override
	public void preInit() {
		ItemRegistry.registerItems();
		FluidRegistry.registerFluids();
		
		ContainerHandler.addContainer(menuContainer);
		ContainerHandler.addContainer(shopContainer);
		ContainerHandler.addContainer(deathContainer);
		ContainerHandler.addContainer(standardVolc);
		
		tileInit();
		entityInit();
		
		EntityRegistry.player = new VRPlayer();
	}
	
	public void tileInit(){
		new DirtTile();
		new RockTile();
		new AirTile();
		new UnbreakableTile();
		new RopeTile();
	}
	
	public void entityInit(){
		new LavaLightEntity();
		new MineralItem();
		new FallingDirt();
		new DynamiteEntity();
		new ChargeEntity();
	}

	@Override
	public void init() {
		ContainerHandler.loadNewContainer(menuContainer);
	}
	

	@Override
	public void postInit() {}
	
}
