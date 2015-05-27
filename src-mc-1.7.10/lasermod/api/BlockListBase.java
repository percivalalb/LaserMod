package lasermod.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;

/**
 * @author ProPercivalalb
 */
public class BlockListBase {

	public static int ALL_METADATA = -1;
	
	public List<List> list = new ArrayList<List>();
	
	//Convenience method
	public void addToList(Block block, int blockMeta) { addToList(Block.blockRegistry.getNameForObject(block), blockMeta); }
	public void addToList(int blockId, int blockMeta) { addToList(Block.blockRegistry.getNameForObject(Block.getBlockById(blockId)), blockMeta); }
	public void addToList(int blockId) { addToList(Block.blockRegistry.getNameForObject(Block.getBlockById(blockId)), ALL_METADATA); }
	public void addToList(Block block) { addToList(Block.blockRegistry.getNameForObject(block), ALL_METADATA); }
	public void addToList(String blockName)  { addToList(blockName, ALL_METADATA); }
	
	public void addToList(String blockName, int blockMeta) {
		if(blockMeta == ALL_METADATA)
			for(int i = 0; i < 16; ++i)
				this.list.add(Arrays.asList(blockName, i));
		else
			this.list.add(Arrays.asList(blockName, blockMeta));
	}
	
	public boolean contains(Block block, int meta) {
		return this.list.contains(Arrays.asList(Block.blockRegistry.getNameForObject(block), meta));
	}
}
