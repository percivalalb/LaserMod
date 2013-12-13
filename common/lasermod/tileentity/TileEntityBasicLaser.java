package lasermod.tileentity;

import lasermod.core.helper.LogHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

/**
 * @author ProPercivalalb
 */
public class TileEntityBasicLaser extends TileEntity {

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
		
        if (meta == 0)
        {
        	
        }

        if (meta == 1)
        {
        	
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
        boundingBox.setBounds(boundingBox.minX + extraMinX, boundingBox.minY + extraMinY, boundingBox.minZ + extraMinZ, boundingBox.maxX + extraMaxX, boundingBox.maxY + extraMaxY, boundingBox.maxZ + extraMaxZ);
        
        return boundingBox;
	}
	
}
