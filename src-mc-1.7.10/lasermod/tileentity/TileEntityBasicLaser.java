package lasermod.tileentity;

import java.util.Arrays;
import java.util.List;

import lasermod.ModBlocks;
import lasermod.api.ILaser;
import lasermod.api.ILaserProvider;
import lasermod.api.LaserInGame;
import lasermod.api.base.TileEntityLaserDevice;
import lasermod.util.BlockActionPos;
import lasermod.util.LaserUtil;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author ProPercivalalb
 */
public class TileEntityBasicLaser extends TileEntityLaserDevice implements ILaserProvider {

	@Override
	public void updateLasers(boolean client) {
		if(!client) {
			if(this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord)) {
				BlockActionPos reciver = LaserUtil.getFirstBlock(this, ForgeDirection.getOrientation(this.getBlockMetadata()));
		    	if(reciver != null && reciver.isLaserReceiver(ForgeDirection.getOrientation(this.getBlockMetadata()))) {
		    		  	
		    		LaserInGame laserInGame = this.getOutputLaser(ForgeDirection.getOrientation(this.getBlockMetadata()));
		
		    		if(reciver.getLaserReceiver(ForgeDirection.getOrientation(this.getBlockMetadata())).canPassOnSide(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite(), laserInGame))
		    			reciver.getLaserReceiver(ForgeDirection.getOrientation(this.getBlockMetadata())).passLaser(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite(), laserInGame);
		    	}
		    	else if(reciver != null) {
		    		LaserInGame laserInGame = this.getOutputLaser(ForgeDirection.getOrientation(this.getBlockMetadata()));
		    		
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
	public LaserInGame getOutputLaser(ForgeDirection dir) {
		return new LaserInGame().setDirection(dir.getOpposite());
	}
	
	@Override
	public int getX() { return this.xCoord; }
	@Override
	public int getY() { return this.yCoord; }
	@Override
	public int getZ() { return this.zCoord; }

	@Override
	public World getWorld() {
		return this.worldObj;
	}
	
	@Override
	public boolean isSendingSignalFromSide(World world, int askerX, int askerY, int askerZ, ForgeDirection dir) {
		return this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord) && this.getBlockMetadata() == dir.ordinal();
	}
	
	@Override
	public int getDistance(ForgeDirection dir) {
		return 64;
	}

	@Override
	public boolean isForgeMultipart() {
		return false;
	}
	
	@Override
	public List<LaserInGame> getOutputLasers() {
		return Arrays.asList(this.getOutputLaser(ForgeDirection.getOrientation(this.getBlockMetadata())));
	}
}
