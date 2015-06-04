package lasermod;

import lasermod.block.BlockAdvancedLaser;
import lasermod.block.BlockBasicLaser;
import lasermod.block.BlockColourConverter;
import lasermod.block.BlockLaserDetector;
import lasermod.block.BlockLensWorkbench;
import lasermod.block.BlockLuminousLamp;
import lasermod.block.BlockReflector;
import lasermod.block.BlockSmallColourConverter;
import lasermod.compat.forgemultipart.ForgeMultipartCompat;
import lasermod.tileentity.TileEntityAdvancedLaser;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.tileentity.TileEntityColourConverter;
import lasermod.tileentity.TileEntityLaserDetector;
import lasermod.tileentity.TileEntityLuminousLamp;
import lasermod.tileentity.TileEntityReflector;
import lasermod.tileentity.TileEntitySmallColourConverter;
import net.minecraft.block.Block;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

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
		basicLaser = new BlockBasicLaser().setBlockName("lasermod.basicLaser");
		advancedLaser = new BlockAdvancedLaser().setBlockName("lasermod.advancedLaser");
		laserDetector = new BlockLaserDetector().setBlockName("lasermod.detector");
		reflector = new BlockReflector().setBlockName("lasermod.reflector");
		colourConverter = new BlockColourConverter().setBlockName("lasermod.colorconverter");
		smallColourConverter = new BlockSmallColourConverter().setBlockName("lasermod.smallcolorconverter");
		luminousLamp = new BlockLuminousLamp().setBlockName("lasermod.luminouslamp");
		lensWorkbench = new BlockLensWorkbench().setBlockName("lasermod.lensworkbench");
		
		GameRegistry.registerBlock(basicLaser, "basiclaser");
		GameRegistry.registerBlock(advancedLaser, "advancedlaser");
		GameRegistry.registerBlock(laserDetector, "detector");
		GameRegistry.registerBlock(reflector, "reflector");
		GameRegistry.registerBlock(colourConverter, "colorconverter");
		
		if(Loader.isModLoaded("ForgeMultipart")) {
			try {
				ForgeMultipartCompat.registerBlock();
			}
			catch(Throwable e) {
				GameRegistry.registerBlock(smallColourConverter, "smallcolorconverter");
				e.printStackTrace();
			}
		}
		else
			GameRegistry.registerBlock(smallColourConverter, "smallcolorconverter");
		
		GameRegistry.registerBlock(luminousLamp, "luminouslamp");
		//GameRegistry.registerBlock(lensWorkbench, "lensWorkbench");
		
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
