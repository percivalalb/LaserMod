package lasermod;

import cpw.mods.fml.common.registry.GameRegistry;
import lasermod.item.ItemLaserCrystal;
import lasermod.item.ItemLaserSeekingGoogles;
import lasermod.item.ItemScrewdriver;
import net.minecraft.item.Item;

/**
 * @author ProPercivalalb
 */
public class ModItems {
	
	public static Item laserCrystal;
	public static Item laserSeekingGoogles;
	public static Item screwdriver;
	
	public static void inti() {
		laserCrystal = new ItemLaserCrystal().setUnlocalizedName("lasermod.laserCrystal");
		laserSeekingGoogles = new ItemLaserSeekingGoogles().setUnlocalizedName("lasermod.laserSeekingGoogles");
		screwdriver = new ItemScrewdriver().setUnlocalizedName("lasermod.screwdriver");
		
		GameRegistry.registerItem(laserCrystal, "lasermod.laserCrystal");
		GameRegistry.registerItem(laserSeekingGoogles, "lasermod.laserSeekingGoogles");
		GameRegistry.registerItem(screwdriver, "lasermod.screwdriver");
	}
}
