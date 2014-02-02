package lasermod;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import lasermod.block.BlockBasicLaser;
import lasermod.block.BlockColourConverter;
import lasermod.block.BlockLaserDetector;
import lasermod.block.BlockReflector;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.tileentity.TileEntityColourConverter;
import lasermod.tileentity.TileEntityLaserDetector;
import lasermod.tileentity.TileEntityReflector;

/**
 * @author ProPercivalalb
 */
public class ModBlocks {
	
	public static Block basicLaser;
	public static Block laserDetector;
	public static Block reflector;
	public static Block colourConverter;
	
	public static void inti() {
		basicLaser = new BlockBasicLaser().func_149663_c("lasermod:basicLaser");
		laserDetector = new BlockLaserDetector().func_149663_c("lasermod.detector");
		reflector = new BlockReflector().func_149663_c("lasermod.reflector");
		colourConverter = new BlockColourConverter().func_149663_c("lasermod.colorconverter");
		
		GameRegistry.registerBlock(basicLaser, "lasermod.basicLaser");
		GameRegistry.registerBlock(laserDetector, "lasermod.detector");
		GameRegistry.registerBlock(reflector, "lasermod.reflector");
		GameRegistry.registerBlock(colourConverter, "lasermod.colorconverter");
		
		GameRegistry.registerTileEntity(TileEntityBasicLaser.class, "lasermod.basicLaser");
		GameRegistry.registerTileEntity(TileEntityLaserDetector.class, "lasermod.detector");
		GameRegistry.registerTileEntity(TileEntityReflector.class, "lasermod.reflector");
		GameRegistry.registerTileEntity(TileEntityColourConverter.class, "lasermod.colourconverter");
		
		//MinecraftForge.setBlockHarvestLevel(basicLaser, "pickaxe", 1);
	}
}
