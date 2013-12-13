package lasermod.tileentity;

import java.util.ArrayList;

import lasermod.api.LaserInGame;
import lasermod.api.LaserWhitelist;
import lasermod.core.helper.LogHelper;
import lasermod.packet.PacketReflectorUpdate;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;

/**
 * @author ProPercivalalb
 */
public class TileEntityReflector extends TileEntity {

	public boolean[] openSides = new boolean[] {true, true, true, true, true, true};
	public ArrayList<LaserInGame> lasers = new ArrayList<LaserInGame>();
	
	public boolean isSideOpen(ForgeDirection direction) {
		return this.isSideOpen(direction.ordinal());
	}
	
	public boolean isSideOpen(int side) {
		return this.openSides[side];
	}
	
	public boolean addLaser(LaserInGame laserInGame) {
		if(lasers.size() == 0) {
			lasers.add(laserInGame);
			return true;
		}
		
		for(int i = 0; i < lasers.size(); ++i) {
			LaserInGame old = lasers.get(i);
			
			if(old.getLaserType() == laserInGame.getLaserType()) {
				if(laserInGame.getStrength() > old.getStrength()) {
					lasers.remove(i);
					lasers.add(laserInGame);
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean containsInputSide(int side) {
		for(int i = 0; i < lasers.size(); ++i) {
			LaserInGame old = lasers.get(i);
			if(old.getSide() == side) {
				return true;
			}
		}
		return false;
	}
	
	public boolean removeAllLasersFromSide(int side) {
		boolean flag = false;
		for(int i = 0; i < lasers.size(); ++i) {
			LaserInGame old = lasers.get(i);
			if(old.getSide() == side) {
				lasers.remove(i);
				flag = true;
			}
		}
		return flag;
	}
	
	public AxisAlignedBB getFromLaserBox(double x, double y, double z, int side) {
		double laserSize = 0.4D;
		AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(x + 0.5D - laserSize / 2, y + 0.5D - laserSize / 2, z + 0.5D - laserSize / 2, x + 0.5D + laserSize / 2, y + 0.5D + laserSize / 2, z + 0.5D + laserSize / 2);
		
		double extraMinX = 0.0D;
		double extraMinY = 0.0D;
		double extraMinZ = 0.0D;
		
		double extraMaxX = 0.0D;
		double extraMaxY = 0.0D;
		double extraMaxZ = 0.0D;
		
        if (side == ForgeDirection.DOWN.ordinal()) {
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
        else if (side == ForgeDirection.UP.ordinal()) {
        	for(int i = this.yCoord + 1; i < 256; ++i) {
        		if(LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, i, this.zCoord)) {
        			extraMaxY++;
        		}
        		else {
        			extraMaxY += 1.0D - laserSize;
        			break;
        		}
        	}
        }
        else if (side == ForgeDirection.NORTH.ordinal()) {
        	for(int i = 1; i < 256; ++i) {
        		if(LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, this.yCoord, this.zCoord - i)) {
        			extraMinZ++;
        		}
        		else {
        			extraMinZ += 1.0D - laserSize;
        			break;
        		}
        	}
        }
        else if (side == ForgeDirection.SOUTH.ordinal()) {
        	for(int i = 1; i < 256; ++i) {
        		if(LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, this.yCoord, this.zCoord + i)) {
        			extraMaxZ++;
        		}
        		else {
        			extraMaxZ += 1.0D - laserSize;
        			break;
        		}
        	}
        }
        else if (side == ForgeDirection.WEST.ordinal()) {
        	for(int i = 1; i < 256; ++i) {
        		if(LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord - i, this.yCoord, this.zCoord)) {
        			extraMinX++;
        		}
        		else {
        			extraMinX += 1.0D - laserSize;
        			break;
        		}
        	}
        }
        else if (side == ForgeDirection.EAST.ordinal()) {
        	for(int i = 1; i < 256; ++i) {
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
	
	@Override
	public void updateEntity() {
		LogHelper.logInfo("" + lasers.size());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		for(int i = 0; i < openSides.length; ++i)
			openSides[i] = tag.getBoolean("openSide" + i);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		
		for(int i = 0; i < openSides.length; ++i)
			tag.setBoolean("openSide" + i, openSides[i]);
	}

	@Override
	public Packet getDescriptionPacket() {
	    return new PacketReflectorUpdate(this.xCoord, this.yCoord, this.zCoord, this).buildPacket();
	}

	
	@Override
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
    	return INFINITE_EXTENT_AABB;
    }
}
