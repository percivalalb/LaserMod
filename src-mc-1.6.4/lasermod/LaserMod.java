package lasermod;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
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
import cpw.mods.fml.common.registry.GameRegistry;

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
		
		try
        	{
        		Configuration c = new Configuration(event.getSuggestedConfigurationFile());
        	 	c.load();
            		BlockIds.ID_BASIC_LASER = c.getBlock("basicLaser", 1670).getInt();
            		BlockIds.ID_ADVANCED_LASER = c.getBlock("advancedLaser", 1671).getInt();
            		BlockIds.ID_REFLECTOR = c.getBlock("reflector", 1672).getInt();
            		BlockIds.ID_LASER_DETECTOR = c.getBlock("detector", 1673).getInt();
            		BlockIds.ID_COLOUR_CONVERTER = c.getBlock("converter", 1674).getInt();
            		ItemIds.ID_LASER_CRYSTAL = c.getItem("crystal", 5670).getInt();
            		ItemIds.ID_LASER_SEEKING_GOOGLES = c.getItem("goggles", 5671).getInt();
            		ItemIds.ID_SCREWDRIVER = c.getItem("scredriver", 5672).getInt();
            		ItemIds.ID_UPGRADES = c.getItem("upgrades", 5673).getInt();
            		c.save();
        	}
        	catch (Exception e)
        	{
            		LogHelper.log(Level.SEVERE, "Couldn't initialize the config file");
            		e.printStackTrace();
        	}
		
		//Loads the Blocks/Items
		ModBlocks.inti();
		ModItems.inti();
		ModEntities.inti();
		
		//Registers all lasers
		LaserRegistry.registerLaser("default", new DefaultLaser());
		LaserRegistry.registerLaser("fire", new FireLaser());
		LaserRegistry.registerLaser("invisible", new InvisibleLaser());
		LaserRegistry.registerLaser("push", new PushLaser());
		LaserRegistry.registerLaser("pull", new PullLaser());
		LaserRegistry.registerLaser("damage", new DamageLaser());
		
		LaserRegistry.registerItemToLaser(ModItems.upgrades.itemID, 0, LaserRegistry.getLaserFromId("fire"));
		LaserRegistry.registerItemToLaser(ModItems.upgrades.itemID, 3, LaserRegistry.getLaserFromId("invisible"));
		LaserRegistry.registerItemToLaser(ModItems.upgrades.itemID, 5, LaserRegistry.getLaserFromId("push"));
		LaserRegistry.registerItemToLaser(ModItems.upgrades.itemID, 6, LaserRegistry.getLaserFromId("pull"));
		LaserRegistry.registerItemToLaser(ModItems.upgrades.itemID, 7, LaserRegistry.getLaserFromId("damage"));
		
		PacketHandler packet = new PacketHandler();
		NetworkRegistry.instance().registerChannel(packet, "laser:reflector");
		NetworkRegistry.instance().registerChannel(packet, "laser:advanced");
		NetworkRegistry.instance().registerChannel(packet, "laser:colour");
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
	    GameRegistry.addRecipe(new ItemStack(ModItems.screwdriver, 1), new Object[] {"  I", "YB ", "BY ", 'B', new ItemStack(Item.dyePowder, 1, 0), 'Y', new ItemStack(Item.dyePowder, 1, 11), 'I', Item.ingotIron});
	    GameRegistry.addRecipe(new ItemStack(ModItems.screwdriver, 1), new Object[] {"  I", "BY ", "YB ", 'B', new ItemStack(Item.dyePowder, 1, 0), 'Y', new ItemStack(Item.dyePowder, 1, 11), 'I', Item.ingotIron});
	    GameRegistry.addRecipe(new ItemStack(ModItems.laserCrystal, 1), new Object[] {"GRG", "SDS", "GRG", 'G', Block.glass, 'R', Item.redstone, 'D', Item.diamond, 'S', Item.glowstone});
	    GameRegistry.addRecipe(new ItemStack(ModItems.laserSeekingGoogles, 1), new Object[] {"I I", "I I", "GLG", 'G', Block.glass, 'L', ModItems.laserCrystal, 'I', Item.ingotIron});
	    GameRegistry.addRecipe(new ItemStack(ModBlocks.advancedLaser, 1), new Object[] {"OGO", "SLS", "OGO", 'G', Block.glass, 'L', ModItems.laserCrystal, 'S', Item.ingotGold, 'O', Block.obsidian});
	    GameRegistry.addRecipe(new ItemStack(ModBlocks.basicLaser, 1), new Object[] {"CGC", "GLG", "CGC", 'G', Block.glass, 'L', ModItems.laserCrystal, 'C', Block.cobblestone});
	    GameRegistry.addRecipe(new ItemStack(ModBlocks.reflector, 1), new Object[] {"CGC", "GSG", "CGC", 'G', Block.glass, 'C', Block.cobblestone, 'S', Item.glowstone});
	    GameRegistry.addRecipe(new ItemStack(ModBlocks.colourConverter, 1), new Object[] {"CGC", "GLG", "CGC", 'G', Item.glowstone, 'L', ModItems.laserCrystal, 'C', Block.cobblestone});
	    GameRegistry.addRecipe(new ItemStack(ModBlocks.laserDetector, 1), new Object[] {"SSS", "SBS", "SSS", 'B', Block.blockRedstone, 'S', Block.stone});
	    
	    GameRegistry.addShapelessRecipe(new ItemStack(ModItems.upgrades, 1, 0), new Object[] {Block.obsidian, Item.fireballCharge, Item.blazePowder, Item.glowstone});
	    GameRegistry.addShapelessRecipe(new ItemStack(ModItems.upgrades, 1, 3), new Object[] {Block.obsidian, Item.goldenCarrot, Item.fermentedSpiderEye, Item.glowstone});
	    GameRegistry.addShapelessRecipe(new ItemStack(ModItems.upgrades, 1, 5), new Object[] {Block.obsidian, Block.pistonBase, Item.feather, Item.glowstone});
	    GameRegistry.addShapelessRecipe(new ItemStack(ModItems.upgrades, 1, 6), new Object[] {Block.obsidian, Block.pistonStickyBase, Item.feather, Item.glowstone});
	    GameRegistry.addShapelessRecipe(new ItemStack(ModItems.upgrades, 1, 7), new Object[] {Block.obsidian, Item.swordGold, Item.spiderEye, Item.glowstone});
	}
}
