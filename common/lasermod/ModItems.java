package lasermod;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import lasermod.item.ItemBase;
import lasermod.item.ItemLaserSeekingGoogles;
import lasermod.lib.ItemIds;

/**
 * @author ProPercivalalb
 */
public class ModItems {
	
	public static Item laserCrystal;
	public static Item laserSeekingGoogles;
	public static Item screwdriver;
	
	public static void inti() {
		laserCrystal = new ItemBase(ItemIds.ID_LASER_CRYSTAL, "laserCrystal").setUnlocalizedName("lasermod.laserCrystal");
		laserSeekingGoogles = new ItemLaserSeekingGoogles(ItemIds.ID_LASER_SEEKING_GOOGLES, "laserSeekingGoogles").setUnlocalizedName("lasermod.laserSeekingGoogles");
		screwdriver = new ItemBase(ItemIds.ID_SCREWDRIVER, "screwdriver").setUnlocalizedName("lasermod.screwdriver");
		
		GameRegistry.registerItem(laserCrystal, "lasermod.laserCrystal");
		GameRegistry.registerItem(laserSeekingGoogles, "lasermod.laserSeekingGoogles");
		GameRegistry.registerItem(screwdriver, "lasermod.screwdriver");
	}
}
