package lasermod;
import lasermod.item.ItemHandheldSensor;
import lasermod.item.ItemLaserCrystal;
import lasermod.item.ItemLaserSeekingGoogles;
import lasermod.item.ItemScrewdriver;
import lasermod.item.ItemUpgrades;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * @author ProPercivalalb
 */
public class ModItems {
	
	public static Item laserCrystal;
	public static Item laserSeekingGoogles;
	public static Item screwdriver;
	public static Item upgrades;
	public static Item handheldSensor;
	
	public static void init() {
		laserCrystal = new ItemLaserCrystal().setUnlocalizedName("lasermod.laserCrystal");
		laserSeekingGoogles = new ItemLaserSeekingGoogles().setUnlocalizedName("lasermod.laserSeekingGoogles");
		screwdriver = new ItemScrewdriver().setUnlocalizedName("lasermod.screwdriver");
		upgrades = new ItemUpgrades().setUnlocalizedName("lasermod.upgrade");
		handheldSensor = new ItemHandheldSensor().setUnlocalizedName("lasermod.handheldsensor");
		
		GameRegistry.registerItem(laserCrystal, "laserCrystal");
		GameRegistry.registerItem(laserSeekingGoogles, "laserSeekingGoogles");
		GameRegistry.registerItem(screwdriver, "screwdriver");
		GameRegistry.registerItem(upgrades, "upgrades");
		GameRegistry.registerItem(handheldSensor, "handheldsensor");
	}
}
