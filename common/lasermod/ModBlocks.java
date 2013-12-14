package lasermod;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import lasermod.block.BlockBasicLaser;
import lasermod.block.BlockLaserDetector;
import lasermod.block.BlockReflector;
import lasermod.lib.BlockIds;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.tileentity.TileEntityLaserDetector;
import lasermod.tileentity.TileEntityReflector;

/**
 * @author ProPercivalalb
 */
public class ModBlocks {
	
	public static Block basicLaser;
	public static Block reflector;
	public static Block laserDetector;
	
	public static void inti() {
		basicLaser = new BlockBasicLaser(BlockIds.ID_BASIC_LASER).setHardness(1.0F).setUnlocalizedName("lasermod.basicLaser").setCreativeTab(LaserMod.laserTab);
		reflector = new BlockReflector(BlockIds.ID_REFLECTOR).setUnlocalizedName("lasermod.reflector").setHardness(1.0F).setCreativeTab(LaserMod.laserTab);
		laserDetector = new BlockLaserDetector(BlockIds.ID_LASER_DETECTOR).setUnlocalizedName("lasermod.detector").setHardness(1.0F).setCreativeTab(LaserMod.laserTab);
		
		GameRegistry.registerBlock(basicLaser, "lasermod.basicLaser");
		GameRegistry.registerBlock(reflector, "lasermod.reflector");
		GameRegistry.registerBlock(laserDetector, "lasermod.detector");
		
		GameRegistry.registerTileEntity(TileEntityBasicLaser.class, "lasermod.basicLaser");
		GameRegistry.registerTileEntity(TileEntityReflector.class, "lasermod.reflector");
		GameRegistry.registerTileEntity(TileEntityLaserDetector.class, "lasermod.detector");
		
		MinecraftForge.setBlockHarvestLevel(basicLaser, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(reflector, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(laserDetector, "pickaxe", 1);
	}
}
