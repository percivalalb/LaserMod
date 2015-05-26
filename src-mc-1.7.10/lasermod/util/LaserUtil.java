package lasermod.util;

import java.util.List;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import lasermod.api.ILaser;
import lasermod.api.ILaserProvider;
import lasermod.api.ILaserReceiver;
import lasermod.api.LaserWhitelist;
import lasermod.compat.forgemultipart.ForgeMultipartCompat;
import lasermod.compat.forgemultipart.SmallColourConverterPart;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author ProPercivalalb
 */
public class LaserUtil {

	public static int TICK_RATE = 10;
	public static int LASER_RATE = 2;
	public static double LASER_SIZE = 0.3D; //The distance across the entire beam
	public static float[][] LASER_COLOUR_TABLE = new float[][] {{1.0F, 1.0F, 1.0F}, {0.85F, 0.5F, 0.2F}, {0.7F, 0.3F, 0.85F}, {0.4F, 0.6F, 0.85F}, {0.9F, 0.9F, 0.2F}, {0.5F, 0.8F, 0.1F}, {0.95F, 0.5F, 0.65F}, {0.3F, 0.3F, 0.3F}, {0.6F, 0.6F, 0.6F}, {0.3F, 0.5F, 0.6F}, {0.5F, 0.25F, 0.7F}, {0.2F, 0.3F, 0.7F}, {0.4F, 0.3F, 0.2F}, {0.0F, 1.0F, 0.0F}, {1.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}};
	
	/**
	 * 
	 * @param meta
	 * @return
	 */
	public static int getOrientation(int meta) {
		return meta & 7;
	}
	
	/**
	public static ILaserReceiver getFirstReciver(ILaserProvider laserProvider, int meta) {
		int orientation = getOrientation(meta);
		
		for(int distance = 1; distance <= LASER_REACH; distance++) {
			int xTemp = laserProvider.getX() + ForgeDirection.VALID_DIRECTIONS[orientation].offsetX * distance;
			int yTemp = laserProvider.getY() + ForgeDirection.VALID_DIRECTIONS[orientation].offsetY * distance;
			int zTemp = laserProvider.getZ() + ForgeDirection.VALID_DIRECTIONS[orientation].offsetZ * distance;
			
			//Check whether the coordinates are in range
			if(xTemp < -30000000 || zTemp < -30000000 || xTemp >= 30000000 || zTemp >= 30000000 || yTemp < 0 || yTemp >= 256)
				break;
			
			TileEntity tileEntity = laserProvider.getWorld().getTileEntity(xTemp, yTemp, zTemp);
			
			//The next block is instance of ILaserReciver so return it
			if(tileEntity instanceof ILaserReceiver)
				return (ILaserReceiver)tileEntity;
			
			//Can't pass through the next block
			if(!LaserWhitelist.canLaserPassThrought(laserProvider.getWorld(), xTemp, yTemp, zTemp))
				break;
		}
		
        return null;
	}**/
	
	public static BlockActionPos getFirstBlock(ILaserProvider laserProvider, int meta) {
		int orientation = getOrientation(meta);
		
		for(int distance = 1; distance <= laserProvider.getDistance(); distance++) {
			int xTemp = laserProvider.getX() + ForgeDirection.VALID_DIRECTIONS[orientation].offsetX * distance;
			int yTemp = laserProvider.getY() + ForgeDirection.VALID_DIRECTIONS[orientation].offsetY * distance;
			int zTemp = laserProvider.getZ() + ForgeDirection.VALID_DIRECTIONS[orientation].offsetZ * distance;
			
			//Check whether the coordinates are in range
			if(xTemp < -30000000 || zTemp < -30000000 || xTemp >= 30000000 || zTemp >= 30000000 || yTemp < 0 || yTemp >= 256)
				break;
			
			BlockActionPos blockActionPos = new BlockActionPos(laserProvider.getWorld(), xTemp, yTemp, zTemp);
			
			
			//Can't pass through the next block
			if(blockActionPos.isLaserReciver(orientation) || !LaserWhitelist.canLaserPassThrought(laserProvider.getWorld(), xTemp, yTemp, zTemp, ForgeDirection.VALID_DIRECTIONS[orientation].getOpposite()))
				return blockActionPos;
		}
		
        return null;
	}
	
	/**
	public static ILaserReceiver lightUp(ILaserProvider laserProvider, int meta) {
		int orientation = getOrientation(meta);
		
		for(int distance = 1; distance <= LASER_REACH; distance++) {
			int xTemp = laserProvider.getX() + ForgeDirection.VALID_DIRECTIONS[orientation].offsetX * distance;
			int yTemp = laserProvider.getY() + ForgeDirection.VALID_DIRECTIONS[orientation].offsetY * distance;
			int zTemp = laserProvider.getZ() + ForgeDirection.VALID_DIRECTIONS[orientation].offsetZ * distance;
			
			//Check whether the coordinates are in range
			if(xTemp < -30000000 || zTemp < -30000000 || xTemp >= 30000000 || zTemp >= 30000000 || yTemp < 0 || yTemp >= 256)
				break;

			
			if(LaserWhitelist.canLaserPassThrought(laserProvider.getWorld(), xTemp, yTemp, zTemp)) {
				laserProvider.getWorld().setLightValue(EnumSkyBlock.Block, xTemp, yTemp, zTemp, 15);
				//laserProvider.getWorld().markBlockForUpdate(xTemp, yTemp, zTemp);
			}
			else {
				//extra[orientation] += 1;
				break;
			}
		}
		
        return null;
	}**/
	
	public static boolean isValidSourceOfPowerOnSide(ILaserReceiver laserReciver, int side) {
		for(int distance = 1; distance <= 64; distance++) {
			int xTemp = laserReciver.getX() + ForgeDirection.VALID_DIRECTIONS[side].offsetX * distance;
			int yTemp = laserReciver.getY() + ForgeDirection.VALID_DIRECTIONS[side].offsetY * distance;
			int zTemp = laserReciver.getZ() + ForgeDirection.VALID_DIRECTIONS[side].offsetZ * distance;
			
			//Check whether the coordinates are in range
			if(xTemp < -30000000 || zTemp < -30000000 || xTemp >= 30000000 || zTemp >= 30000000 || yTemp < 0 || yTemp >= 256)
				break;
			
			BlockActionPos blockActionPos = new BlockActionPos(laserReciver.getWorld(), xTemp, yTemp, zTemp);
			//FMLLog.info(blockActionPos.toString());
			
			//Can't pass through the next block
			if(blockActionPos.isLaserProvider(side)) {
				return blockActionPos.getLaserProvider().isSendingSignalFromSide(laserReciver.getWorld(), laserReciver.getX(), laserReciver.getY(), laserReciver.getZ(), Facing.oppositeSide[side]);
				
				
			}
			else if(!LaserWhitelist.canLaserPassThrought(laserReciver.getWorld(), xTemp, yTemp, zTemp, ForgeDirection.VALID_DIRECTIONS[side]))
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
		
		for(int distance = 1; distance <= laserProvider.getDistance(); distance++) {
			int xTemp = laserProvider.getX() + ForgeDirection.VALID_DIRECTIONS[orientation].offsetX * distance;
			int yTemp = laserProvider.getY() + ForgeDirection.VALID_DIRECTIONS[orientation].offsetY * distance;
			int zTemp = laserProvider.getZ() + ForgeDirection.VALID_DIRECTIONS[orientation].offsetZ * distance;
			
			
			//Check whether the coordinates are in range
			if(xTemp < -30000000 || zTemp < -30000000 || xTemp >= 30000000 || zTemp >= 30000000 || yTemp < 0 || yTemp >= 256)
				break;
			
			BlockActionPos blockActionPos = new BlockActionPos(laserProvider.getWorld(), xTemp, yTemp, zTemp);
			
			
			//Can't pass through the next block
			if((!blockActionPos.isLaserReciver(orientation) && LaserWhitelist.canLaserPassThrought(laserProvider.getWorld(), xTemp, yTemp, zTemp, ForgeDirection.VALID_DIRECTIONS[orientation].getOpposite())))
				extra[orientation] += 1;
			else {
				extra[orientation] += 1 - offsetMax - 0.01;
				break;
			}
			/**
			//Check whether the coordinates are in range
			if(xTemp < -30000000 || zTemp < -30000000 || xTemp >= 30000000 || zTemp >= 30000000 || yTemp < 0 || yTemp >= 256)
				break;
			
			Block block = laserProvider.getWorld().getBlock(xTemp, yTemp, zTemp);
			int blockMeta = laserProvider.getWorld().getBlockMetadata(xTemp, yTemp, zTemp);
			TileEntity tileEntity = laserProvider.getWorld().getTileEntity(xTemp, yTemp, zTemp);
			
			//Can't pass through the next block
			if(LaserWhitelist.canLaserPassThrought(laserProvider.getWorld(), xTemp, yTemp, zTemp, ForgeDirection.VALID_DIRECTIONS[orientation].getOpposite()))
				extra[orientation] += 1;
			else {
				extra[orientation] += 1 - offsetMax;
				extra[ForgeDirection.OPPOSITES[orientation]] += offsetMax;
				break;
			}**/
			
		}
		extra[ForgeDirection.OPPOSITES[orientation]] = offsetMin - 0.01;
		
        boundingBox.setBounds(boundingBox.minX - extra[4], boundingBox.minY - extra[0], boundingBox.minZ - extra[2], boundingBox.maxX + extra[5], boundingBox.maxY + extra[1], boundingBox.maxZ + extra[3]);
        
        return boundingBox;
	}
	
}
