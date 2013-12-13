package lasermod;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import lasermod.block.BlockBasicLaser;
import lasermod.block.BlockReflector;
import lasermod.lib.BlockIds;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.tileentity.TileEntityReflector;

/**
 * @author ProPercivalalb
 */
public class ModBlocks {
	
	public static Block basicLaser;
	public static Block reflector;
	
	public static void inti() {
		basicLaser = new BlockBasicLaser(BlockIds.ID_BASIC_LASER).setHardness(1.0F).setCreativeTab(CreativeTabs.tabBlock);
		reflector = new BlockReflector(BlockIds.ID_REFLECTOR).setHardness(1.0F).setCreativeTab(CreativeTabs.tabBlock);
		
		GameRegistry.registerBlock(basicLaser, "lasermod.basicLaser");
		GameRegistry.registerBlock(reflector, "lasermod.reflector");
		
		GameRegistry.registerTileEntity(TileEntityBasicLaser.class, "lasermod.basicLaser");
		GameRegistry.registerTileEntity(TileEntityReflector.class, "lasermod.reflector");
		
		MinecraftForge.setBlockHarvestLevel(basicLaser, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(reflector, "pickaxe", 1);
	}
}
