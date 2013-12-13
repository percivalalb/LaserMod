package lasermod;

import lasermod.core.helper.LogHelper;
import lasermod.core.proxy.CommonProxy;
import lasermod.lib.Reference;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

/**
 * @author ProPercivalalb
 */
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, dependencies = Reference.MOD_DEPENDENCIES)
@NetworkMod(clientSideRequired = false, serverSideRequired = true)
public class LaserMod {

	@Instance(value = Reference.MOD_ID)
	public static LaserMod instance;
	
	@SidedProxy(clientSide = Reference.SP_CLIENT, serverSide = Reference.SP_SERVER)
    public static CommonProxy proxy;
	
	public LaserMod() {
   	 	instance = this;
    }

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		LogHelper.init();
		//Loads the Blocks/Items
		ModBlocks.inti();
		ModItems.inti();
		ModEntities.inti();
		proxy.onPreLoad();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.registerHandlers();
	}
		
	@EventHandler
	public void modsLoaded(FMLPostInitializationEvent par1) {
		
	}
}
