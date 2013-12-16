package lasermod.tileentity;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lasermod.ModBlocks;
import lasermod.api.ILaserReciver;
import lasermod.api.LaserInGame;
import lasermod.api.LaserRegistry;
import lasermod.api.LaserWhitelist;
import lasermod.core.helper.LogHelper;
import lasermod.lib.Constants;
import lasermod.packet.PacketAdvancedLaserUpdate;
import lasermod.packet.PacketColourConverterUpdate;
import net.minecraft.block.Block;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraftforge.common.ForgeDirection;

/**
 * @author ProPercivalalb
 */
public class TileEntityColourConverter extends TileEntity {

	public AxisAlignedBB last = AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0);
	public int[] reciverCords = new int[3];
	public LaserInGame laser = null;
	public boolean hadPower = false;
	public int lagReduce = 0;
	
	@Override
	public void updateEntity() {
		if(this.lagReduce == 1) {
			this.lagReduce = 0;
			return;
		}
		
		if(this.laser != null) {
			ILaserReciver reciver = getFirstReciver(this.getBlockMetadata());
			if(reciver != null) {
			  	if(reciver.canPassOnSide(worldObj, reciverCords[0], reciverCords[1], reciverCords[2], this.xCoord, this.yCoord, this.zCoord, Facing.oppositeSide[this.getBlockMetadata()])) {
					reciver.passLaser(worldObj, reciverCords[0], reciverCords[1], reciverCords[2], this.xCoord, this.yCoord, this.zCoord, this.laser);
				}
			}
		}
		
		if(!this.isValidSourceOfPowerOnSide(this.getBlockMetadata())) {
			this.laser = null;
			if(!this.worldObj.isRemote)
				PacketDispatcher.sendPacketToAllAround(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, this.worldObj.provider.dimensionId, 512, this.getDescriptionPacket());
		}
		this.lagReduce += 1;
	}
	
	public ILaserReciver getFirstReciver(int meta) {
        if (meta == ForgeDirection.DOWN.ordinal()) {
        	for(int i = this.yCoord - 1; this.yCoord - i >= 0; --i) {
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
        	for(int i = this.yCoord + 1; i < this.yCoord + Constants.LASER_REACH; ++i) {
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
        	for(int i = 1; i < Constants.LASER_REACH; ++i) {
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
        	for(int i = 1; i < Constants.LASER_REACH; ++i) {
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
        	for(int i = 1; i < Constants.LASER_REACH; ++i) {
        		if(!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord - i, this.yCoord, this.zCoord)) {
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
        	for(int i = 1; i < Constants.LASER_REACH; ++i) {
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
		int meta = this.getBlockMetadata();
		double laserSize = 0.4D;
		AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(x + 0.5D - laserSize / 2, y + 0.5D - laserSize / 2, z + 0.5D - laserSize / 2, x + 0.5D + laserSize / 2, y + 0.5D + laserSize / 2, z + 0.5D + laserSize / 2);
		
		double extraMinX = 0.0D;
		double extraMinY = 0.0D;
		double extraMinZ = 0.0D;
		
		double extraMaxX = 0.0D;
		double extraMaxY = 0.0D;
		double extraMaxZ = 0.0D;
		
        if (meta == ForgeDirection.DOWN.ordinal()) {
        	for(int i = this.yCoord - 1; this.yCoord - i >= 0; --i) {
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
        	for(int i = this.yCoord + 1; i < this.yCoord + Constants.LASER_REACH; ++i) {
        		if(LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, i, this.zCoord)) {
        			extraMaxY++;
        		}
        		else {
        			extraMaxY += 1.0D - laserSize;
        			break;
        		}
        	}
        }
        else if (meta == ForgeDirection.NORTH.ordinal()) {
        	for(int i = 1; i < Constants.LASER_REACH; ++i) {
        		if(LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, this.yCoord, this.zCoord - i)) {
        			extraMinZ++;
        		}
        		else {
        			extraMinZ += 1.0D - laserSize;
        			break;
        		}
        	}
        }
        else if (meta == ForgeDirection.SOUTH.ordinal()) {
        	for(int i = 1; i < Constants.LASER_REACH; ++i) {
        		if(LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, this.yCoord, this.zCoord + i)) {
        			extraMaxZ++;
        		}
        		else {
        			extraMaxZ += 1.0D - laserSize;
        			break;
        		}
        	}
        }
        else if (meta == ForgeDirection.WEST.ordinal()) {
        	for(int i = 1; i < Constants.LASER_REACH; ++i) {
        		if(LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord - i, this.yCoord, this.zCoord)) {
        			extraMinX++;
        		}
        		else {
        			extraMinX += 1.0D - laserSize;
        			break;
        		}
        	}
        }
        else if (meta == ForgeDirection.EAST.ordinal()) {
        	for(int i = 1; i < Constants.LASER_REACH; ++i) {
        		if(LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord + i, this.yCoord, this.zCoord)) {
        			extraMaxX++;
        		}
        		else {
        			extraMaxX += 1.0D - laserSize;
        			break;
        		}
        	}
        }
        boundingBox.setBounds(boundingBox.minX - extraMinX, boundingBox.minY - extraMinY, boundingBox.minZ - extraMinZ, boundingBox.maxX + extraMaxX, boundingBox.maxY + extraMaxY, boundingBox.maxZ + extraMaxZ);
        
        return boundingBox;
	}
	
	public LaserInGame getCreatedLaser() {
		if(laser == null)
			laser = new LaserInGame(LaserRegistry.getLaserFromId("default")).setSide(Facing.oppositeSide[this.getBlockMetadata()]);
		
		return laser;
	}
	

	@Override
	public Packet getDescriptionPacket() {
	    return new PacketColourConverterUpdate(this.xCoord, this.yCoord, this.zCoord, this).buildPacket();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
    	return INFINITE_EXTENT_AABB;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 6400.0D;
    }
	
	public boolean isValidSourceOfPowerOnSide(int side) {
		if (side == ForgeDirection.DOWN.ordinal()) {
        	for(int i = this.yCoord - 1; this.yCoord - i >= 0; --i) {
        		if(!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, i, this.zCoord)) {
        			Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord, i, this.zCoord)];
        			if(block != null && (block.blockID == ModBlocks.basicLaser.blockID || block.blockID == ModBlocks.advancedLaser.blockID)) {
            			int meta = this.worldObj.getBlockMetadata(this.xCoord, i, this.zCoord);
            			boolean hasPower = worldObj.isBlockIndirectlyGettingPowered(this.xCoord, i, this.zCoord);
        				return meta == Facing.oppositeSide[side] && hasPower;
        			}
        			else if(block != null && block instanceof ILaserReciver) {
        				return ((ILaserReciver)block).isSendingSignalFromSide(this.worldObj, this.xCoord, i, this.zCoord, this.xCoord, this.yCoord, this.zCoord, Facing.oppositeSide[side]);
        			}
        			break;
        		}
        	}
        }
        else if (side == ForgeDirection.UP.ordinal()) {
        	for(int i = this.yCoord + 1; i < this.yCoord + Constants.LASER_REACH; ++i) {
        		if(!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, i, this.zCoord)) {
        			Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord, i, this.zCoord)];
        			if(block != null && (block.blockID == ModBlocks.basicLaser.blockID || block.blockID == ModBlocks.advancedLaser.blockID)) {
        				int meta = this.worldObj.getBlockMetadata(this.xCoord, i, this.zCoord);
            			boolean hasPower = worldObj.isBlockIndirectlyGettingPowered(this.xCoord, i, this.zCoord);
        				return meta == Facing.oppositeSide[side] && hasPower;
        			}
        			else if(block != null && block instanceof ILaserReciver) {
        				return ((ILaserReciver)block).isSendingSignalFromSide(this.worldObj, this.xCoord, i, this.zCoord, this.xCoord, this.yCoord, this.zCoord, Facing.oppositeSide[side]);
        			}
        			break;
        		}
        	}
        }
        else if (side == ForgeDirection.NORTH.ordinal()) {
        	for(int i = 1; i < Constants.LASER_REACH; ++i) {
        		if(!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, this.yCoord, this.zCoord - i)) {
        			Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord - i)];
        			if(block != null && (block.blockID == ModBlocks.basicLaser.blockID || block.blockID == ModBlocks.advancedLaser.blockID)) {
        				int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord - i);
            			boolean hasPower = worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord - i);
        				return meta == Facing.oppositeSide[side] && hasPower;
        			}
        			else if(block != null && block instanceof ILaserReciver) {
        				return ((ILaserReciver)block).isSendingSignalFromSide(this.worldObj, this.xCoord, this.yCoord, this.zCoord - i, this.xCoord, this.yCoord, this.zCoord, Facing.oppositeSide[side]);
        			}
        			break;
        		}
        	}
        }
        else if (side == ForgeDirection.SOUTH.ordinal()) {
        	for(int i = 1; i < Constants.LASER_REACH; ++i) {
        		if(!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, this.yCoord, this.zCoord + i)) {
        			Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord + i)];
        			if(block != null && (block.blockID == ModBlocks.basicLaser.blockID || block.blockID == ModBlocks.advancedLaser.blockID)) {
        				int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord + i);
            			boolean hasPower = worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord + i);
        				return meta == Facing.oppositeSide[side] && hasPower;
        			}
        			else if(block != null && block instanceof ILaserReciver) {
        				return ((ILaserReciver)block).isSendingSignalFromSide(this.worldObj, this.xCoord, this.yCoord, this.zCoord + i, this.xCoord, this.yCoord, this.zCoord, Facing.oppositeSide[side]);
        			}
        			break;
        		}
        	}
        }
        else if (side == ForgeDirection.WEST.ordinal()) {
        	for(int i = 1; i < Constants.LASER_REACH; ++i) {
        		if(!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord - i, this.yCoord, this.zCoord)) {
        			Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord - i, this.yCoord, this.zCoord)];
        			if(block != null && (block.blockID == ModBlocks.basicLaser.blockID || block.blockID == ModBlocks.advancedLaser.blockID)) {
        				int meta = this.worldObj.getBlockMetadata(this.xCoord - i, this.yCoord, this.zCoord);
            			boolean hasPower = worldObj.isBlockIndirectlyGettingPowered(this.xCoord - i, this.yCoord, this.zCoord);
        				return meta == Facing.oppositeSide[side] && hasPower;
        			}
        			else if(block != null && block instanceof ILaserReciver) {
        				return ((ILaserReciver)block).isSendingSignalFromSide(this.worldObj, this.xCoord - i, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord, Facing.oppositeSide[side]);
        			}
        			break;
        		}
        	}
        }
        else if (side == ForgeDirection.EAST.ordinal()) {
        	for(int i = 1; i < Constants.LASER_REACH; ++i) {
        		if(!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord + i, this.yCoord, this.zCoord)) {
        			Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord + i, this.yCoord, this.zCoord)];
        			if(block != null && (block.blockID == ModBlocks.basicLaser.blockID || block.blockID == ModBlocks.advancedLaser.blockID)) {
        				int meta = this.worldObj.getBlockMetadata(this.xCoord + i, this.yCoord, this.zCoord);
            			boolean hasPower = worldObj.isBlockIndirectlyGettingPowered(this.xCoord + i, this.yCoord, this.zCoord);
        				return meta == Facing.oppositeSide[side] && hasPower;
        			}
        			else if(block != null && block instanceof ILaserReciver) {
        				return ((ILaserReciver)block).isSendingSignalFromSide(this.worldObj, this.xCoord + i, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord, Facing.oppositeSide[side]);
        			}
        			break;
        		}
        	}
        }
        
    	return false;
	}
}
