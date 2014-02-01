package lasermod.util;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import lasermod.api.ILaserProvider;
import lasermod.api.ILaserReciver;
import lasermod.api.LaserWhitelist;

/**
 * @author ProPercivalalb
 */
public class LaserUtil {

	public static int LASER_REACH = 64; //Distance in blocks
	
	/**
	 * 
	 * @param meta
	 * @return
	 */
	public static int getOrientation(int meta) {
		return meta & 7;
	}
	
	public ILaserReciver getFirstReciver(ILaserProvider laserProvider, int meta) {
		int orientation = getOrientation(meta);
		
		for(int distance = 0; distance <= LASER_REACH; distance++) {
			int xTemp = laserProvider.getX() + ForgeDirection.VALID_DIRECTIONS[orientation].offsetX * distance;
			int yTemp = laserProvider.getY() + ForgeDirection.VALID_DIRECTIONS[orientation].offsetY * distance;
			int zTemp = laserProvider.getZ() + ForgeDirection.VALID_DIRECTIONS[orientation].offsetZ * distance;
			
			//Check whether the coordinates are in range
			if(xTemp < -30000000 || zTemp < -30000000 || xTemp >= 30000000 || zTemp >= 30000000 || yTemp < 0 || yTemp >= 256)
				break;
			
			Block block = laserProvider.getWorld().func_147439_a(xTemp, yTemp, zTemp);
			int blockMeta = laserProvider.getWorld().getBlockMetadata(xTemp, yTemp, zTemp);
			TileEntity tileEntity = laserProvider.getWorld().func_147438_o(xTemp, yTemp, zTemp);
			
			//Can't pass through the next block
			if(!LaserWhitelist.canLaserPassThrought(block, blockMeta))
				break;
			
			//The next block is instance of ILaserReciver so return it
			if(tileEntity instanceof ILaserReciver)
				return (ILaserReciver)tileEntity;
		}
		
        return null;
	}
}
