package lasermod.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lasermod.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author ProPercivalalb
 */
public class LaserWhitelist {

	public static int ALL_METADATA = -1;
	
	public static List<List> WHITELIST = new ArrayList<List>();
	public static List<List> BLACKLIST = new ArrayList<List>();
	
	//Convenience method
	public static void addToWhiteList(Block block, int blockMeta) { addToWhiteList(Block.blockRegistry.getNameForObject(block), blockMeta); }
	public static void addToWhiteList(int blockId, int blockMeta) { addToWhiteList(Block.blockRegistry.getNameForObject(Block.getBlockById(blockId)), blockMeta); }
	public static void addToWhiteList(int blockId) { addToWhiteList(Block.blockRegistry.getNameForObject(Block.getBlockById(blockId)), -1); }
	public static void addToWhiteList(Block block) { addToWhiteList(Block.blockRegistry.getNameForObject(block), -1); }
	
	//Convenience method
	public static void addToBlackList(Block block, int blockMeta) { addToBlackList(Block.blockRegistry.getNameForObject(block), blockMeta); }
	public static void addToBlackList(int blockId, int blockMeta) { addToBlackList(Block.blockRegistry.getNameForObject(Block.getBlockById(blockId)), blockMeta); }
	public static void addToBlackList(int blockId) { addToBlackList(Block.blockRegistry.getNameForObject(Block.getBlockById(blockId)), -1); }
	public static void addToBlackList(Block block) { addToBlackList(Block.blockRegistry.getNameForObject(block), -1); }
	
	
	public static void addToWhiteList(String blockName, int blockMeta) {
		if(blockMeta == ALL_METADATA)
			for(int i = 0; i < 16; ++i)
				WHITELIST.add(Arrays.asList(blockName, i));
		else
			WHITELIST.add(Arrays.asList(blockName, blockMeta));
	}
	
	public static void addToBlackList(String blockName, int blockMeta) {
		if(blockMeta == ALL_METADATA)
			for(int i = 0; i < 16; ++i)
				BLACKLIST.add(Arrays.asList(blockName, i));
		else
			BLACKLIST.add(Arrays.asList(blockName, blockMeta));
	}
	
	//Convenience method
	public static boolean canLaserPassThrought(World world, int x, int y, int z, ForgeDirection sideHit) {
		Chunk chunk = world.getChunkFromBlockCoords(x, z);
		if(chunk == null || !chunk.isChunkLoaded)
			return false;
		
		Block block = world.getBlock(x, y, z);
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		boolean blockaccepted = block.isAir(world, x, y, z) || !(block.isSideSolid(world, x, y, z, sideHit) || block.isSideSolid(world, x, y, z, sideHit.getOpposite())) || canLaserPassThrought(block, world.getBlockMetadata(x, y, z));
	
		return blockaccepted; 
	}
	
	/**
	 * Checks whether the laser can pass through a block
	 * @param block The block of the target location
	 * @param blockMeta The blockMeta of the target location
	 * @return If the laser is stopped by the given block
	 */
	public static boolean canLaserPassThrought(Block block, int meta) {
		return WHITELIST.contains(Arrays.asList(Block.blockRegistry.getNameForObject(block), meta));
	}
	
	public static boolean neverAllowThrough(Block block, int meta) {
		return BLACKLIST.contains(Arrays.asList(Block.blockRegistry.getNameForObject(block), meta));
	}
	
	static {
		addToWhiteList(Blocks.air);
		addToWhiteList(Blocks.ice);
		addToWhiteList(Blocks.tallgrass);
		addToWhiteList(Blocks.double_plant);
		addToWhiteList(Blocks.leaves);
		addToWhiteList(Blocks.leaves2);
		addToWhiteList(Blocks.lever);
		addToWhiteList(Blocks.torch);
		addToWhiteList(Blocks.ladder);
		addToWhiteList(Blocks.redstone_torch);
		addToWhiteList(Blocks.unlit_redstone_torch);
		addToWhiteList(Blocks.redstone_wire);
		addToWhiteList(Blocks.glass);
		addToWhiteList(Blocks.stained_glass);
		addToWhiteList(Blocks.glass_pane);
		addToWhiteList(Blocks.stained_glass_pane);
		
		//Mods
		addToWhiteList("TConstruct:decoration.stonetorch", -1);
		
		
		addToBlackList(ModBlocks.laserDetector);
		//addToBlackList(ModBlocks.smallColourConverter);
	}
}
