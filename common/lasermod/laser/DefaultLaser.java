package lasermod.laser;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import lasermod.api.ILaser;

/**
 * @author ProPercivalalb
 */
public class DefaultLaser implements ILaser {

	@Override
	public void performActionOnEntitiesServer(List<Entity> entities, int direction) {
		for(Entity entity : entities) {
			
		}
	}
	
	@Override
	public void performActionOnEntitiesClient(List<Entity> entities, int direction) {
		for(Entity entity : entities) {
			
		}
	}
	
	@Override
	public void performActionOnEntitiesBoth(List<Entity> entities, int direction) {
		for(Entity entity : entities) {
			
		}
	}

	@Override
	public boolean shouldRenderLaser(EntityPlayer player, int direction) {
		return true;
	}

}
