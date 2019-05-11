package lasermod.tileentity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lasermod.LaserMod;
import lasermod.api.ILaserProvider;
import lasermod.api.LaserInGame;
import lasermod.api.LaserType;
import lasermod.api.base.TileEntityLaserDevice;
import lasermod.block.BlockBasicLaser;
import lasermod.block.BlockPoweredRedstone;
import lasermod.util.BlockActionPos;
import lasermod.util.LaserUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class TileEntityBasicLaser extends TileEntityLaserDevice implements ILaserProvider {

	@Override
	public void updateLasers(boolean client) {
		if(!client) {
			IBlockState state = this.getWorld().getBlockState(this.pos);
			
			if(state.getValue(BlockPoweredRedstone.POWERED)) {
				EnumFacing facing = state.getValue(BlockBasicLaser.FACING);
				
				BlockActionPos reciver = LaserUtil.getFirstBlock(this, facing);
				
		    	if(reciver != null && reciver.isLaserReceiver(facing)) {
		    		LaserInGame laserInGame = this.getOutputLaser(facing);
		    		//LaserMod.LOGGER.debug(("Passings");
		    		if(reciver.getLaserReceiver(facing).canReceive(this.world, this.pos, facing.getOpposite(), laserInGame)) {
		    			reciver.getLaserReceiver(facing).onLaserIncident(this.world, this.pos, facing.getOpposite(), laserInGame);
		    			LaserMod.LOGGER.debug("Passing");
		    		}
		    			
		    	}
		    	else if(reciver != null) {
		    		LaserInGame laserInGame = this.getOutputLaser(facing);
		    		
		    		if(laserInGame != null)
			    		for(LaserType laser : laserInGame.getLaserType())
			    			laser.actionOnBlock(reciver);
		
		    	}
			}
		}
	}
	
	@Override
	public void updateLaserAction(boolean client) {
		
	}
	
	@Override
	public LaserInGame getOutputLaser(EnumFacing dir) {
		return new LaserInGame().setDirection(dir);
	}

	@Override
	public World getWorld() {
		return this.world;
	}
	
	@Override
	public boolean isEmittingFromSide(World worldIn, BlockPos askerPos, EnumFacing side) {
		IBlockState state = this.getWorld().getBlockState(this.pos);
		
		return state.getValue(BlockBasicLaser.POWERED) && state.getValue(BlockBasicLaser.FACING) == side;
	}
	
	@Override
	public int getRange(EnumFacing dir) {
		return 64;
	}

	@Override
	public boolean isForgeMultipart() {
		return false;
	}
	
	@Override
	public List<LaserInGame> getOutputLasers() {
		IBlockState state = this.getWorld().getBlockState(this.pos);
		
		if(state.getValue(BlockBasicLaser.POWERED))
			return Arrays.asList(this.getOutputLaser(state.getValue(BlockBasicLaser.FACING)));
		
		return Collections.<LaserInGame>emptyList();
	}
	
	@Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }
}
