package lasermod.laser;

import java.util.List;

import lasermod.api.ILaser;
import lasermod.util.BlockActionPos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;

/**
 * @author ProPercivalalb
 */
public class InvisibleLaser implements ILaser {

	@Override
	public void performActionOnEntitiesServer(List<Entity> entities, EnumFacing dir) {
		
	}
	
	@Override
	public void performActionOnEntitiesClient(List<Entity> entities, EnumFacing dir) {
		
	}
	
	@Override
	public void performActionOnEntitiesBoth(List<Entity> entities, EnumFacing dir) {
		
	}
	
	@Override
	public boolean shouldRenderLaser(EntityPlayer player, EnumFacing dir) {
		return false;
	}
	
	@Override
	public void actionOnBlock(BlockActionPos action) {
		
	}
}
