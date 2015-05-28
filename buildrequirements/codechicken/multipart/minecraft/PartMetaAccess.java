package codechicken.multipart.minecraft;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

public class PartMetaAccess implements IBlockAccess {
	
	public IPartMeta part;
	
	public PartMetaAccess(IPartMeta p) {}

	@Override
	public TileEntity getTileEntity(int x, int y, int z) { return null; }

	@Override
	public Block getBlock(int p_147439_1_, int p_147439_2_, int p_147439_3_) { return null; }

	@Override
	public int getLightBrightnessForSkyBlocks(int p_72802_1_, int p_72802_2_,
			int p_72802_3_, int p_72802_4_) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBlockMetadata(int p_72805_1_, int p_72805_2_, int p_72805_3_) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int isBlockProvidingPowerTo(int p_72879_1_, int p_72879_2_,
			int p_72879_3_, int p_72879_4_) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isAirBlock(int p_147437_1_, int p_147437_2_, int p_147437_3_) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BiomeGenBase getBiomeGenForCoords(int p_72807_1_, int p_72807_2_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean extendedLevelsInChunkCache() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSideSolid(int x, int y, int z, ForgeDirection side,
			boolean _default) {
		// TODO Auto-generated method stub
		return false;
	}

}
