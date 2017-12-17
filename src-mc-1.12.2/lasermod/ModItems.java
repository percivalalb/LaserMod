package lasermod;

import lasermod.item.ItemHandheldSensor;
import lasermod.item.ItemLaserCrystal;
import lasermod.item.ItemLaserSeekingGoogles;
import lasermod.item.ItemScrewdriver;
import lasermod.item.ItemUpgrades;
import lasermod.lib.Reference;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * @author ProPercivalalb
 */
@EventBusSubscriber(modid = Reference.MOD_ID)
public class ModItems {
	
	public static Item LASER_CRYSTAL;
	public static Item LASER_SEEKING_GOOGLES;
	public static Item SCREWDRIVER;
	public static Item UPGRADES;
	public static Item HANDHELD_SENSOR;
	
	public static void init() {
		LASER_CRYSTAL = new ItemLaserCrystal().setRegistryName(Reference.MOD_ID, "laser_crystal").setUnlocalizedName("lasermod.laserCrystal");
		LASER_SEEKING_GOOGLES = new ItemLaserSeekingGoogles().setRegistryName(Reference.MOD_ID, "laser_seeking_googles").setUnlocalizedName("lasermod.laserSeekingGoogles");
		SCREWDRIVER = new ItemScrewdriver().setRegistryName(Reference.MOD_ID, "screwdriver").setUnlocalizedName("lasermod.screwdriver");
		UPGRADES = new ItemUpgrades().setRegistryName(Reference.MOD_ID, "upgrades").setUnlocalizedName("lasermod.upgrade");
		HANDHELD_SENSOR = new ItemHandheldSensor().setRegistryName(Reference.MOD_ID, "handheld_sensor").setUnlocalizedName("lasermod.handheldsensor");
	}
	
	@SubscribeEvent
	public static void onRegisterItem(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		
		registry.register(LASER_CRYSTAL);
		registry.register(LASER_SEEKING_GOOGLES);
		registry.register(SCREWDRIVER);
		registry.register(UPGRADES);
		registry.register(HANDHELD_SENSOR);
	}
}
