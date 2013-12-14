package lasermod.tileentity;

import lasermod.ModBlocks;
import lasermod.api.ILaserReciver;
import lasermod.api.LaserWhitelist;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraftforge.common.ForgeDirection;

/**
 * @author ProPercivalalb
 */
public class TileEntityLaserDetector extends TileEntity {

	@Override
	public void updateEntity() {
		int count = 0;
		for(int i = 0; i < 6; ++i) {
			if(this.getBlockMetadata() == 1 && !isValidSourceOfPowerOnSide(i)) {
				count++;
			}
		}
		if(count == 6)
			this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 0, 2);
	}
	
	public boolean isValidSourceOfPowerOnSide(int side) {
		if (side == ForgeDirection.DOWN.ordinal()) {
        	for(int i = this.yCoord - 1; this.yCoord - i >= 0; --i) {
        		if(!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, i, this.zCoord)) {
        			Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord, i, this.zCoord)];
        			if(block != null && block.blockID == ModBlocks.basicLaser.blockID) {
        				return true;
        			}
        			else if(block != null && block instanceof ILaserReciver) {
        				return ((ILaserReciver)block).canPassOnSide(this.worldObj, this.xCoord, i, this.zCoord, this.xCoord, this.yCoord, this.zCoord, Facing.oppositeSide[side]);
        			}
        			break;
        		}
        	}
        }
        else if (side == ForgeDirection.UP.ordinal()) {
        	for(int i = this.yCoord + 1; i < this.yCoord + 64; ++i) {
        		if(!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, i, this.zCoord)) {
        			Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord, i, this.zCoord)];
        			if(block != null && block.blockID == ModBlocks.basicLaser.blockID) {
        				return true;
        			}
        			else if(block != null && block instanceof ILaserReciver) {
        				return ((ILaserReciver)block).canPassOnSide(this.worldObj, this.xCoord, i, this.zCoord, this.xCoord, this.yCoord, this.zCoord, Facing.oppositeSide[side]);
        			}
        			break;
        		}
        	}
        }
        else if (side == ForgeDirection.NORTH.ordinal()) {
        	for(int i = 1; i < 64; ++i) {
        		if(!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, this.yCoord, this.zCoord - i)) {
        			Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord - i)];
        			if(block != null && block.blockID == ModBlocks.basicLaser.blockID) {
        				return true;
        			}
        			else if(block != null && block instanceof ILaserReciver) {
        				return ((ILaserReciver)block).canPassOnSide(this.worldObj, this.xCoord, this.yCoord, this.zCoord - i, this.xCoord, this.yCoord, this.zCoord, Facing.oppositeSide[side]);
        			}
        			break;
        		}
        	}
        }
        else if (side == ForgeDirection.SOUTH.ordinal()) {
        	for(int i = 1; i < 64; ++i) {
        		if(!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, this.yCoord, this.zCoord + i)) {
        			Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord + i)];
        			if(block != null && block.blockID == ModBlocks.basicLaser.blockID) {
        				return true;
        			}
        			else if(block != null && block instanceof ILaserReciver) {
        				return ((ILaserReciver)block).canPassOnSide(this.worldObj, this.xCoord, this.yCoord, this.zCoord + i, this.xCoord, this.yCoord, this.zCoord, Facing.oppositeSide[side]);
        			}
        			break;
        		}
        	}
        }
        else if (side == ForgeDirection.WEST.ordinal()) {
        	for(int i = 1; i < 64; ++i) {
        		if(!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord - i, this.yCoord, this.zCoord)) {
        			Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord - i, this.yCoord, this.zCoord)];
        			if(block != null && block.blockID == ModBlocks.basicLaser.blockID) {
        				return true;
        			}
        			else if(block != null && block instanceof ILaserReciver) {
        				return ((ILaserReciver)block).canPassOnSide(this.worldObj, this.xCoord - i, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord, Facing.oppositeSide[side]);
        			}
        			break;
        		}
        	}
        }
        else if (side == ForgeDirection.EAST.ordinal()) {
        	for(int i = 1; i < 64; ++i) {
        		if(!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord + i, this.yCoord, this.zCoord)) {
        			Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord + i, this.yCoord, this.zCoord)];
        			if(block != null && block.blockID == ModBlocks.basicLaser.blockID) {
        				return true;
        			}
        			else if(block != null && block instanceof ILaserReciver) {
        				return ((ILaserReciver)block).canPassOnSide(this.worldObj, this.xCoord + i, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord, Facing.oppositeSide[side]);
        			}
        			break;
        		}
        	}
        }
        
    	return false;
	}
}
