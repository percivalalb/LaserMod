package lasermod.api;

import java.util.List;

import lasermod.util.BlockActionPos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;

/**
 * @author ProPercivalalb
 */
public interface ILaser {

	public void performActionOnEntitiesBoth(List<Entity> entities, EnumFacing dir);
	public void performActionOnEntitiesClient(List<Entity> entities, EnumFacing dir);
	public void performActionOnEntitiesServer(List<Entity> entities, EnumFacing dir);
	
	public boolean shouldRenderLaser(EntityPlayer player, EnumFacing dir);
	public void actionOnBlock(BlockActionPos reciver);
}
