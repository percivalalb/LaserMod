package lasermod;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import lasermod.block.BlockAdvancedLaser;
import lasermod.block.BlockBasicLaser;
import lasermod.block.BlockColourConverter;
import lasermod.block.BlockLaserDetector;
import lasermod.block.BlockReflector;
import lasermod.lib.BlockIds;
import lasermod.tileentity.TileEntityAdvancedLaser;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.tileentity.TileEntityColourConverter;
import lasermod.tileentity.TileEntityLaserDetector;
import lasermod.tileentity.TileEntityReflector;

/**
 * @author ProPercivalalb
 */
public class ModBlocks {
	
	public static Block basicLaser;
	public static Block advancedLaser;
	public static Block reflector;
	public static Block laserDetector;
	public static Block colourConverter;
	
	public static void inti() {
		basicLaser = new BlockBasicLaser(BlockIds.ID_BASIC_LASER).setHardness(1.0F).setUnlocalizedName("lasermod.basicLaser").setCreativeTab(LaserMod.laserTab);
		advancedLaser = new BlockAdvancedLaser(BlockIds.ID_ADVANCED_LASER).setHardness(1.0F).setUnlocalizedName("lasermod.advancedLaser").setCreativeTab(LaserMod.laserTab);
		reflector = new BlockReflector(BlockIds.ID_REFLECTOR).setUnlocalizedName("lasermod.reflector").setHardness(1.0F).setCreativeTab(LaserMod.laserTab);
		laserDetector = new BlockLaserDetector(BlockIds.ID_LASER_DETECTOR).setUnlocalizedName("lasermod.detector").setHardness(1.0F).setCreativeTab(LaserMod.laserTab);
		colourConverter = new BlockColourConverter(BlockIds.ID_COLOUR_CONVERTER).setUnlocalizedName("lasermod.colorconverter").setHardness(1.0F).setCreativeTab(LaserMod.laserTab);
		
		GameRegistry.registerBlock(basicLaser, "lasermod.basicLaser");
		GameRegistry.registerBlock(advancedLaser, "lasermod.advancedLaser");
		GameRegistry.registerBlock(reflector, "lasermod.reflector");
		GameRegistry.registerBlock(laserDetector, "lasermod.detector");
		GameRegistry.registerBlock(colourConverter, "lasermod.colorconverter");
		
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
	}
}
