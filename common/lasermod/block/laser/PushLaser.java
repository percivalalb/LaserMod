package lasermod.block.laser;

import java.util.List;

import net.minecraft.entity.Entity;
import lasermod.api.ILaser;

/**
 * @author ProPercivalalb
 */
public class PushLaser implements ILaser {

	@Override
	public void performActionOnEntities(List<Entity> entities, int direction) {
		for(Entity entity : entities) {
			
		}
	}
}
