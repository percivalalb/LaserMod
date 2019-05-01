package lasermod.laser;

import java.util.List;

import lasermod.api.LaserType;
import lasermod.util.BlockActionPos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;

/**
 * @author ProPercivalalb
 */
public class IceLaser extends LaserType {
	
	@Override
	public void performActionOnEntitiesServer(List<Entity> entities, EnumFacing dir) {
		for(Entity entity : entities) {
			
		}
	}
	
	@Override
	public void performActionOnEntitiesClient(List<Entity> entities, EnumFacing dir) {
	}
	
	@Override
	public void performActionOnEntitiesBoth(List<Entity> entities, EnumFacing dir) {
		for(Entity entity : entities) {
			double verticalSpeed = 0.120000000000000003D;
            double maxSpeed = 0.19999999999999999D;     
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
