package lasermod.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * @author ProPercivalalb
 */
public class BlockListBase {

	public static int ALL_METADATA = -1;
	
	public List<List> list = new ArrayList<List>();
	
	//Convenience method
	public void addToList(Block block, int blockMeta) { addToList(ForgeRegistries.BLOCKS.getKey(block), blockMeta); }
	public void addToList(Block block) { addToList(ForgeRegistries.BLOCKS.getKey(block)); }
	public void addToList(ResourceLocation blockName)  { addToList(blockName, ALL_METADATA); }
	
	public void addToList(ResourceLocation blockName, int blockMeta) {
		if(blockMeta == ALL_METADATA) {
			for(int i = 0; i < 16; ++i) this.list.add(Arrays.asList(blockName, i));
		}
		else this.list.add(Arrays.asList(blockName, blockMeta));
	}
	
	public boolean contains(Block block, int meta) { return this.list.contains(Arrays.asList(ForgeRegistries.BLOCKS.getKey(block), meta)); }
}
