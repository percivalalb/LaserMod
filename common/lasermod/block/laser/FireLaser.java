package lasermod.block.laser;

import java.util.List;

import net.minecraft.entity.Entity;
import lasermod.api.ILaser;

/**
 * @author ProPercivalalb
 */
public class FireLaser implements ILaser {

	@Override
	public void performActionOnEntities(List<Entity> entities) {
		for(Entity entity : entities) {
			entity.setFire(4);
		}
	}
}
