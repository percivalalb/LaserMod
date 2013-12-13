package lasermod.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

/**
 * @author ProPercivalalb
 */
public class TileEntityBasicLaser extends TileEntity {

	public AxisAlignedBB getLaserBox() {
		int i = getBlockMetadata();
		double laserSize = 0.4D;
		AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(this.xCoord + 0.5D - laserSize / 2, this.yCoord + 0.5D - laserSize / 2, this.zCoord + 0.5D - laserSize / 2, this.xCoord + 0.5D + laserSize / 2, this.yCoord + 0.5D + laserSize / 2, this.zCoord + 0.5D + laserSize / 2);

        if (i == 0)
        {
        	boundingBox.expand(2.0D, 2.0D, 2.0D);
        }

        if (i == 1)
        {
            //return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, (double)xCoord + 1.0D, (double)yCoord + 1.0D + d, (double)zCoord + 1.0D);
        }

        if (i == 2)
        {
            //return AxisAlignedBB.getBoundingBox(xCoord, yCoord, (double)zCoord - d, (double)xCoord + 1.0D, (double)yCoord + 1.0D, (double)zCoord + 1.0D);
        }

        if (i == 3)
        {
            //return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, (double)xCoord + 1.0D, (double)yCoord + 1.0D, (double)zCoord + 1.0D + d);
        }

        if (i == 4)
        {
            //return AxisAlignedBB.getBoundingBox((double)xCoord - d, yCoord, zCoord, (double)xCoord + 1.0D, (double)yCoord + 1.0D, (double)zCoord + 1.0D);
        }

        if (i == 5)
        {
            //return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, (double)xCoord + 1.0D + d, (double)yCoord + 1.0D, (double)zCoord + 1.0D);
        }
        else
        {
            //return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, (double)xCoord + 1.0D, (double)yCoord + 1.0D, (double)zCoord + 1.0D);
        }
        return boundingBox;
	}
	
}
