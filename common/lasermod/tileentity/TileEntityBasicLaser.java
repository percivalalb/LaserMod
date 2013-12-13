package lasermod.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lasermod.api.ILaserReciver;
import lasermod.api.LaserWhitelist;
import lasermod.core.helper.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraftforge.common.ForgeDirection;

/**
 * @author ProPercivalalb
 */
public class TileEntityBasicLaser extends TileEntity {

	public AxisAlignedBB last = AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0);
	public int[] reciverCords = new int[3];
	
	@Override
	public void updateEntity() {
		ILaserReciver reciver = getFirstReciver();
		if(reciver != null) {
			if(reciver.canPassOnSide(worldObj, reciverCords[0], reciverCords[1], reciverCords[2], this.xCoord, this.yCoord, this.zCoord, Facing.oppositeSide[this.getBlockMetadata()])) {
				LogHelper.logInfo("Pass");
			}
		}
		
		//LogHelper.logInfo("Reciver: " + (reciver != null));
	}
	
	public ILaserReciver getFirstReciver() {
	   	if(!this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord))
    		return null;
		
		int meta = this.getBlockMetadata();

        if (meta == ForgeDirection.DOWN.ordinal()) {
        	for(int i = this.yCoord - 1; i >= 0; --i) {
        		if(!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, i, this.zCoord)) {
        			Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord, i, this.zCoord)];
        			if(block != null && block instanceof ILaserReciver) {
        				reciverCords = new int[] {this.xCoord, i, this.zCoord};
        				return (ILaserReciver)block;
        			}
        			break;
        		}
        	}
        }
        else if (meta == ForgeDirection.UP.ordinal()) {
        	for(int i = this.yCoord + 1; i < 256; ++i) {
        		if(!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, i, this.zCoord)) {
        			Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord, i, this.zCoord)];
        			if(block != null && block instanceof ILaserReciver) {
        				reciverCords = new int[] {this.xCoord, i, this.zCoord};
        				return (ILaserReciver)block;
        			}
        			break;
        		}
        	}
        }
        else if (meta == ForgeDirection.NORTH.ordinal()) {
        	for(int i = 1; i < 256; ++i) {
        		if(!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, this.yCoord, this.zCoord - i)) {
        			Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord - i)];
        			if(block != null && block instanceof ILaserReciver) {
        				reciverCords = new int[] {this.xCoord, this.yCoord, this.zCoord - i};
        				return (ILaserReciver)block;
        			}
        			break;
        		}
        	}
        }
        else if (meta == ForgeDirection.SOUTH.ordinal()) {
        	for(int i = 1; i < 256; ++i) {
        		if(!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, this.yCoord, this.zCoord + i)) {
        			Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord + i)];
        			if(block != null && block instanceof ILaserReciver) {
        				reciverCords = new int[] {this.xCoord, this.yCoord, this.zCoord + i};
        				return (ILaserReciver)block;
        			}
        			break;
        		}
        	}
        }
        else if (meta == ForgeDirection.WEST.ordinal()) {
        	for(int i = 1; i < 256; ++i) {
        		if(LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord - i, this.yCoord, this.zCoord)) {
        			Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord - i, this.yCoord, this.zCoord)];
        			if(block != null && block instanceof ILaserReciver) {
        				reciverCords = new int[] {this.xCoord - i, this.yCoord, this.zCoord};
        				return (ILaserReciver)block;
        			}
        			break;
        		}
        	}
        }
        else if (meta == ForgeDirection.EAST.ordinal()) {
        	for(int i = 1; i < 256; ++i) {
        		if(!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord + i, this.yCoord, this.zCoord)) {
        			Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord + i, this.yCoord, this.zCoord)];
        			if(block != null && block instanceof ILaserReciver) {
        				reciverCords = new int[] {this.xCoord + i, this.yCoord, this.zCoord};
        				return (ILaserReciver)block;
        			}
        			break;
        		}
        	}
        }
        
        return null;
	}
	
	public AxisAlignedBB getLaserBox(double x, double y, double z) {
		int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
		double laserSize = 0.4D;
		AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(x + 0.5D - laserSize / 2, y + 0.5D - laserSize / 2, z + 0.5D - laserSize / 2, x + 0.5D + laserSize / 2, y + 0.5D + laserSize / 2, z + 0.5D + laserSize / 2);
		
		double extraMinX = 0.0D;
		double extraMinY = 0.0D;
		double extraMinZ = 0.0D;
		
		double extraMaxX = 0.0D;
		double extraMaxY = 0.0D;
		double extraMaxZ = 0.0D;
		
        if (meta == ForgeDirection.DOWN.ordinal()) {
        	for(int i = this.yCoord - 1; i >= 0; --i) {
        		if(LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, i, this.zCoord)) {
        			extraMinY++;
        		}
        		else {
        			extraMinY += 1.0D - laserSize;
        			break;
        		}
        	}
        }
        else if (meta == ForgeDirection.UP.ordinal()) {
        	for(int i = this.yCoord + 1; i < 256; ++i) {
        		if(LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, i, this.zCoord)) {
        			extraMaxY++;
        		}
        		else {
        			extraMaxY += 1.0D - laserSize;
        			break;
        		}
        	}
            //return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, (double)xCoord + 1.0D, (double)yCoord + 1.0D + d, (double)zCoord + 1.0D);
        }
        else if (meta == ForgeDirection.NORTH.ordinal()) {
        	for(int i = 1; i < 256; ++i) {
        		if(LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, this.yCoord, this.zCoord - i)) {
        			extraMinZ++;
        		}
        		else {
        			extraMinZ += 1.0D - laserSize;
        			break;
        		}
        	}
            //return AxisAlignedBB.getBoundingBox(xCoord, yCoord, (double)zCoord - d, (double)xCoord + 1.0D, (double)yCoord + 1.0D, (double)zCoord + 1.0D);
        }
        else if (meta == ForgeDirection.SOUTH.ordinal()) {
        	for(int i = 1; i < 256; ++i) {
        		if(LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, this.yCoord, this.zCoord + i)) {
        			extraMaxZ++;
        		}
        		else {
        			extraMaxZ += 1.0D - laserSize;
        			break;
        		}
        	}
        	//return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, (double)xCoord + 1.0D, (double)yCoord + 1.0D, (double)zCoord + 1.0D + d);
        }
        else if (meta == ForgeDirection.WEST.ordinal()) {
        	for(int i = 1; i < 256; ++i) {
        		if(LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord - i, this.yCoord, this.zCoord)) {
        			extraMinX++;
        		}
        		else {
        			extraMinX += 1.0D - laserSize;
        			break;
        		}
        	}
            //return AxisAlignedBB.getBoundingBox((double)xCoord - d, yCoord, zCoord, (double)xCoord + 1.0D, (double)yCoord + 1.0D, (double)zCoord + 1.0D);
        }
        else if (meta == ForgeDirection.EAST.ordinal()) {
        	for(int i = 1; i < 256; ++i) {
        		if(LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord + i, this.yCoord, this.zCoord)) {
        			extraMaxX++;
        		}
        		else {
        			extraMaxX += 1.0D - laserSize;
        			break;
        		}
        	}
        	//return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, (double)xCoord + 1.0D + d, (double)yCoord + 1.0D, (double)zCoord + 1.0D);
        }
        boundingBox.setBounds(boundingBox.minX - extraMinX, boundingBox.minY - extraMinY, boundingBox.minZ - extraMinZ, boundingBox.maxX + extraMaxX, boundingBox.maxY + extraMaxY, boundingBox.maxZ + extraMaxZ);
        
        return boundingBox;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
    	return INFINITE_EXTENT_AABB;
    }
}
