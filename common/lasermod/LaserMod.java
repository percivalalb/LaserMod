package lasermod;

import lasermod.api.LaserRegistry;
import lasermod.block.BlockAdvancedLaser;
import lasermod.block.BlockBasicLaser;
import lasermod.block.BlockColourConverter;
import lasermod.block.BlockLaserDetector;
import lasermod.block.BlockReflector;
import lasermod.block.laser.DamageLaser;
import lasermod.block.laser.DefaultLaser;
import lasermod.block.laser.FireLaser;
import lasermod.block.laser.InvisibleLaser;
import lasermod.block.laser.PullLaser;
import lasermod.block.laser.PushLaser;
import lasermod.core.handler.OverlayHandler;
import lasermod.core.helper.LogHelper;
import lasermod.core.proxy.CommonProxy;
import lasermod.item.ItemBase;
import lasermod.item.ItemLaserSeekingGoogles;
import lasermod.item.ItemScrewdriver;
import lasermod.item.ItemUpgrades;
import lasermod.lib.BlockIds;
import lasermod.lib.ItemIds;
import lasermod.lib.Reference;
import lasermod.packet.PacketHandler;
import lasermod.tileentity.TileEntityAdvancedLaser;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.tileentity.TileEntityColourConverter;
import lasermod.tileentity.TileEntityLaserDetector;
import lasermod.tileentity.TileEntityReflector;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
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
	public static Block basicLaser;
	public static Block advancedLaser;
	public static Block reflector;
	public static Block laserDetector;
	public static Block colourConverter;

	public static Item laserCrystal;
	public static Item laserSeekingGoogles;
	public static Item screwdriver;
	public static Item upgrades;

	@Instance(value = Reference.MOD_ID)
	public static LaserMod instance;

	@SidedProxy(clientSide = Reference.SP_CLIENT, serverSide = Reference.SP_SERVER)
	public static CommonProxy proxy;

	public static CreativeTabs laserTab = new CreativeTabs("lasermod") {
		public ItemStack getIconItemStack() {
			return new ItemStack(screwdriver);
		}
	};

	public LaserMod() {
		instance = this;
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		LogHelper.init();

		basicLaser = new BlockBasicLaser(BlockIds.ID_BASIC_LASER).setHardness(1.0F).setUnlocalizedName("lasermod.basicLaser").setCreativeTab(LaserMod.laserTab);
		advancedLaser = new BlockAdvancedLaser(BlockIds.ID_ADVANCED_LASER).setHardness(1.0F).setUnlocalizedName("lasermod.advancedLaser").setCreativeTab(LaserMod.laserTab);
		reflector = new BlockReflector(BlockIds.ID_REFLECTOR).setUnlocalizedName("lasermod.reflector").setHardness(1.0F).setCreativeTab(LaserMod.laserTab);
		laserDetector = new BlockLaserDetector(BlockIds.ID_LASER_DETECTOR).setUnlocalizedName("lasermod.detector").setHardness(1.0F).setCreativeTab(LaserMod.laserTab);
		colourConverter = new BlockColourConverter(BlockIds.ID_COLOUR_CONVERTER).setUnlocalizedName("lasermod.colorconverter").setHardness(1.0F).setCreativeTab(LaserMod.laserTab);

		laserCrystal = new ItemBase(ItemIds.ID_LASER_CRYSTAL, "laserCrystal").setUnlocalizedName("lasermod.laserCrystal");
		laserSeekingGoogles = new ItemLaserSeekingGoogles(ItemIds.ID_LASER_SEEKING_GOOGLES, "laserSeekingGoogles").setUnlocalizedName("lasermod.laserSeekingGoogles");
		screwdriver = new ItemScrewdriver(ItemIds.ID_SCREWDRIVER, "screwdriver").setUnlocalizedName("lasermod.screwdriver");
		upgrades = new ItemUpgrades(ItemIds.ID_UPGRADES).setUnlocalizedName("lasermod.upgrade");

		GameRegistry.registerBlock(basicLaser, "lasermod.basicLaser");
		GameRegistry.registerBlock(advancedLaser, "lasermod.advancedLaser");
		GameRegistry.registerBlock(reflector, "lasermod.reflector");
		GameRegistry.registerBlock(laserDetector, "lasermod.detector");
		GameRegistry.registerBlock(colourConverter, "lasermod.colorconverter");

		GameRegistry.registerItem(laserCrystal, "lasermod.laserCrystal");
		GameRegistry.registerItem(laserSeekingGoogles, "lasermod.laserSeekingGoogles");
		GameRegistry.registerItem(screwdriver, "lasermod.screwdriver");
		GameRegistry.registerItem(upgrades, "lasermod.upgrades");

		GameRegistry.registerTileEntity(TileEntityBasicLaser.class, "lasermod.basicLaser");
		GameRegistry.registerTileEntity(TileEntityAdvancedLaser.class, "lasermod.advancedLaser");
		GameRegistry.registerTileEntity(TileEntityReflector.class, "lasermod.reflector");
		GameRegistry.registerTileEntity(TileEntityLaserDetector.class, "lasermod.detector");
		GameRegistry.registerTileEntity(TileEntityColourConverter.class, "lasermod.colourconverter");

		MinecraftForge.setBlockHarvestLevel(basicLaser, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(advancedLaser, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(reflector, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(laserDetector, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(colourConverter, "pickaxe", 1);

		LaserRegistry.registerLaser("default", new DefaultLaser());
		LaserRegistry.registerLaser("fire", new FireLaser());
		LaserRegistry.registerLaser("invisible", new InvisibleLaser());
		LaserRegistry.registerLaser("push", new PushLaser());
		LaserRegistry.registerLaser("pull", new PullLaser());
		LaserRegistry.registerLaser("damage", new DamageLaser());

		LaserRegistry.registerItemToLaser(upgrades.itemID, 0, LaserRegistry.getLaserFromId("fire"));
		LaserRegistry.registerItemToLaser(upgrades.itemID, 3, LaserRegistry.getLaserFromId("invisible"));
		LaserRegistry.registerItemToLaser(upgrades.itemID, 5, LaserRegistry.getLaserFromId("push"));
		LaserRegistry.registerItemToLaser(upgrades.itemID, 6, LaserRegistry.getLaserFromId("pull"));
		LaserRegistry.registerItemToLaser(upgrades.itemID, 7, LaserRegistry.getLaserFromId("damage"));

		PacketHandler p = new PacketHandler();
		NetworkRegistry.instance().registerChannel(p, "laser:reflector");
		NetworkRegistry.instance().registerChannel(p, "laser:advanced");
		NetworkRegistry.instance().registerChannel(p, "laser:colour");
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
		GameRegistry.addRecipe(new ItemStack(screwdriver, 1), new Object[] { "  I", "YB ", "BY ", 'B', new ItemStack(Item.dyePowder, 1, 0), 'Y', new ItemStack(Item.dyePowder, 1, 11), 'I', Item.ingotIron });
		GameRegistry.addRecipe(new ItemStack(screwdriver, 1), new Object[] { "  I", "BY ", "YB ", 'B', new ItemStack(Item.dyePowder, 1, 0), 'Y', new ItemStack(Item.dyePowder, 1, 11), 'I', Item.ingotIron });
		GameRegistry.addRecipe(new ItemStack(laserCrystal, 1), new Object[] { "GRG", "SDS", "GRG", 'G', Block.glass, 'R', Item.redstone, 'D', Item.diamond, 'S', Item.glowstone });
		GameRegistry.addRecipe(new ItemStack(laserSeekingGoogles, 1), new Object[] { "I I", "I I", "GLG", 'G', Block.glass, 'L', laserCrystal, 'I', Item.ingotIron });
		GameRegistry.addRecipe(new ItemStack(advancedLaser, 1), new Object[] { "OGO", "SLS", "OGO", 'G', Block.glass, 'L', laserCrystal, 'S', Item.ingotGold, 'O', Block.obsidian });
		GameRegistry.addRecipe(new ItemStack(basicLaser, 1), new Object[] { "CGC", "GLG", "CGC", 'G', Block.glass, 'L', laserCrystal, 'C', Block.cobblestone });
		GameRegistry.addRecipe(new ItemStack(reflector, 1), new Object[] { "CGC", "GSG", "CGC", 'G', Block.glass, 'C', Block.cobblestone, 'S', Item.glowstone });
		GameRegistry.addRecipe(new ItemStack(colourConverter, 1), new Object[] { "CGC", "GLG", "CGC", 'G', Item.glowstone, 'L', laserCrystal, 'C', Block.cobblestone });
		GameRegistry.addRecipe(new ItemStack(laserDetector, 1), new Object[] { "SSS", "SBS", "SSS", 'B', Block.blockRedstone, 'S', Block.stone });

		GameRegistry.addShapelessRecipe(new ItemStack(upgrades, 1, 0), new Object[] { Block.obsidian, Item.fireballCharge, Item.blazePowder, Item.glowstone });
		GameRegistry.addShapelessRecipe(new ItemStack(upgrades, 1, 3), new Object[] { Block.obsidian, Item.goldenCarrot, Item.fermentedSpiderEye, Item.glowstone });
		GameRegistry.addShapelessRecipe(new ItemStack(upgrades, 1, 5), new Object[] { Block.obsidian, Block.pistonBase, Item.feather, Item.glowstone });
		GameRegistry.addShapelessRecipe(new ItemStack(upgrades, 1, 6), new Object[] { Block.obsidian, Block.pistonStickyBase, Item.feather, Item.glowstone });
		GameRegistry.addShapelessRecipe(new ItemStack(upgrades, 1, 7), new Object[] { Block.obsidian, Item.swordGold, Item.spiderEye, Item.glowstone });
	}
}