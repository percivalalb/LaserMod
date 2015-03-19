package lasermod.laser;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import lasermod.api.ILaser;

public class GrowthLaser implements ILaser {

	@Override
	public void performActionOnEntitiesBoth(List<Entity> entities, int direction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performActionOnEntitiesClient(List<Entity> entities,
			int direction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performActionOnEntitiesServer(List<Entity> entities,
			int direction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean shouldRenderLaser(EntityPlayer player, int direction) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
