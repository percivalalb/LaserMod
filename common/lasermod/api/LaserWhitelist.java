package lasermod.api;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 * If you block should allow a laser to pass through it eg like tall grass
 * please register you blocks here.
 */
public class LaserWhitelist {

	public static int ALL_METADATA = -1;
	
	public static ArrayList<Integer> WHITELIST = new ArrayList<Integer>();
	
	//Convenience method
	public static void addToWhiteList(Block block, int blockMeta) { addToWhiteList(block.blockID, blockMeta); }
	public static void addToWhiteList(int blockId) { addToWhiteList(blockId, -1); }
	public static void addToWhiteList(Block block) { addToWhiteList(block.blockID, -1); }
	
	
	public static void addToWhiteList(int blockId, int blockMeta) {
		if(blockMeta == ALL_METADATA)
			for(int i = 0; i < 16; ++i)
				WHITELIST.add(blockId + Block.blocksList.length * i);
		else
			WHITELIST.add(blockId + Block.blocksList.length * blockMeta);
	}
	
	//Convenience method
	public static boolean canLaserPassThrought(World world, int x, int y, int z) { return canLaserPassThrought(world.getBlockId(x, y, z), world.getBlockMetadata(x, y, z)); }
	
	/**
	 * Checks whether the laser can pass through a block
	 * @param blockId The blockId of the target location
	 * @param blockMeta The blockMeta of the target location
	 * @return If the laser is stopped by the given block
	 */
	public static boolean canLaserPassThrought(int blockId, int blockMeta) {
		return WHITELIST.contains(blockId + Block.blocksList.length * blockMeta);
	}
	
	static {
		addToWhiteList(Block.tallGrass);
		addToWhiteList(Block.leaves);
	}
}
