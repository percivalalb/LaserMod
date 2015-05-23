package lasermod.api;

import java.util.List;

import lasermod.util.BlockActionPos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author ProPercivalalb
 */
public interface ILaser {

	public void performActionOnEntitiesBoth(List<Entity> entities, int direction);
	public void performActionOnEntitiesClient(List<Entity> entities, int direction);
	public void performActionOnEntitiesServer(List<Entity> entities, int direction);
	
	public boolean shouldRenderLaser(EntityPlayer player, int direction);
	public void actionOnBlock(BlockActionPos reciver);
}
