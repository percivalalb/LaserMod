package lasermod.block.laser;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import lasermod.api.ILaser;

/**
 * @author ProPercivalalb
 */
public class IceLaser implements ILaser {

	@Override
	public void performActionOnEntities(List<Entity> entities, int direction) {
		for(Entity entity : entities) {
			
		}
	}
	
	@Override
	public boolean shouldRenderLaser(EntityPlayer player, int direction) {
		return true;
	}
}
