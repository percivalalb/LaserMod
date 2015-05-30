package lasermod.laser;

import java.util.List;

import lasermod.api.ILaser;
import lasermod.api.LaserModAPI;
import lasermod.util.BlockActionPos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.FMLLog;

/**
 * @author ProPercivalalb
 */
public class MiningLaser implements ILaser {

	@Override
	public void performActionOnEntitiesServer(List<Entity> entities, int direction) {
		
	}
	
	@Override
	public void performActionOnEntitiesClient(List<Entity> entities, int direction) {
		
	}
	
	@Override
	public void performActionOnEntitiesBoth(List<Entity> entities, int direction) {
		
	}
	
	@Override
	public boolean shouldRenderLaser(EntityPlayer player, int direction) {
		return true;
	}
	
	@Override
	public void actionOnBlock(BlockActionPos action) {
		if(LaserModAPI.MINING_BLACKLIST.contains(action.block, action.meta)) return;
		
		int harvestLevel = action.block.getHarvestLevel(action.meta);
		FMLLog.info("harvest " + harvestLevel);
		action.block.dropBlockAsItem(action.world, action.x, action.y, action.z, action.meta, 0);
		action.world.setBlock(action.x, action.y, action.z, Blocks.air);
		action.world.playSoundEffect(action.x + 0.5D, action.y + 0.5D, action.z + 0.5D, "random.fizz", 0.3F, action.world.rand.nextFloat() * 0.4F + 0.2F);
	}
}
