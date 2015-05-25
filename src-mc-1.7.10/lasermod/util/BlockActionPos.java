package lasermod.util;

import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import cpw.mods.fml.common.FMLLog;
import lasermod.api.ILaserProvider;
import lasermod.api.ILaserReceiver;
import lasermod.compat.forgemultipart.SmallColourConverterPart;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockActionPos {

	public World world;
	public int x, y, z;
	public Block block;
	public int meta;
	public TileEntity tileEntity;
	public SmallColourConverterPart part;
	
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
		if(this.tileEntity instanceof TileMultipart) {
            TileMultipart tem = (TileMultipart)this.tileEntity;

            for (TMultiPart t : tem.jPartList()) {
                if(t instanceof SmallColourConverterPart)
                	if(((SmallColourConverterPart) t).meta == orientation) {
                		part = (SmallColourConverterPart)t;
                		return true;
                	}
            }
        }
	
		return this.tileEntity instanceof ILaserReceiver;
	}
	
	public ILaserReceiver getLaserReceiver() {
		if(this.part != null)
			return this.part;
		return (ILaserReceiver)this.tileEntity;
	}
	
	public boolean isLaserProvider(int orientation) {
		if(this.tileEntity instanceof TileMultipart) {
            TileMultipart tem = (TileMultipart)this.tileEntity;

            for (TMultiPart t : tem.jPartList()) {
                if(t instanceof SmallColourConverterPart)
                	if(((SmallColourConverterPart) t).meta == orientation) {
                		part = (SmallColourConverterPart)t;
                		return true;
                	}
            }
        }
	
		return this.tileEntity instanceof ILaserProvider;
	}
	
	public ILaserProvider getLaserProvider() {
		if(this.part != null)
			return this.part;
		return (ILaserProvider)this.tileEntity;
	}
}
