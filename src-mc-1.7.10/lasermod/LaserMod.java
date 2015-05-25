package lasermod;

import codechicken.multipart.MultiPartRegistry;
import lasermod.api.LaserRegistry;
import lasermod.forgemultipart.PartRegister;
import lasermod.laser.*;
import lasermod.lib.Reference;
import lasermod.network.NetworkManager;
import lasermod.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
	
	public static NetworkManager NETWORK_MANAGER;
	
	/** Laser Mod Creative tab **/
	public static CreativeTabs tabLaser = new CreativeTabs("tabLaser") {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return ModItems.screwdriver;
		}
	};
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		ModBlocks.init();
		ModItems.init();
		ModEntities.init();
		
		proxy.onPreLoad();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		NETWORK_MANAGER = new NetworkManager();
    	NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
			
		proxy.registerHandlers();
	}
	
	@EventHandler
	public void modsLoaded(FMLPostInitializationEvent event) {
		PartRegister part = new PartRegister();
		MultiPartRegistry.registerConverter(part);
		MultiPartRegistry.registerParts(part, new String[] {"lasermod:smallcolorconverter"});
		
		//Registers all lasers
		LaserRegistry.registerLaser("default", new DefaultLaser());
		LaserRegistry.registerLaser("water", new WaterLaser());
		LaserRegistry.registerLaser("ice", new IceLaser());
		LaserRegistry.registerLaser("fire", new FireLaser());
		LaserRegistry.registerLaser("invisible", new InvisibleLaser());
		LaserRegistry.registerLaser("push", new PushLaser());
		LaserRegistry.registerLaser("pull", new PullLaser());
		LaserRegistry.registerLaser("damage", new DamageLaser());
		LaserRegistry.registerLaser("healing", new HealingLaser());
		LaserRegistry.registerLaser("mining", new MiningLaser());
				
		LaserRegistry.registerItemToLaser(ModItems.upgrades, 0, LaserRegistry.getLaserFromId("fire"));
		LaserRegistry.registerItemToLaser(ModItems.upgrades, 1, LaserRegistry.getLaserFromId("water"));
		LaserRegistry.registerItemToLaser(ModItems.upgrades, 2, LaserRegistry.getLaserFromId("ice"));
		LaserRegistry.registerItemToLaser(ModItems.upgrades, 3, LaserRegistry.getLaserFromId("invisible"));
		LaserRegistry.registerItemToLaser(ModItems.upgrades, 5, LaserRegistry.getLaserFromId("push"));
		LaserRegistry.registerItemToLaser(ModItems.upgrades, 6, LaserRegistry.getLaserFromId("pull"));
		LaserRegistry.registerItemToLaser(ModItems.upgrades, 7, LaserRegistry.getLaserFromId("damage"));
		LaserRegistry.registerItemToLaser(ModItems.upgrades, 8, LaserRegistry.getLaserFromId("healing"));
		LaserRegistry.registerItemToLaser(ModItems.upgrades, 4, LaserRegistry.getLaserFromId("mining"));
		
		GameRegistry.addRecipe(new ItemStack(ModItems.laserCrystal), " r ", "rgr", " r ", 'g', Blocks.glass, 'r', Items.redstone);
		GameRegistry.addRecipe(new ItemStack(ModItems.screwdriver, 1), new Object[] {"  I", "YB ", "BY ", 'B', new ItemStack(Items.dye, 1, 0), 'Y', new ItemStack(Items.dye, 1, 11), 'I', Items.iron_ingot});
		GameRegistry.addRecipe(new ItemStack(ModItems.screwdriver, 1), new Object[] {"  I", "BY ", "YB ", 'B', new ItemStack(Items.dye, 1, 0), 'Y', new ItemStack(Items.dye, 1, 11), 'I', Items.iron_ingot});
		GameRegistry.addRecipe(new ItemStack(ModItems.laserSeekingGoogles, 1), new Object[] {"I I", "I I", "GLG", 'G', Blocks.glass, 'L', ModItems.laserCrystal, 'I', Items.iron_ingot});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.advancedLaser, 1), new Object[] {"OGO", "SLS", "OGO", 'G', Blocks.glass, 'L', ModItems.laserCrystal, 'S', Items.iron_ingot, 'O', Blocks.obsidian});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.basicLaser, 1), new Object[] {"CGC", "GLG", "CGC", 'G', Blocks.glass, 'L', ModItems.laserCrystal, 'C', Blocks.cobblestone});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.reflector, 1), new Object[] {"CGC", "GSG", "CGC", 'G', Blocks.glass, 'C', Blocks.cobblestone, 'S', Items.glowstone_dust});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.colourConverter, 1), new Object[] {"CGC", "GLG", "CGC", 'G', Items.glowstone_dust, 'L', ModItems.laserCrystal, 'C', Blocks.cobblestone});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.laserDetector, 1), new Object[] {"SSS", "SBS", "SSS", 'B', Blocks.redstone_block, 'S', Blocks.stone});
		    
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.upgrades, 1, 0), new Object[] {Blocks.obsidian, Items.fire_charge, Items.blaze_powder, Items.glowstone_dust});
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.upgrades, 1, 3), new Object[] {Blocks.obsidian, Items.golden_carrot, Items.fermented_spider_eye, Items.glowstone_dust});
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.upgrades, 1, 5), new Object[] {Blocks.obsidian, Blocks.piston, Items.feather, Items.glowstone_dust});
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.upgrades, 1, 6), new Object[] {Blocks.obsidian, Blocks.sticky_piston, Items.feather, Items.glowstone_dust});
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.upgrades, 1, 7), new Object[] {Blocks.obsidian, Items.golden_sword, Items.spider_eye, Items.glowstone_dust});
	}
}
