package lasermod.util;

import java.util.List;

import lasermod.ModBlocks;
import lasermod.api.ILaser;
import lasermod.api.ILaserProvider;
import lasermod.api.ILaserReceiver;
import lasermod.api.LaserInGame;
import lasermod.api.LaserModAPI;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;

/**
 * @author ProPercivalalb
 */
public class LaserUtil {

	public static int TICK_RATE = 8;
	public static int LASER_RATE = 2;
	public static double LASER_SIZE = 0.3D; //The distance across the entire beam
	public static float[][] LASER_COLOUR_TABLE = new float[][] {{1.0F, 1.0F, 1.0F}, {0.85F, 0.5F, 0.2F}, {0.7F, 0.3F, 0.85F}, {0.4F, 0.6F, 0.85F}, {0.9F, 0.9F, 0.2F}, {0.5F, 0.8F, 0.1F}, {0.95F, 0.5F, 0.65F}, {0.3F, 0.3F, 0.3F}, {0.6F, 0.6F, 0.6F}, {0.3F, 0.5F, 0.6F}, {0.5F, 0.25F, 0.7F}, {0.2F, 0.3F, 0.7F}, {0.4F, 0.3F, 0.2F}, {0.0F, 1.0F, 0.0F}, {1.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}};
	
	public static BlockActionPos getFirstBlock(ILaserProvider laserProvider, EnumFacing dir) {
		for(int distance = laserProvider.isForgeMultipart() ? 0 : 1; distance < laserProvider.getDistance(dir); distance++) {
			BlockPos tempPos = laserProvider.getPos().add(dir.getFrontOffsetX() * distance, dir.getFrontOffsetY() * distance, dir.getFrontOffsetZ() * distance);
			
			
			//Check whether the coordinates are in range
			if(tempPos.getX() < -30000000 || tempPos.getZ() < -30000000 || tempPos.getX() >= 30000000 || tempPos.getZ() >= 30000000 || tempPos.getY() < 0 || tempPos.getY() >= 256)
				break;
			
			BlockActionPos blockActionPos = new BlockActionPos(laserProvider.getWorld(), tempPos);
			
			
			//Can't pass through the next block
			if(blockActionPos.isLaserReceiver(dir) && !tempPos.equals(laserProvider.getPos())) {
				return blockActionPos;
			}
			else if(blockActionPos.block == ModBlocks.COLOUR_CONVERTER_SMALL && blockActionPos.meta == dir.getOpposite().ordinal()) {
				break;
			}
			else if(LaserModAPI.LASER_BLACKLIST.contains(blockActionPos.block, blockActionPos.meta)) {
				return blockActionPos;
			}	
			else if(blockActionPos.state.getBlockFaceShape(laserProvider.getWorld(), tempPos, dir.getOpposite()) == BlockFaceShape.UNDEFINED) {
				return blockActionPos;
			}
			else if(blockActionPos.block.isSideSolid(laserProvider.getWorld(), tempPos, dir)) {
				return blockActionPos;
			}
			else if(blockActionPos.block.isAir(laserProvider.getWorld(), tempPos) || (!blockActionPos.block.isSideSolid(laserProvider.getWorld(), tempPos, dir) && !blockActionPos.block.isSideSolid(laserProvider.getWorld(), tempPos, dir.getOpposite())) || LaserModAPI.LASER_WHITELIST.contains(blockActionPos.block, blockActionPos.meta)) {
				
			}
			else {
				return blockActionPos;
			}
		}
		
        return null;
	}
	
	/**
	public static ILaserReceiver lightUp(ILaserProvider laserProvider, int meta) {
		int orientation = getOrientation(meta);
		
		for(int distance = 1; distance <= LASER_REACH; distance++) {
			int xTemp = laserProvider.getX() + EnumFacing.VALID_DIRECTIONS[orientation].offsetX * distance;
			int yTemp = laserProvider.getY() + EnumFacing.VALID_DIRECTIONS[orientation].offsetY * distance;
			int zTemp = laserProvider.getZ() + EnumFacing.VALID_DIRECTIONS[orientation].offsetZ * distance;
			
			//Check whether the coordinates are in range
			if(xTemp < -30000000 || zTemp < -30000000 || xTemp >= 30000000 || zTemp >= 30000000 || yTemp < 0 || yTemp >= 256)
				break;

			
			if(LaserWhitelist.canLaserPassThrought(laserProvider.getWorld(), tempPos)) {
				laserProvider.getWorld().setLightValue(EnumSkyBlock.Block, tempPos, 15);
				//laserProvider.getWorld().markBlockForUpdate(tempPos);
			}
			else {
				//extra[orientation] += 1;
				break;
			}
		}
		
        return null;
	}**/
	
	public static boolean isValidSourceOfPowerOnSide(ILaserReceiver laserReciver, EnumFacing dir) {
		for(int distance = 1; distance <= 64; distance++) {
			BlockPos tempPos = laserReciver.getPos().add(dir.getFrontOffsetX() * distance, dir.getFrontOffsetY() * distance, dir.getFrontOffsetZ() * distance);
			
			//Check whether the coordinates are in range
			if(tempPos.getX() < -30000000 || tempPos.getZ() < -30000000 || tempPos.getX() >= 30000000 || tempPos.getZ() >= 30000000 || tempPos.getY() < 0 || tempPos.getY() >= 256)
				break;
			
			BlockActionPos blockActionPos = new BlockActionPos(laserReciver.getWorld(), tempPos);
			
			//Can't pass through the next block
			if(blockActionPos.isLaserProvider(dir)) {
				ILaserProvider provider = blockActionPos.getLaserProvider(dir);
				int distanceX = Math.abs(dir.getFrontOffsetX() * (laserReciver.getPos().getX() - provider.getPos().getX()));
				int distanceY = Math.abs(dir.getFrontOffsetY() * (laserReciver.getPos().getY() - provider.getPos().getY()));
				int distanceZ = Math.abs(dir.getFrontOffsetZ() * (laserReciver.getPos().getZ() - provider.getPos().getZ()));
				
				int distanceApart = distanceX + distanceY + distanceZ;
				return distanceApart <= provider.getDistance(dir) && provider.isSendingSignalFromSide(laserReciver.getWorld(), laserReciver.getPos(), dir.getOpposite());
			}
			else if(blockActionPos.block == ModBlocks.COLOUR_CONVERTER_SMALL && blockActionPos.meta == dir.ordinal()) {
				break;
			}
			else if(LaserModAPI.LASER_BLACKLIST.contains(blockActionPos.block, blockActionPos.meta)) {
				break;
			}	
			else if(blockActionPos.block.isSideSolid(laserReciver.getWorld(), tempPos, dir.getOpposite())) {
				break;
			}
			else if(blockActionPos.block.isSideSolid(laserReciver.getWorld(), tempPos, dir)) {
				break;
			}
			else if(blockActionPos.block.isAir(laserReciver.getWorld(), tempPos) || (!blockActionPos.block.isSideSolid(laserReciver.getWorld(), tempPos, dir) && !blockActionPos.block.isSideSolid(laserReciver.getWorld(), tempPos, dir.getOpposite())) || LaserModAPI.LASER_WHITELIST.contains(blockActionPos.block, blockActionPos.meta)) {
			}
			else {
				break;
			}
		}
    	return false;
	}
	
	public static void performLaserAction(ILaserProvider laserProvider, EnumFacing dir, BlockPos pos) {
		LaserInGame laserInGame = laserProvider.getOutputLaser(dir);
		if(laserInGame != null) {
			AxisAlignedBB boundingBox = LaserUtil.getLaserOutline(laserProvider, dir, pos.getX(), pos.getY(), pos.getZ());
			List<Entity> entities = laserProvider.getWorld().getEntitiesWithinAABB(Entity.class, boundingBox);
			for(ILaser ilaser : laserInGame.getLaserType()) {
				ilaser.performActionOnEntitiesBoth(entities, dir);
				
				if(laserProvider.getWorld().isRemote) 
					ilaser.performActionOnEntitiesClient(entities, dir);
				else
					ilaser.performActionOnEntitiesServer(entities, dir);
			}
		}
	}
	
	public static AxisAlignedBB getLaserOutline(ILaserProvider laserProvider, EnumFacing dir, double renderX, double renderY, double renderZ) {
		double offsetMin = 0.5D - LASER_SIZE / 2;
		double offsetMax = 0.5D + LASER_SIZE / 2;
		AxisAlignedBB boundingBox = new AxisAlignedBB(renderX + offsetMin, renderY + offsetMin, renderZ + offsetMin, renderX + offsetMax, renderY + offsetMax, renderZ + offsetMax);
		
		double[] extra = new double[EnumFacing.VALUES.length];
		
		for(int distance = laserProvider.isForgeMultipart() ? 0 : 1; distance < laserProvider.getDistance(dir); distance++) {
			BlockPos tempPos = laserProvider.getPos().add(dir.getFrontOffsetX() * distance, dir.getFrontOffsetY() * distance, dir.getFrontOffsetZ() * distance);
			
			
			//Check whether the coordinates are in range
			if(tempPos.getX() < -30000000 || tempPos.getZ() < -30000000 || tempPos.getX() >= 30000000 || tempPos.getZ() >= 30000000 || tempPos.getY() < 0 || tempPos.getY() >= 256)
				break;
			
			BlockActionPos blockActionPos = new BlockActionPos(laserProvider.getWorld(), tempPos);
			
			if(blockActionPos.isLaserReceiver(dir) && !tempPos.equals(laserProvider.getPos())) {
				extra[dir.ordinal()] += (distance == 0 ? 0 : 1) - offsetMax - 0.01;
				break;
			}
			else if(LaserModAPI.LASER_BLACKLIST.contains(blockActionPos.block, blockActionPos.meta)) {
				extra[dir.ordinal()] += (distance == 0 ? 0 : 1) - offsetMax - 0.01;
				break;
			}	
			else if(blockActionPos.block == ModBlocks.COLOUR_CONVERTER_SMALL && blockActionPos.meta == dir.getOpposite().ordinal()) {
				extra[dir.ordinal()] += (distance == 0 ? 0 : 1) + offsetMin - 0.01;
				break;
			}
			else if(blockActionPos.block.isSideSolid(laserProvider.getWorld(), tempPos, dir.getOpposite())) {
				extra[dir.ordinal()] += offsetMin - 0.01;
				break;
			}
			else if(blockActionPos.block.isSideSolid(laserProvider.getWorld(), tempPos, dir)) {
				extra[dir.ordinal()] += (distance == 0 ? 0 : 1) + offsetMin - 0.01;
				break;
			}
			else if(blockActionPos.block.isAir(laserProvider.getWorld(), tempPos) || (!blockActionPos.block.isSideSolid(laserProvider.getWorld(), tempPos, dir) && !blockActionPos.block.isSideSolid(laserProvider.getWorld(), tempPos, dir.getOpposite())) || LaserModAPI.LASER_WHITELIST.contains(blockActionPos.block, blockActionPos.meta)) {
				extra[dir.ordinal()] += (distance == 0 ? 0 : 1);
			}
			else {
				extra[dir.ordinal()] += (distance == 0 ? 0 : 1) - offsetMax - 0.01;
				break;
			}
			
		}
		extra[dir.getOpposite().ordinal()] = offsetMin - 0.01;
		
        return new AxisAlignedBB(boundingBox.minX - extra[4], boundingBox.minY - extra[0], boundingBox.minZ - extra[2], boundingBox.maxX + extra[5], boundingBox.maxY + extra[1], boundingBox.maxZ + extra[3]);
	}
	
}
