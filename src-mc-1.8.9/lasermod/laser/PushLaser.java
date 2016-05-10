package lasermod.laser;

import java.util.List;

import lasermod.api.ILaser;
import lasermod.util.BlockActionPos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;

/**
 * @author ProPercivalalb
 */
public class PushLaser implements ILaser {

	public static final float SPEED_MULTIPLYER = 1.0F;
	
	@Override
	public void performActionOnEntitiesServer(List<Entity> entities, EnumFacing dir) {
		for(Entity entity : entities) {
			
		}
	}
	
	@Override
	public void performActionOnEntitiesClient(List<Entity> entities, EnumFacing dir) {
		for(Entity entity : entities) {
			
		}
	}
	
	@Override
	public void performActionOnEntitiesBoth(List<Entity> entities, EnumFacing dir) {
		for(Entity entity : entities) {
			double verticalSpeed = 0.180000000000000003D;
            double maxSpeed = 0.29999999999999999D;
            verticalSpeed *= SPEED_MULTIPLYER;

            if(entity instanceof EntityItem)
                maxSpeed *= 2.34D;

            if (entity instanceof EntityMinecart)
                verticalSpeed *= 0.5D;

            if ((entity instanceof EntityFallingBlock) && dir == EnumFacing.UP)
                verticalSpeed = 0.0D;

            if (dir == EnumFacing.DOWN && entity.motionY > -maxSpeed)
                entity.motionY += -verticalSpeed;

            if (dir == EnumFacing.UP && entity.motionY < maxSpeed * 0.5D)
                entity.motionY += verticalSpeed;

            if (dir == EnumFacing.NORTH && entity.motionZ > -maxSpeed)
                entity.motionZ += -verticalSpeed;

            if (dir == EnumFacing.SOUTH && entity.motionZ < maxSpeed)
                entity.motionZ += verticalSpeed;

            if (dir == EnumFacing.WEST && entity.motionX > -maxSpeed)
                entity.motionX += -verticalSpeed;

            if (dir == EnumFacing.EAST && entity.motionX < maxSpeed)
                entity.motionX += verticalSpeed;
		}
	}
	
	@Override
	public boolean shouldRenderLaser(EntityPlayer player, EnumFacing dir) {
		return true;
	}
	
	@Override
	public void actionOnBlock(BlockActionPos action) {
		
	}
}
