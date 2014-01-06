package lasermod.block.laser;

import java.util.List;

import lasermod.api.ILaser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author ProPercivalalb
 */
public class InvisibleLaser implements ILaser {
	@Override
	public void performActionOnEntitiesServer(List<Entity> entities, int direction) {
		for (Entity entity : entities) {}
	}

	@Override
	public void performActionOnEntitiesClient(List<Entity> entities, int direction) {
		for (Entity entity : entities) {}
	}

	@Override
	public void performActionOnEntitiesBoth(List<Entity> entities, int direction) {
		for (Entity entity : entities) {}
	}

	@Override
	public boolean shouldRenderLaser(EntityPlayer player, int direction) {
		return false;
	}
}