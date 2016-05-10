package lasermod.util;

import lasermod.api.ILaserProvider;
import lasermod.api.ILaserReceiver;
import lasermod.tileentity.TileEntitySmallColourConverter;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockActionPos {

	public World world;
	public BlockPos pos;
	public IBlockState state;
	public Block block;
	public int meta;
	public TileEntity tileEntity;
	
	public BlockActionPos(World world, BlockPos pos, Block block, int meta, TileEntity tileEntity) {
		this.world = world;
		this.pos = pos;
		this.tileEntity = tileEntity;
		this.state = block.getStateFromMeta(meta);
		this.block = block;
		this.meta = meta;
	}

	public BlockActionPos(World world, BlockPos pos) {
		this.world = world;
		this.pos = pos;

		this.tileEntity = world.getTileEntity(pos);
		this.state = world.getBlockState(pos);
		this.block = this.state.getBlock();
		this.meta = this.block.getMetaFromState(this.state);
	}
	
	public boolean isLaserReceiver(EnumFacing dir) {
		if(this.tileEntity instanceof TileEntitySmallColourConverter) {
			return dir == null || dir.ordinal() == this.tileEntity.getBlockMetadata() ;
		}

		return this.tileEntity instanceof ILaserReceiver;
	}
	
	public ILaserReceiver getLaserReceiver(EnumFacing dir) {
		return (ILaserReceiver)this.tileEntity;
	}
	
	public boolean isLaserProvider(EnumFacing dir) {
		
		if(this.tileEntity instanceof TileEntitySmallColourConverter) {
			return dir == null || dir.ordinal() == this.tileEntity.getBlockMetadata();
		}
		
		return this.tileEntity instanceof ILaserProvider;
	}
	
	public ILaserProvider getLaserProvider(EnumFacing dir) {
		
		return (ILaserProvider)this.tileEntity;
	}
	
	@Override
	public String toString() {
		return this.pos.toString();
	}
}
