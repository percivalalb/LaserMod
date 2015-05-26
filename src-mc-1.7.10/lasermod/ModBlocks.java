package lasermod;

import lasermod.block.BlockAdvancedLaser;
import lasermod.block.BlockBasicLaser;
import lasermod.block.BlockColourConverter;
import lasermod.block.BlockLaserDetector;
import lasermod.block.BlockMirror;
import lasermod.block.BlockReflector;
import lasermod.block.BlockSmallColourConverter;
import lasermod.compat.forgemultipart.ForgeMultipartCompat;
import lasermod.tileentity.TileEntityAdvancedLaser;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.tileentity.TileEntityColourConverter;
import lasermod.tileentity.TileEntityLaserDetector;
import lasermod.tileentity.TileEntityMirror;
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
	public static Block mirror;
	
	public static void init() {
		basicLaser = new BlockBasicLaser().setBlockName("lasermod.basicLaser");
		advancedLaser = new BlockAdvancedLaser().setBlockName("lasermod.advancedLaser");
		laserDetector = new BlockLaserDetector().setBlockName("lasermod.detector");
		reflector = new BlockReflector().setBlockName("lasermod.reflector");
		colourConverter = new BlockColourConverter().setBlockName("lasermod.colorconverter");
		smallColourConverter = new BlockSmallColourConverter().setBlockName("lasermod.smallcolorconverter");
		mirror = new BlockMirror().setBlockName("lasermod.mirror");
		
		GameRegistry.registerBlock(basicLaser, "basicLaser");
		GameRegistry.registerBlock(advancedLaser, "advancedLaser");
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
		
		GameRegistry.registerBlock(mirror, "mirror");
		
		GameRegistry.registerTileEntity(TileEntityBasicLaser.class, "lasermod.basicLaser");
		GameRegistry.registerTileEntity(TileEntityAdvancedLaser.class, "lasermod.advancedLaser");
		GameRegistry.registerTileEntity(TileEntityLaserDetector.class, "lasermod.detector");
		GameRegistry.registerTileEntity(TileEntityReflector.class, "lasermod.reflector");
		GameRegistry.registerTileEntity(TileEntityColourConverter.class, "lasermod.colourconverter");
		GameRegistry.registerTileEntity(TileEntitySmallColourConverter.class, "lasermod.smallcolourconverter");
		GameRegistry.registerTileEntity(TileEntityMirror.class, "lasermod.mirror");
		
		//MinecraftForge.setBlockHarvestLevel(basicLaser, "pickaxe", 1);
	}
}
