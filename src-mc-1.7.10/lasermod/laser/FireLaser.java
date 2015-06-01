package lasermod.laser;

import java.util.List;

import lasermod.api.ILaser;
import lasermod.util.BlockActionPos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author ProPercivalalb
 */
public class FireLaser implements ILaser {

	@Override
	public void performActionOnEntitiesServer(List<Entity> entities, ForgeDirection dir) {
		for(Entity entity : entities) {
			entity.setFire(4);
		}
	}
	
	@Override
	public void performActionOnEntitiesClient(List<Entity> entities, ForgeDirection dir) {
		
	}
	
	@Override
	public void performActionOnEntitiesBoth(List<Entity> entities, ForgeDirection dir) {
		
	}
	
	@Override
	public boolean shouldRenderLaser(EntityPlayer player, ForgeDirection dir) {
		return true;
	}
	
	@Override
	public void actionOnBlock(BlockActionPos action) {
		
	}
}
