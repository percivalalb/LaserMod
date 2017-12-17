package lasermod;

import lasermod.block.BlockAdvancedLaser;
import lasermod.block.BlockBasicLaser;
import lasermod.block.BlockColourConverter;
import lasermod.block.BlockLaserDetector;
import lasermod.block.BlockLensWorkbench;
import lasermod.block.BlockLuminousLamp;
import lasermod.block.BlockReflector;
import lasermod.block.BlockSmallColourConverter;
import lasermod.lib.Reference;
import lasermod.tileentity.TileEntityAdvancedLaser;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.tileentity.TileEntityColourConverter;
import lasermod.tileentity.TileEntityLaserDetector;
import lasermod.tileentity.TileEntityLuminousLamp;
import lasermod.tileentity.TileEntityReflector;
import lasermod.tileentity.TileEntitySmallColourConverter;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * @author ProPercivalalb
 */
@EventBusSubscriber(modid = Reference.MOD_ID)
public class ModBlocks {
	
	public static Block LASER_BASIC;
	public static Block LASER_ADVANCED;
	public static Block LASER_DETECTOR;
	public static Block REFLECTOR;
	public static Block COLOUR_CONVERTER;
	public static Block COLOUR_CONVERTER_SMALL;
	public static Block LUMINOUS_LAMP;
	public static Block LENS_WORKBENCH;
	
	public static void init() {
		LASER_BASIC = new BlockBasicLaser().setRegistryName(Reference.MOD_ID, "basic_laser").setUnlocalizedName("lasermod.basicLaser");
		LASER_ADVANCED = new BlockAdvancedLaser().setRegistryName(Reference.MOD_ID, "advanced_laser").setUnlocalizedName("lasermod.advancedLaser");
		LASER_DETECTOR = new BlockLaserDetector().setRegistryName(Reference.MOD_ID, "detector").setUnlocalizedName("lasermod.detector");
		REFLECTOR = new BlockReflector().setRegistryName(Reference.MOD_ID, "reflector").setUnlocalizedName("lasermod.reflector");
		COLOUR_CONVERTER = new BlockColourConverter().setRegistryName(Reference.MOD_ID, "colour_converter").setUnlocalizedName("lasermod.colorconverter");
		COLOUR_CONVERTER_SMALL = new BlockSmallColourConverter().setRegistryName(Reference.MOD_ID, "small_colour_converter").setUnlocalizedName("lasermod.smallcolorconverter");
		LUMINOUS_LAMP = new BlockLuminousLamp().setRegistryName(Reference.MOD_ID, "luminous_lamp").setUnlocalizedName("lasermod.luminouslamp");
		LENS_WORKBENCH = new BlockLensWorkbench().setRegistryName(Reference.MOD_ID, "lens_workbench").setUnlocalizedName("lasermod.lensworkbench");
		
		GameRegistry.registerTileEntity(TileEntityBasicLaser.class, "lasermod.basiclaser");
		GameRegistry.registerTileEntity(TileEntityAdvancedLaser.class, "lasermod.advancedlaser");
		GameRegistry.registerTileEntity(TileEntityLaserDetector.class, "lasermod.detector");
		GameRegistry.registerTileEntity(TileEntityReflector.class, "lasermod.reflector");
		GameRegistry.registerTileEntity(TileEntityColourConverter.class, "lasermod.colourconverter");
		GameRegistry.registerTileEntity(TileEntitySmallColourConverter.class, "lasermod.smallcolourconverter");
		GameRegistry.registerTileEntity(TileEntityLuminousLamp.class, "lasermod.luminouslamp");
		
		//MinecraftForge.setBlockHarvestLevel(basicLaser, "pickaxe", 1);
	}
	
	@SubscribeEvent
	public static void onRegisterBlock(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();
		
		registry.register(LASER_BASIC);
		registry.register(LASER_ADVANCED);
		registry.register(LASER_DETECTOR);
		registry.register(REFLECTOR);
		registry.register(COLOUR_CONVERTER);
		registry.register(COLOUR_CONVERTER_SMALL);
		registry.register(LUMINOUS_LAMP);
		registry.register(LENS_WORKBENCH);
	}
	
	@SubscribeEvent
	public static void onRegisterItem(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		
		registry.register(makeItemBlock(LASER_BASIC));
		registry.register(makeItemBlock(LASER_ADVANCED));
		registry.register(makeItemBlock(LASER_DETECTOR));
		registry.register(makeItemBlock(REFLECTOR));
		registry.register(makeItemBlock(COLOUR_CONVERTER));
		registry.register(makeItemBlock(COLOUR_CONVERTER_SMALL));
		registry.register(makeItemBlock(LUMINOUS_LAMP));
		registry.register(makeItemBlock(LENS_WORKBENCH));
	}
	
	private static ItemBlock makeItemBlock(Block block) {
        return (ItemBlock)new ItemBlock(block).setRegistryName(block.getRegistryName());
    }
}
