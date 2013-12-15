package lasermod.block.laser;

import java.util.List;

import net.minecraft.entity.Entity;
import lasermod.api.ILaser;

/**
 * @author ProPercivalalb
 */
public class DefaultLaser implements ILaser {

	@Override
	public void performActionOnEntities(List<Entity> entities, int direction) {
		
	}

	@Override
	public boolean shouldRenderLaser(int direction) {
		return true;
	}

}
