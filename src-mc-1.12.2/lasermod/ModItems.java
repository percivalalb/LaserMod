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
	    	
	    	itemRegistry.register(new Item().setRegistryName(ItemNames.LASER_CRYSTAL).setUnlocalizedName("lasermod.laser_crystal").setCreativeTab(LaserMod.TAB_LASER));
	    	itemRegistry.register(new ItemLaserSeekingGoggles().setRegistryName(ItemNames.LASER_SEEKING_GOGGLES).setUnlocalizedName("lasermod.laser_seeking_goggles"));
	    	itemRegistry.register(new Item().setRegistryName(ItemNames.SCREWDRIVER).setUnlocalizedName("lasermod.screwdriver").setCreativeTab(LaserMod.TAB_LASER));
	    	itemRegistry.register(new Item().setRegistryName(ItemNames.HANDHELD_SENSOR).setUnlocalizedName("lasermod.handheld_sensor").setCreativeTab(LaserMod.TAB_LASER));
	    	itemRegistry.register(new Item().setRegistryName(ItemNames.FIRE).setUnlocalizedName("lasermod.upgrade_fire").setCreativeTab(LaserMod.TAB_LASER));
	    	itemRegistry.register(new Item().setRegistryName(ItemNames.WATER).setUnlocalizedName("lasermod.upgrade_water").setCreativeTab(LaserMod.TAB_LASER));
	    	itemRegistry.register(new Item().setRegistryName(ItemNames.ICE).setUnlocalizedName("lasermod.upgrade_ice").setCreativeTab(LaserMod.TAB_LASER));
	    	itemRegistry.register(new Item().setRegistryName(ItemNames.INVISIBLE).setUnlocalizedName("lasermod.upgrade_invisible").setCreativeTab(LaserMod.TAB_LASER));
	    	itemRegistry.register(new Item().setRegistryName(ItemNames.MINING).setUnlocalizedName("lasermod.upgrade_mining").setCreativeTab(LaserMod.TAB_LASER));
	    	itemRegistry.register(new Item().setRegistryName(ItemNames.PUSH).setUnlocalizedName("lasermod.upgrade_push").setCreativeTab(LaserMod.TAB_LASER));
	    	itemRegistry.register(new Item().setRegistryName(ItemNames.PULL).setUnlocalizedName("lasermod.upgrade_pull").setCreativeTab(LaserMod.TAB_LASER));
	    	itemRegistry.register(new Item().setRegistryName(ItemNames.DAMAGE).setUnlocalizedName("lasermod.upgrade_damage").setCreativeTab(LaserMod.TAB_LASER));
	    	itemRegistry.register(new Item().setRegistryName(ItemNames.HEALING).setUnlocalizedName("lasermod.upgrade_healing").setCreativeTab(LaserMod.TAB_LASER));
	    }
    }
}
