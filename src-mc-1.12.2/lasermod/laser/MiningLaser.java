package lasermod.laser;

import java.util.List;

import lasermod.LaserMod;
import lasermod.api.ILaser;
import lasermod.api.LaserModAPI;
import lasermod.util.BlockActionPos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;

/**
 * @author ProPercivalalb
 */
public class MiningLaser implements ILaser {

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
		return true;
	}
	
	@Override
	public void actionOnBlock(BlockActionPos action) {
		LaserMod.LOGGER.debug("harvest " + 2);
		if(LaserModAPI.MINING_BLACKLIST.contains(action.block, action.meta)) return;
		
		int harvestLevel = action.block.getHarvestLevel(action.state);
		LaserMod.LOGGER.debug("harvest " + harvestLevel);
		action.block.dropBlockAsItem(action.world, action.pos, action.state, 0);
		action.world.setBlockToAir(action.pos);
		//TODO action.world.playSoundEffect(action.pos.getX() + 0.5D, action.pos.getY() + 0.5D, action.pos.getZ() + 0.5D, "random.fizz", 0.3F, action.world.rand.nextFloat() * 0.4F + 0.2F);
	}
}
