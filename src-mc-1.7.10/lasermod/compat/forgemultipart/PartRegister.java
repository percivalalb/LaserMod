package lasermod.compat.forgemultipart;

import java.util.Arrays;

import lasermod.ModBlocks;
import lasermod.tileentity.TileEntitySmallColourConverter;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import codechicken.lib.vec.BlockCoord;
import codechicken.multipart.MultiPartRegistry.IPartConverter;
import codechicken.multipart.MultiPartRegistry.IPartFactory;
import codechicken.multipart.TMultiPart;

/**
 * @author ProPercivalalb
 */
public class PartRegister implements IPartFactory, IPartConverter {

	@Override
	public Iterable<Block> blockTypes() {
		return Arrays.asList(ModBlocks.smallColourConverter);
	}

	@Override
	public TMultiPart convert(World world, BlockCoord pos) {
		Block b = world.getBlock(pos.x, pos.y, pos.z);
		int meta = world.getBlockMetadata(pos.x, pos.y, pos.z);
		TileEntity tileEntity = world.getTileEntity(pos.x, pos.y, pos.z);
		if(b == ModBlocks.smallColourConverter) return new SmallColourConverterPart((byte)meta, ((TileEntitySmallColourConverter)tileEntity).colour);
		return null;
	}

	@Override
	public TMultiPart createPart(String name, boolean client) {
		if(name.equals("lasermod:smallcolorconverter")) return new SmallColourConverterPart();
		return null;
	}

}
