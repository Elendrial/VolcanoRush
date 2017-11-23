package me.hii488.volcanoRush;

import me.hii488.controllers.InitilisationController;
import me.hii488.controllers.TickController;
import me.hii488.handlers.ContainerHandler;
import me.hii488.handlers.InputHandler;
import me.hii488.interfaces.IInitiliser;
import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.additionalTickers.LiquidFix;
import me.hii488.volcanoRush.containers.menus.DeathMenu;
import me.hii488.volcanoRush.containers.menus.MainMenu;
import me.hii488.volcanoRush.containers.menus.ShopMenu;
import me.hii488.volcanoRush.containers.volcanoes.StandardVolcano;
import me.hii488.volcanoRush.items.ItemList;
import me.hii488.volcanoRush.objects.entities.ChargeEntity;
import me.hii488.volcanoRush.objects.entities.DynamiteEntity;
import me.hii488.volcanoRush.objects.entities.FallingDirt;
import me.hii488.volcanoRush.objects.entities.MineralItem;
import me.hii488.volcanoRush.objects.entities.VRPlayer;
import me.hii488.volcanoRush.objects.tiles.AirTile;
import me.hii488.volcanoRush.objects.tiles.DirtTile;
import me.hii488.volcanoRush.objects.tiles.RockTile;
import me.hii488.volcanoRush.objects.tiles.RopeTile;
import me.hii488.volcanoRush.objects.tiles.UnbreakableTile;

public class Initilisation implements IInitiliser{
	
	public static Initilisation instance = new Initilisation();
	
	public static void setup(){
		InitilisationController.initList.add(instance);
		InputHandler.inputUsers.add(new ItemList());
		TickController.additionalEarlyTicking.add(new ItemList());
		TickController.additionalLateTicking.add(new LiquidFix());
	}

	public static MainMenu menuContainer = new MainMenu();
	public static ShopMenu shopContainer = new ShopMenu();
	public static DeathMenu deathContainer = new DeathMenu();
	public static StandardVolcano standardVolc = new StandardVolcano();
	
	@Override
	public void preInit() {
		ItemList.registerItems();
		
		ContainerHandler.addContainer(menuContainer);
		ContainerHandler.addContainer(shopContainer);
		ContainerHandler.addContainer(deathContainer);
		ContainerHandler.addContainer(standardVolc);
		
		// These need to be instantiated to add them to the entity/tile register.
		new DirtTile();
		new RockTile();
		new AirTile();
		new UnbreakableTile();
		new RopeTile();
		
		new MineralItem();
		new FallingDirt();
		new DynamiteEntity();
		new ChargeEntity();
		
		EntityRegistry.player = new VRPlayer();
	}

	@Override
	public void init() {
		ContainerHandler.loadNewContainer(menuContainer);
	}
	

	@Override
	public void postInit() {}
	
}
