package lasermod.util;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import lasermod.api.ILaserProvider;
import lasermod.api.ILaserReceiver;
import lasermod.compat.forgemultipart.ForgeMultipartCompat;
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
	
	public boolean isLaserReciver(int orientation) {
		if(Loader.isModLoaded("ForgeMultipart")) {
			try {
				if(ForgeMultipartCompat.isTileMultipart(this.tileEntity))
					return true;
			}
			catch(Throwable e) {
				e.printStackTrace();
			
			}
    	}
	
		return this.tileEntity instanceof ILaserReceiver;
	}
	
	public ILaserReceiver getLaserReceiver() {
		if(Loader.isModLoaded("ForgeMultipart")) {
			try {
				ILaserReceiver reciver = ForgeMultipartCompat.getLaserReciverFromPart(this.tileEntity);
				if(reciver != null)
					return reciver;
			}
			catch(Throwable e) {
				e.printStackTrace();
			
			}
    	}
		return (ILaserReceiver)this.tileEntity;
	}
	
	public boolean isLaserProvider(int orientation) {
		if(Loader.isModLoaded("ForgeMultipart")) {
			try {
				if(ForgeMultipartCompat.isTileMultipart(this.tileEntity))
					return true;
			}
			catch(Throwable e) {
				e.printStackTrace();
			
			}
    	}
	
		return this.tileEntity instanceof ILaserProvider;
	}
	
	public ILaserProvider getLaserProvider() {
		if(Loader.isModLoaded("ForgeMultipart")) {
			try {
				ILaserProvider provider = ForgeMultipartCompat.getLaserProviderFromPart(this.tileEntity);
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
