package lasermod;

import lasermod.api.LaserModAPI;
import lasermod.api.LaserRegistry;
import lasermod.compat.forgemultipart.ForgeMultipartCompat;
import lasermod.laser.DamageLaser;
import lasermod.laser.FireLaser;
import lasermod.laser.HealingLaser;
import lasermod.laser.IceLaser;
import lasermod.laser.InvisibleLaser;
import lasermod.laser.MiningLaser;
import lasermod.laser.PullLaser;
import lasermod.laser.PushLaser;
import lasermod.laser.WaterLaser;
import lasermod.lib.Reference;
import lasermod.network.PacketDispatcher;
import lasermod.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, dependencies = Reference.MOD_DEPENDENCIES)
public class LaserMod {

	@Instance(value = Reference.MOD_ID)
	public static LaserMod instance;
	
	@SidedProxy(clientSide = Reference.SP_CLIENT, serverSide = Reference.SP_SERVER)
    public static CommonProxy proxy;
	
	/** Laser Mod Creative tab **/
	public static CreativeTabs tabLaser = new CreativeTabs("tabLaser") {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() { return ModItems.screwdriver; }
	};
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModBlocks.init();
		ModItems.init();
		ModEntities.init();
		
		proxy.onPreLoad();
		PacketDispatcher.registerPackets();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
    	NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
    	if(Loader.isModLoaded("ForgeMultipart")) {
    		
			try { ForgeMultipartCompat.init(); }
			catch(Throwable e) { e.printStackTrace(); }
    	}
    	
		proxy.registerHandlers();
	}
	
	@EventHandler
	public void modsLoaded(FMLPostInitializationEvent event) {
		RegistryHandling.init();
	}
}
