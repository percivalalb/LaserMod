package lasermod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lasermod.api.LaserModAPI;
import lasermod.api.LaserType;
import lasermod.lib.Reference;
import lasermod.network.PacketDispatcher;
import lasermod.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
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
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

/**
 * @author ProPercivalalb
 */
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)
public class LaserMod {

	@Instance(value = Reference.MOD_ID)
	public static LaserMod INSTANCE;
	
	public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_NAME);
	
	@SidedProxy(clientSide = Reference.SP_CLIENT, serverSide = Reference.SP_SERVER)
    public static CommonProxy PROXY;
	
	public LaserMod() {
		LaserModAPI.LASER_TYPES = makeRegistry(new ResourceLocation(Reference.MOD_ID, "laser_types"), LaserType.class, 64).create();
	}
	
	private static <T extends IForgeRegistryEntry<T>> RegistryBuilder<T> makeRegistry(ResourceLocation name, Class<T> type, int max) {
        return new RegistryBuilder<T>().setName(name).setType(type).setMaxID(max);
    }
	
	/** Laser Mod Creative tab **/
	public static CreativeTabs TAB_LASER = new CreativeTabs("tab_laser") {
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack createIcon() { return new ItemStack(ModItems.SCREWDRIVER); }
	};
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModEntities.Registration.registerEntities();
		
		PROXY.preInit();
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
