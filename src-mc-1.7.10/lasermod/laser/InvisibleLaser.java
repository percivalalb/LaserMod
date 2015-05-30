package lasermod.laser;

import java.util.List;

import lasermod.api.ILaser;
import lasermod.util.BlockActionPos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author ProPercivalalb
 */
public class InvisibleLaser implements ILaser {

	@Override
	public void performActionOnEntitiesServer(List<Entity> entities, int direction) {
		
	}
	
	@Override
	public void performActionOnEntitiesClient(List<Entity> entities, int direction) {
		
	}
	
	@Override
	public void performActionOnEntitiesBoth(List<Entity> entities, int direction) {
		
	}
	
	@Override
	public boolean shouldRenderLaser(EntityPlayer player, int direction) {
		return false;
	}
	
	@Override
	public void actionOnBlock(BlockActionPos action) {
		
	}
}
