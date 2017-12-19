package lasermod;

import lasermod.lib.Reference;
import lasermod.network.PacketDispatcher;
import lasermod.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)
public class LaserMod {

	@Instance(value = Reference.MOD_ID)
	public static LaserMod INSTANCE;
	
	@SidedProxy(clientSide = Reference.SP_CLIENT, serverSide = Reference.SP_SERVER)
    public static CommonProxy PROXY;
	
	/** Laser Mod Creative tab **/
	public static CreativeTabs TAB_LASER = new CreativeTabs("tabLaser") {
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() { return new ItemStack(ModItems.SCREWDRIVER); }
	};
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModEntities.init();
		
		PROXY.onPreLoad();
		PacketDispatcher.registerPackets();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
    	NetworkRegistry.INSTANCE.registerGuiHandler(this, PROXY);
		PROXY.registerHandlers();
	}
	
	@EventHandler
	public void modsLoaded(FMLPostInitializationEvent event) {
		RegistryHandling.init();
	}
}
