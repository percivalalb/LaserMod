package lasermod.util;

import lasermod.api.ILaserReceiver;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockActionPos {

	public World world;
	public int x, y, z;
	public Block block;
	public int meta;
	public TileEntity tileEntity;
	
	public BlockActionPos(World world, int x, int y, int z, Block block, int meta, TileEntity tileEntity) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.tileEntity = tileEntity;
		this.block = block;
		this.meta = meta;
	}

	public BlockActionPos(World world, int x, int y, int z) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.tileEntity = world.getTileEntity(x, y, z);
		this.block = world.getBlock(x, y, z);
		this.meta = world.getBlockMetadata(x, y, z);
	}
	
	public boolean isLaserReciver() {
		return this.tileEntity instanceof ILaserReceiver;
	}
	
	public ILaserReceiver getLaserReceiver() {
		return (ILaserReceiver)tileEntity;
	}
}
