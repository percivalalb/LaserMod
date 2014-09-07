package lasermod.util;

import java.util.List;

import lasermod.api.ILaser;
import lasermod.api.ILaserProvider;
import lasermod.api.ILaserReciver;
import lasermod.api.LaserWhitelist;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author ProPercivalalb
 */
public class LaserUtil {

	public static int LASER_REACH = 64; //Distance in blocks
	public static int TICK_RATE = 2;
	public static double LASER_SIZE = 0.4D; //The distance across the entire beam
	public static float[][] LASER_COLOUR_TABLE = new float[][] {{1.0F, 1.0F, 1.0F}, {0.85F, 0.5F, 0.2F}, {0.7F, 0.3F, 0.85F}, {0.4F, 0.6F, 0.85F}, {0.9F, 0.9F, 0.2F}, {0.5F, 0.8F, 0.1F}, {0.95F, 0.5F, 0.65F}, {0.3F, 0.3F, 0.3F}, {0.6F, 0.6F, 0.6F}, {0.3F, 0.5F, 0.6F}, {0.5F, 0.25F, 0.7F}, {0.2F, 0.3F, 0.7F}, {0.4F, 0.3F, 0.2F}, {0.0F, 1.0F, 0.0F}, {1.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}};
	
	/**
	 * 
	 * @param meta
	 * @return
	 */
	public static int getOrientation(int meta) {
		return meta & 7;
	}
	
	public static ILaserReciver getFirstReciver(ILaserProvider laserProvider, int meta) {
		int orientation = getOrientation(meta);
		
		for(int distance = 1; distance <= LASER_REACH; distance++) {
			int xTemp = laserProvider.getX() + ForgeDirection.VALID_DIRECTIONS[orientation].offsetX * distance;
			int yTemp = laserProvider.getY() + ForgeDirection.VALID_DIRECTIONS[orientation].offsetY * distance;
			int zTemp = laserProvider.getZ() + ForgeDirection.VALID_DIRECTIONS[orientation].offsetZ * distance;
			
			//Check whether the coordinates are in range
			if(xTemp < -30000000 || zTemp < -30000000 || xTemp >= 30000000 || zTemp >= 30000000 || yTemp < 0 || yTemp >= 256)
				break;
			
			Block block = laserProvider.getWorld().getBlock(xTemp, yTemp, zTemp);
			int blockMeta = laserProvider.getWorld().getBlockMetadata(xTemp, yTemp, zTemp);
			TileEntity tileEntity = laserProvider.getWorld().getTileEntity(xTemp, yTemp, zTemp);
			
			//The next block is instance of ILaserReciver so return it
			if(tileEntity instanceof ILaserReciver)
				return (ILaserReciver)tileEntity;
			
			//Can't pass through the next block
			if(!LaserWhitelist.canLaserPassThrought(block, blockMeta))
				break;
		}
		
        return null;
	}
	
	public static boolean isValidSourceOfPowerOnSide(ILaserReciver laserReciver, int side) {
		for(int distance = 1; distance <= LASER_REACH; distance++) {
			int xTemp = laserReciver.getX() + ForgeDirection.VALID_DIRECTIONS[side].offsetX * distance;
			int yTemp = laserReciver.getY() + ForgeDirection.VALID_DIRECTIONS[side].offsetY * distance;
			int zTemp = laserReciver.getZ() + ForgeDirection.VALID_DIRECTIONS[side].offsetZ * distance;
			
			//Check whether the coordinates are in range
			if(xTemp < -30000000 || zTemp < -30000000 || xTemp >= 30000000 || zTemp >= 30000000 || yTemp < 0 || yTemp >= 256)
				break;
			
			Block block = laserReciver.getWorld().getBlock(xTemp, yTemp, zTemp);
			int blockMeta = laserReciver.getWorld().getBlockMetadata(xTemp, yTemp, zTemp);
			TileEntity tileEntity = laserReciver.getWorld().getTileEntity(xTemp, yTemp, zTemp);
			
			if(!LaserWhitelist.canLaserPassThrought(block, blockMeta))
				if(tileEntity instanceof ILaserProvider)
					return ((ILaserProvider)tileEntity).isSendingSignalFromSide(laserReciver.getWorld(), laserReciver.getX(), laserReciver.getY(), laserReciver.getZ(), Facing.oppositeSide[side]);
			else
				break;
		}
		
    	return false;
	}
	
	public static void performLaserAction(ILaserProvider laserProvider, int meta, double renderX, double renderY, double renderZ) {
		AxisAlignedBB boundingBox = LaserUtil.getLaserOutline(laserProvider, meta, renderX, renderY, renderZ);
		
		//Gets all entities within the 'laser beam' 
		List<Entity> entities = laserProvider.getWorld().getEntitiesWithinAABB(Entity.class, boundingBox);
		
		for(ILaser ilaser : laserProvider.getOutputLaser(meta).getLaserType()) {
			
			ilaser.performActionOnEntitiesBoth(entities, meta);
			
			if(laserProvider.getWorld().isRemote) 
				ilaser.performActionOnEntitiesClient(entities, meta);
			else
				ilaser.performActionOnEntitiesServer(entities, meta);
		}
	}
	

	public static AxisAlignedBB getLaserOutline(ILaserProvider laserProvider, int meta, double renderX, double renderY, double renderZ) {
		int orientation = LaserUtil.getOrientation(meta);
		double offsetMin = 0.5D - LASER_SIZE / 2;
		double offsetMax = 0.5D + LASER_SIZE / 2;
		AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(renderX + offsetMin, renderY + offsetMin, renderZ + offsetMin, renderX + offsetMax, renderY + offsetMax, renderZ + offsetMax);
		
		double[] extra = new double[ForgeDirection.VALID_DIRECTIONS.length];
		
		for(int distance = 1; distance <= LaserUtil.LASER_REACH; distance++) {
			int xTemp = laserProvider.getX() + ForgeDirection.VALID_DIRECTIONS[orientation].offsetX * distance;
			int yTemp = laserProvider.getY() + ForgeDirection.VALID_DIRECTIONS[orientation].offsetY * distance;
			int zTemp = laserProvider.getZ() + ForgeDirection.VALID_DIRECTIONS[orientation].offsetZ * distance;
			
			//Check whether the coordinates are in range
			if(xTemp < -30000000 || zTemp < -30000000 || xTemp >= 30000000 || zTemp >= 30000000 || yTemp < 0 || yTemp >= 256)
				break;
			
			Block block = laserProvider.getWorld().getBlock(xTemp, yTemp, zTemp);
			int blockMeta = laserProvider.getWorld().getBlockMetadata(xTemp, yTemp, zTemp);
			TileEntity tileEntity = laserProvider.getWorld().getTileEntity(xTemp, yTemp, zTemp);
			
			//Can't pass through the next block
			if(LaserWhitelist.canLaserPassThrought(block, blockMeta))
				extra[orientation] += 1;
			else {
				extra[orientation] += 1 - offsetMax + 0.01D;
				break;
			}
			
		}
		
        boundingBox.setBounds(boundingBox.minX - extra[4], boundingBox.minY - extra[0], boundingBox.minZ - extra[2], boundingBox.maxX + extra[5], boundingBox.maxY + extra[1], boundingBox.maxZ + extra[3]);
        
        return boundingBox;
	}
	
}
