package lasermod.api;

import java.util.List;

import lasermod.util.BlockActionPos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author ProPercivalalb
 */
public interface ILaser {

	public void performActionOnEntitiesBoth(List<Entity> entities, ForgeDirection dir);
	public void performActionOnEntitiesClient(List<Entity> entities, ForgeDirection dir);
	public void performActionOnEntitiesServer(List<Entity> entities, ForgeDirection dir);
	
	public boolean shouldRenderLaser(EntityPlayer player, ForgeDirection dir);
	public void actionOnBlock(BlockActionPos reciver);
}
