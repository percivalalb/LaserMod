package lasermod;

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
	public static Item upgrades;
	
	public static void inti() {
		laserCrystal = new ItemLaserCrystal().setUnlocalizedName("lasermod.laserCrystal");
		laserSeekingGoogles = new ItemLaserSeekingGoogles().setUnlocalizedName("lasermod.laserSeekingGoogles");
		screwdriver = new ItemScrewdriver().setUnlocalizedName("lasermod.screwdriver");
	}
}
