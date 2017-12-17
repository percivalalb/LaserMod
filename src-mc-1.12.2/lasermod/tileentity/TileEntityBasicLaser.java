package lasermod.tileentity;

import java.util.Arrays;
import java.util.List;

import lasermod.api.ILaser;
import lasermod.api.ILaserProvider;
import lasermod.api.LaserInGame;
import lasermod.api.base.TileEntityLaserDevice;
import lasermod.util.BlockActionPos;
import lasermod.util.LaserUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class TileEntityBasicLaser extends TileEntityLaserDevice implements ILaserProvider {

	@Override
	public void updateLasers(boolean client) {
		if(!client) {
			if(this.world.isBlockIndirectlyGettingPowered(this.pos) > 0) {
				BlockActionPos reciver = LaserUtil.getFirstBlock(this, EnumFacing.getFront(this.getBlockMetadata()));
		    	if(reciver != null && reciver.isLaserReceiver(EnumFacing.getFront(this.getBlockMetadata()))) {
		    		  	
		    		LaserInGame laserInGame = this.getOutputLaser(EnumFacing.getFront(this.getBlockMetadata()));
		
		    		if(reciver.getLaserReceiver(EnumFacing.getFront(this.getBlockMetadata())).canPassOnSide(this.world, this.pos, EnumFacing.getFront(this.getBlockMetadata()).getOpposite(), laserInGame))
		    			reciver.getLaserReceiver(EnumFacing.getFront(this.getBlockMetadata())).passLaser(this.world, this.pos, EnumFacing.getFront(this.getBlockMetadata()).getOpposite(), laserInGame);
		    	}
		    	else if(reciver != null) {
		    		LaserInGame laserInGame = this.getOutputLaser(EnumFacing.getFront(this.getBlockMetadata()));
		    		
		    		if(laserInGame != null)
			    		for(ILaser laser : laserInGame.getLaserType())
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
		return new LaserInGame().setDirection(dir.getOpposite());
	}

	@Override
	public World getWorld() {
		return this.world;
	}
	
	@Override
	public boolean isSendingSignalFromSide(World world, BlockPos askerPos, EnumFacing dir) {
		return this.world.isBlockIndirectlyGettingPowered(this.pos) > 0 && this.getBlockMetadata() == dir.ordinal();
	}
	
	@Override
	public int getDistance(EnumFacing dir) {
		return 64;
	}

	@Override
	public boolean isForgeMultipart() {
		return false;
	}
	
	@Override
	public List<LaserInGame> getOutputLasers() {
		return Arrays.asList(this.getOutputLaser(EnumFacing.getFront(this.getBlockMetadata())));
	}
}
