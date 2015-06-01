package lasermod.util;

import lasermod.api.ILaserProvider;
import lasermod.api.ILaserReceiver;
import lasermod.compat.forgemultipart.ForgeMultipartCompat;
import lasermod.tileentity.TileEntitySmallColourConverter;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.Loader;

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
	
	public boolean isLaserReceiver(ForgeDirection dir) {
		if(Loader.isModLoaded("ForgeMultipart")) {
			try {
				if(ForgeMultipartCompat.isTileMultipart(this.tileEntity, dir))
					return true;
			}
			catch(Throwable e) {
				e.printStackTrace();
			
			}
    	}
		
		if(this.tileEntity instanceof TileEntitySmallColourConverter) {
			return dir.ordinal() == this.tileEntity.getBlockMetadata() || dir == ForgeDirection.UNKNOWN;
		}

		return this.tileEntity instanceof ILaserReceiver;
	}
	
	public ILaserReceiver getLaserReceiver(ForgeDirection dir) {
		if(Loader.isModLoaded("ForgeMultipart")) {
			try {
				ILaserReceiver reciver = ForgeMultipartCompat.getLaserReceiverFromPart(this.tileEntity, dir);
				if(reciver != null)
					return reciver;
			}
			catch(Throwable e) {
				e.printStackTrace();
			
			}
    	}
		return (ILaserReceiver)this.tileEntity;
	}
	
	public boolean isLaserProvider(ForgeDirection dir) {
		if(Loader.isModLoaded("ForgeMultipart")) {
			try {
				if(ForgeMultipartCompat.isTileMultipart(this.tileEntity, dir))
					return true;
			}
			catch(Throwable e) {
				e.printStackTrace();
			
			}
    	}
		if(this.tileEntity instanceof TileEntitySmallColourConverter) {
			return dir.ordinal() == this.tileEntity.getBlockMetadata() || dir == ForgeDirection.UNKNOWN;
		}
		
		return this.tileEntity instanceof ILaserProvider;
	}
	
	public ILaserProvider getLaserProvider(ForgeDirection dir) {
		if(Loader.isModLoaded("ForgeMultipart")) {
			try {
				ILaserProvider provider = ForgeMultipartCompat.getLaserProviderFromPart(this.tileEntity, dir);
				if(provider != null)
					return provider;
			}
			catch(Throwable e) {
				e.printStackTrace();
			
			}
    	}
		return (ILaserProvider)this.tileEntity;
	}
	
	public String toString() {
		return String.format("%d, %d, %d", this.x, this.y, this.z);
	}
}
