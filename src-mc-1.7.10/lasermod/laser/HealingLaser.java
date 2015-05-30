package lasermod.laser;

import java.util.List;

import lasermod.api.ILaser;
import lasermod.util.BlockActionPos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author webmilio
 */
public class HealingLaser implements ILaser {

	@Override
	public void performActionOnEntitiesBoth(List<Entity> entities, int direction) {
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
	public void performActionOnEntitiesClient(List<Entity> entities, int direction) {
		
	}

	@Override
	public void performActionOnEntitiesServer(List<Entity> entities, int direction) {
		
	}

	@Override
	public boolean shouldRenderLaser(EntityPlayer player, int direction) {
		return true;
	}
	
	@Override
	public void actionOnBlock(BlockActionPos action) {
		
	}
}
