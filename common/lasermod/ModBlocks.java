package lasermod;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import lasermod.block.BlockBasicLaser;
import lasermod.lib.BlockIds;

/**
 * @author ProPercivalalb
 */
public class ModBlocks {
	
	public static Block basicLaser;
	
	public static void inti() {
		basicLaser = new BlockBasicLaser(BlockIds.ID_BASIC_LASER).setHardness(1.0F);
		
		GameRegistry.registerBlock(basicLaser, "lasermod.basicLaser");
	}
}
