package lasermod.api;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author ProPercivalalb
 */
public interface ILaser {

	public void performActionOnEntities(List<Entity> entities, int direction);
	
	public boolean shouldRenderLaser(EntityPlayer player, int direction);
}
