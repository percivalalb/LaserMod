package lasermod.laser;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import lasermod.api.ILaser;

/**
 * @author webmilio
 */
public class GrowthLaser implements ILaser {

	@Override
	public void performActionOnEntitiesBoth(List<Entity> entities, int direction) {
		for (Entity entity : entities)
		{
			
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
		// TODO Auto-generated method stub
		return true;
	}
	
}
