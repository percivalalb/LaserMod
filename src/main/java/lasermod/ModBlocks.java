package lasermod;

import lasermod.block.BlockAdvancedLaser;
import lasermod.block.BlockBasicLaser;
import lasermod.block.BlockColourConverter;
import lasermod.block.BlockLaserDetector;
import lasermod.block.BlockReflector;
import lasermod.tileentity.TileEntityAdvancedLaser;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.tileentity.TileEntityColourConverter;
import lasermod.tileentity.TileEntityLaserDetector;
import lasermod.tileentity.TileEntityReflector;
import net.minecraft.block.Block;
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
	
	public static void init() {
		basicLaser = new BlockBasicLaser().setBlockName("lasermod.basicLaser");
		advancedLaser = new BlockAdvancedLaser().setBlockName("lasermod.advancedLaser");
		laserDetector = new BlockLaserDetector().setBlockName("lasermod.detector");
		reflector = new BlockReflector().setBlockName("lasermod.reflector");
		colourConverter = new BlockColourConverter().setBlockName("lasermod.colorconverter");
		
		GameRegistry.registerBlock(basicLaser, "basicLaser");
		GameRegistry.registerBlock(advancedLaser, "advancedLaser");
		GameRegistry.registerBlock(laserDetector, "detector");
		GameRegistry.registerBlock(reflector, "reflector");
		GameRegistry.registerBlock(colourConverter, "colorconverter");
		
		GameRegistry.registerTileEntity(TileEntityBasicLaser.class, "lasermod.basicLaser");
		GameRegistry.registerTileEntity(TileEntityAdvancedLaser.class, "lasermod.advancedLaser");
		GameRegistry.registerTileEntity(TileEntityLaserDetector.class, "lasermod.detector");
		GameRegistry.registerTileEntity(TileEntityReflector.class, "lasermod.reflector");
		GameRegistry.registerTileEntity(TileEntityColourConverter.class, "lasermod.colourconverter");
		
		//MinecraftForge.setBlockHarvestLevel(basicLaser, "pickaxe", 1);
	}
}
