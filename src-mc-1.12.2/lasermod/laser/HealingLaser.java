package lasermod.laser;

import java.util.List;

import lasermod.api.LaserType;
import lasermod.util.BlockActionPos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;

/**
 * @author webmilio
 */
public class HealingLaser extends LaserType {

	@Override
	public void performActionOnEntitiesBoth(List<Entity> entities, EnumFacing dir) {
		for (Entity entity : entities)
		{
			float healRate = 0.05F;
			if (entity instanceof EntityLiving)
			{
				((EntityLiving) entity).heal(healRate);
			}
			else if(entity instanceof EntityPlayer)
			{
				((EntityPlayer) entity).heal(healRate);
			}
		}
	}

	@Override
	public void performActionOnEntitiesClient(List<Entity> entities, EnumFacing dir) {
		
	}

	@Override
	public void performActionOnEntitiesServer(List<Entity> entities, EnumFacing dir) {
		
	}

	@Override
	public boolean shouldRenderLaser(EntityPlayer player, EnumFacing dir) {
		return true;
	}
	
	@Override
	public void actionOnBlock(BlockActionPos action) {
		
	}
}
