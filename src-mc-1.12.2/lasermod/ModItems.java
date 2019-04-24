package lasermod;

import lasermod.item.ItemHandheldSensor;
import lasermod.item.ItemLaserCrystal;
import lasermod.item.ItemLaserSeekingGoggles;
import lasermod.item.ItemScrewdriver;
import lasermod.item.ItemUpgrades;
import lasermod.lib.ItemNames;
import lasermod.lib.Reference;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * @author ProPercivalalb
 */
public class ModItems {
	
	@ObjectHolder(ItemNames.LASER_CRYSTAL)
	public static Item LASER_CRYSTAL;
	@ObjectHolder(ItemNames.LASER_SEEKING_GOGGLES)
	public static Item LASER_SEEKING_GOGGLES;
	@ObjectHolder(ItemNames.SCREWDRIVER)
	public static Item SCREWDRIVER;
	@ObjectHolder(ItemNames.UPGRADES)
	public static Item UPGRADES;
	@ObjectHolder(ItemNames.HANDHELD_SENSOR)
	public static Item HANDHELD_SENSOR;
	
	@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Registration {
		
	    @SubscribeEvent
	    public static void onItemRegister(final RegistryEvent.Register<Item> event) {
	    	IForgeRegistry<Item> itemRegistry = event.getRegistry();
	    	
	    	itemRegistry.register(new ItemLaserCrystal().setRegistryName(ItemNames.LASER_CRYSTAL).setTranslationKey("lasermod.laser_crystal"));
	    	itemRegistry.register(new ItemLaserSeekingGoggles().setRegistryName(ItemNames.LASER_SEEKING_GOGGLES).setTranslationKey("lasermod.laser_seeking_goggles"));
	    	itemRegistry.register(new ItemScrewdriver().setRegistryName(ItemNames.SCREWDRIVER).setTranslationKey("lasermod.screwdriver"));
	    	itemRegistry.register(new ItemUpgrades().setRegistryName(ItemNames.UPGRADES).setTranslationKey("lasermod.upgrade"));
	    	itemRegistry.register(new ItemHandheldSensor().setRegistryName(ItemNames.HANDHELD_SENSOR).setTranslationKey("lasermod.handheldsensor"));
	    }
    }
}
