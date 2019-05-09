package lasermod;

import lasermod.item.ItemLaserSeekingGoggles;
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
	@ObjectHolder(ItemNames.HANDHELD_SENSOR)
	public static Item HANDHELD_SENSOR;
	@ObjectHolder(ItemNames.FIRE)
	public static Item UPGRADE_FIRE;
	@ObjectHolder(ItemNames.WATER)
	public static Item UPGRADE_WATER;
	@ObjectHolder(ItemNames.ICE)
	public static Item UPGRADE_ICE;
	@ObjectHolder(ItemNames.INVISIBLE)
	public static Item UPGRADE_INVISIBLE;
	@ObjectHolder(ItemNames.MINING)
	public static Item UPGRADE_MINING;
	@ObjectHolder(ItemNames.PUSH)
	public static Item UPGRADE_PUSH;
	@ObjectHolder(ItemNames.PULL)
	public static Item UPGRADE_PULL;
	@ObjectHolder(ItemNames.DAMAGE)
	public static Item UPGRADE_DAMAGE;
	@ObjectHolder(ItemNames.HEALING)
	public static Item UPGRADE_HEAL;
	
	@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Registration {
		
	    @SubscribeEvent
	    public static void onItemRegister(final RegistryEvent.Register<Item> event) {
	    	IForgeRegistry<Item> itemRegistry = event.getRegistry();
	    	
	    	itemRegistry.register(new Item().setRegistryName(ItemNames.LASER_CRYSTAL).setTranslationKey("lasermod.laser_crystal").setCreativeTab(LaserMod.TAB_LASER));
	    	itemRegistry.register(new ItemLaserSeekingGoggles().setRegistryName(ItemNames.LASER_SEEKING_GOGGLES).setTranslationKey("lasermod.laser_seeking_goggles"));
	    	itemRegistry.register(new Item().setRegistryName(ItemNames.SCREWDRIVER).setTranslationKey("lasermod.screwdriver").setCreativeTab(LaserMod.TAB_LASER));
	    	itemRegistry.register(new Item().setRegistryName(ItemNames.HANDHELD_SENSOR).setTranslationKey("lasermod.handheld_sensor").setCreativeTab(LaserMod.TAB_LASER));
	    	itemRegistry.register(new Item().setRegistryName(ItemNames.FIRE).setTranslationKey("lasermod.upgrade_fire").setCreativeTab(LaserMod.TAB_LASER));
	    	itemRegistry.register(new Item().setRegistryName(ItemNames.WATER).setTranslationKey("lasermod.upgrade_water").setCreativeTab(LaserMod.TAB_LASER));
	    	itemRegistry.register(new Item().setRegistryName(ItemNames.ICE).setTranslationKey("lasermod.upgrade_ice").setCreativeTab(LaserMod.TAB_LASER));
	    	itemRegistry.register(new Item().setRegistryName(ItemNames.INVISIBLE).setTranslationKey("lasermod.upgrade_invisible").setCreativeTab(LaserMod.TAB_LASER));
	    	itemRegistry.register(new Item().setRegistryName(ItemNames.MINING).setTranslationKey("lasermod.upgrade_mining").setCreativeTab(LaserMod.TAB_LASER));
	    	itemRegistry.register(new Item().setRegistryName(ItemNames.PUSH).setTranslationKey("lasermod.upgrade_push").setCreativeTab(LaserMod.TAB_LASER));
	    	itemRegistry.register(new Item().setRegistryName(ItemNames.PULL).setTranslationKey("lasermod.upgrade_pull").setCreativeTab(LaserMod.TAB_LASER));
	    	itemRegistry.register(new Item().setRegistryName(ItemNames.DAMAGE).setTranslationKey("lasermod.upgrade_damage").setCreativeTab(LaserMod.TAB_LASER));
	    	itemRegistry.register(new Item().setRegistryName(ItemNames.HEALING).setTranslationKey("lasermod.upgrade_healing").setCreativeTab(LaserMod.TAB_LASER));
	    }
    }
}
