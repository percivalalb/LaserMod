package lasermod.block.laser;

import java.util.List;

import net.minecraft.entity.Entity;
import lasermod.api.ILaser;
import lasermod.tileentity.TileEntityAdvancedLaser;

/**
 * @author ProPercivalalb
 */
public class FireLaser implements ILaser {

	@Override
	public void performActionOnEntities(TileEntityAdvancedLaser tileEntityAdvancedLaser, List<Entity> entities) {
		for(Entity entity : entities) {
			entity.setFire(4);
		}
	}
}
