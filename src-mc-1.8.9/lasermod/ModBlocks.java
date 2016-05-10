package lasermod;

import lasermod.block.BlockAdvancedLaser;
import lasermod.block.BlockBasicLaser;
import lasermod.block.BlockColourConverter;
import lasermod.block.BlockLaserDetector;
import lasermod.block.BlockLensWorkbench;
import lasermod.block.BlockLuminousLamp;
import lasermod.block.BlockReflector;
import lasermod.block.BlockSmallColourConverter;
import lasermod.tileentity.TileEntityAdvancedLaser;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.tileentity.TileEntityColourConverter;
import lasermod.tileentity.TileEntityLaserDetector;
import lasermod.tileentity.TileEntityLuminousLamp;
import lasermod.tileentity.TileEntityReflector;
import lasermod.tileentity.TileEntitySmallColourConverter;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author ProPercivalalb
 */
public class ModBlocks {
	
	public static Block basicLaser;
	public static Block advancedLaser;
	public static Block laserDetector;
	public static Block reflector;
	public static Block colourConverter;
	public static Block smallColourConverter;
	public static Block luminousLamp;
	public static Block lensWorkbench;
	
	public static void init() {
		basicLaser = new BlockBasicLaser().setUnlocalizedName("lasermod.basicLaser");
		advancedLaser = new BlockAdvancedLaser().setUnlocalizedName("lasermod.advancedLaser");
		laserDetector = new BlockLaserDetector().setUnlocalizedName("lasermod.detector");
		reflector = new BlockReflector().setUnlocalizedName("lasermod.reflector");
		colourConverter = new BlockColourConverter().setUnlocalizedName("lasermod.colorconverter");
		smallColourConverter = new BlockSmallColourConverter().setUnlocalizedName("lasermod.smallcolorconverter");
		luminousLamp = new BlockLuminousLamp().setUnlocalizedName("lasermod.luminouslamp");
		lensWorkbench = new BlockLensWorkbench().setUnlocalizedName("lasermod.lensworkbench");
		
		GameRegistry.registerBlock(basicLaser, "basic_laser");
		GameRegistry.registerBlock(advancedLaser, "advanced_laser");
		GameRegistry.registerBlock(laserDetector, "detector");
		GameRegistry.registerBlock(reflector, "reflector");
		GameRegistry.registerBlock(colourConverter, "colorconverter");
		GameRegistry.registerBlock(smallColourConverter, "smallcolorconverter");
		
		GameRegistry.registerBlock(luminousLamp, "luminouslamp");
		GameRegistry.registerBlock(lensWorkbench, "lensWorkbench");
		
		GameRegistry.registerTileEntity(TileEntityBasicLaser.class, "lasermod.basiclaser");
		GameRegistry.registerTileEntity(TileEntityAdvancedLaser.class, "lasermod.advancedlaser");
		GameRegistry.registerTileEntity(TileEntityLaserDetector.class, "lasermod.detector");
		GameRegistry.registerTileEntity(TileEntityReflector.class, "lasermod.reflector");
		GameRegistry.registerTileEntity(TileEntityColourConverter.class, "lasermod.colourconverter");
		GameRegistry.registerTileEntity(TileEntitySmallColourConverter.class, "lasermod.smallcolourconverter");
		GameRegistry.registerTileEntity(TileEntityLuminousLamp.class, "lasermod.luminouslamp");
		
		//MinecraftForge.setBlockHarvestLevel(basicLaser, "pickaxe", 1);
	}
}
