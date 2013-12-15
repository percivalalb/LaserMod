package lasermod.block.laser;

import java.util.List;

import net.minecraft.entity.Entity;
import lasermod.api.ILaser;

/**
 * @author ProPercivalalb
 */
public class InvisibleLaser implements ILaser {

	@Override
	public void performActionOnEntities(List<Entity> entities, int direction) {
		for(Entity entity : entities) {
			
		}
	}
	
	@Override
	public boolean shouldRenderLaser(int direction) {
		return false;
	}
}
