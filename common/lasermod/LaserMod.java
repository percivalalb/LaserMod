package lasermod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import lasermod.api.LaserRegistry;
import lasermod.block.laser.DamageLaser;
import lasermod.block.laser.DefaultLaser;
import lasermod.block.laser.FireLaser;
import lasermod.block.laser.IceLaser;
import lasermod.block.laser.InvisibleLaser;
import lasermod.block.laser.PullLaser;
import lasermod.block.laser.PushLaser;
import lasermod.block.laser.WaterLaser;
import lasermod.core.handler.OverlayHandler;
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
		LaserRegistry.registerLaser("water", new WaterLaser());
		LaserRegistry.registerLaser("ice", new IceLaser());
		LaserRegistry.registerLaser("invisible", new InvisibleLaser());
		LaserRegistry.registerLaser("push", new PushLaser());
		LaserRegistry.registerLaser("pull", new PullLaser());
		LaserRegistry.registerLaser("damage", new DamageLaser());
		
		LaserRegistry.registerItemToLaser(ModItems.upgrades.itemID, 0, LaserRegistry.getLaserFromId("fire"));
		LaserRegistry.registerItemToLaser(ModItems.upgrades.itemID, 1, LaserRegistry.getLaserFromId("water"));
		LaserRegistry.registerItemToLaser(ModItems.upgrades.itemID, 2, LaserRegistry.getLaserFromId("ice"));
		LaserRegistry.registerItemToLaser(ModItems.upgrades.itemID, 3, LaserRegistry.getLaserFromId("invisible"));
		LaserRegistry.registerItemToLaser(ModItems.upgrades.itemID, 5, LaserRegistry.getLaserFromId("push"));
		LaserRegistry.registerItemToLaser(ModItems.upgrades.itemID, 6, LaserRegistry.getLaserFromId("pull"));
		LaserRegistry.registerItemToLaser(ModItems.upgrades.itemID, 7, LaserRegistry.getLaserFromId("damage"));
		
		PacketHandler packet = new PacketHandler();
		NetworkRegistry.instance().registerChannel(packet, "laser:reflector");
		NetworkRegistry.instance().registerChannel(packet, "laser:advanced");
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
		
		MinecraftForge.EVENT_BUS.register(new OverlayHandler());
		
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
