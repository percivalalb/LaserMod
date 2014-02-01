package lasermod;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import lasermod.block.BlockBasicLaser;
import lasermod.tileentity.TileEntityBasicLaser;

/**
 * @author ProPercivalalb
 */
public class ModBlocks {
	
	public static Block basicLaser;
	
	public static void inti() {
		basicLaser = new BlockBasicLaser().func_149663_c("lasermod:basicLaser");
	
		GameRegistry.registerBlock(basicLaser, "lasermod.basicLaser");
		
		GameRegistry.registerTileEntity(TileEntityBasicLaser.class, "lasermod.basicLaser");
	
		//MinecraftForge.setBlockHarvestLevel(basicLaser, "pickaxe", 1);
	}
}
