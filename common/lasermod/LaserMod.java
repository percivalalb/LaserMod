package lasermod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import lasermod.api.LaserRegistry;
import lasermod.block.laser.DefaultLaser;
import lasermod.block.laser.FireLaser;
import lasermod.block.laser.PullLaser;
import lasermod.block.laser.PushLaser;
import lasermod.core.helper.LogHelper;
import lasermod.core.proxy.CommonProxy;
import lasermod.lib.Reference;
import lasermod.packet.PacketHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

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
	
	public static CreativeTabs laserTab = new CreativeTabs("lasermod") {
		public ItemStack getIconItemStack() {
			return new ItemStack(ModItems.screwdriver);
		}
	};
	
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
		
		//Registers all lasers
		LaserRegistry.registerLaser("default", new DefaultLaser());
		LaserRegistry.registerLaser("fire", new FireLaser());
		LaserRegistry.registerLaser("push", new PushLaser());
		LaserRegistry.registerLaser("pull", new PullLaser());
		
		LaserRegistry.registerItemToLaser(ModItems.upgrades.itemID, 0, LaserRegistry.getLaserFromId("fire"));
		LaserRegistry.registerItemToLaser(ModItems.upgrades.itemID, 5, LaserRegistry.getLaserFromId("push"));
		LaserRegistry.registerItemToLaser(ModItems.upgrades.itemID, 6, LaserRegistry.getLaserFromId("pull"));
		
		PacketHandler packet = new PacketHandler();
		NetworkRegistry.instance().registerChannel(packet, "laser:reflector");
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
		
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
