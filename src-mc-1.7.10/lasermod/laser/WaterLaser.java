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
public class WaterLaser implements ILaser {

	public static final float SPEED_MULTIPLYER = 1.0F;
	
	@Override
	public void performActionOnEntitiesServer(List<Entity> entities, ForgeDirection dir) {
		for(Entity entity : entities) {
			
		}
	}
	
	@Override
	public void performActionOnEntitiesClient(List<Entity> entities, ForgeDirection dir) {
		for(Entity entity : entities) {
			
		}
	}
	
	@Override
	public void performActionOnEntitiesBoth(List<Entity> entities, ForgeDirection dir) {
		for(Entity entity : entities) {
			
		}
	}
	
	@Override
	public boolean shouldRenderLaser(EntityPlayer player, ForgeDirection dir) {
		return true;
	}
	
	@Override
	public void actionOnBlock(BlockActionPos action) {
		
	}
}
