package lasermod.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lasermod.api.LaserWhitelist;
import lasermod.core.helper.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

/**
 * @author ProPercivalalb
 */
public class TileEntityBasicLaser extends TileEntity {

	public AxisAlignedBB last = AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0);
	
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
		
        if (meta == 0) {
        	for(int i = this.yCoord - 1; i > 0; --i) {
        		if(LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, i, this.zCoord)) {
        			extraMinY++;
        		}
        	}
        }

        if (meta == 1)
        {
        	for(int i = this.yCoord + 1; i < 256; ++i) {
        		if(LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, i, this.zCoord)) {
        			extraMaxY++;
        		}
        	}
            //return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, (double)xCoord + 1.0D, (double)yCoord + 1.0D + d, (double)zCoord + 1.0D);
        }

        if (meta == 2)
        {
        	
            //return AxisAlignedBB.getBoundingBox(xCoord, yCoord, (double)zCoord - d, (double)xCoord + 1.0D, (double)yCoord + 1.0D, (double)zCoord + 1.0D);
        }

        if (meta == 3)
        {
            //return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, (double)xCoord + 1.0D, (double)yCoord + 1.0D, (double)zCoord + 1.0D + d);
        }

        if (meta == 4)
        {
            //return AxisAlignedBB.getBoundingBox((double)xCoord - d, yCoord, zCoord, (double)xCoord + 1.0D, (double)yCoord + 1.0D, (double)zCoord + 1.0D);
        }

        if (meta == 5)
        {
            //return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, (double)xCoord + 1.0D + d, (double)yCoord + 1.0D, (double)zCoord + 1.0D);
        }
        else
        {
            //return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, (double)xCoord + 1.0D, (double)yCoord + 1.0D, (double)zCoord + 1.0D);
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
