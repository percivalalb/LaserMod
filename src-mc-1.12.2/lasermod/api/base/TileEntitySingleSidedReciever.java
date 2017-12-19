package lasermod.api.base;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import lasermod.api.ILaserReceiver;
import lasermod.api.LaserInGame;
import lasermod.util.LaserUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class TileEntitySingleSidedReciever extends TileEntityLaserDevice implements ILaserReceiver {

	public LaserInGame laser;
	
	/**
	 * Override and will be called when there is an update to the lasers
	 */
	public abstract void sendUpdateDescription();
	
	public abstract void onLaserPass(World world);
	
	public abstract void onLaserRemoved(World world);
	
	public abstract EnumFacing getInputSide();
	
	@Override
	public void updateLasers(boolean client) {
		
		if(!client) {
			if(!this.noLaserInput()) {

				if(!LaserUtil.isValidSourceOfPowerOnSide(this, this.getInputSide())) {
					this.laser = null;
					this.sendUpdateDescription();
					this.onLaserRemoved(this.world);
				}	
			}
		}
	}

	@Override
	public void updateLaserAction(boolean client) {
		
	}

	@Override
	public World getWorld() {
		return this.world;
	}
	
	@Override
	public boolean canPassOnSide(World world, BlockPos orginPos, EnumFacing dir, LaserInGame laserInGame) {
		return dir == this.getInputSide() && !Objects.equals(laserInGame, this.laser);
	}
	

	@Override
	public void passLaser(World world, BlockPos orginPos, EnumFacing dir, LaserInGame laserInGame) {
		this.laser = laserInGame;
		this.onLaserPass(world);
		this.sendUpdateDescription();
		this.setUpdateRequired();
	}

	@Override
	public void removeLasersFromSide(World world, BlockPos orginPos, EnumFacing dir) {
		if(dir == this.getInputSide()) {
			boolean flag = this.laser != null;
			this.laser = null;
			if(flag) {
				this.onLaserRemoved(world);
				this.sendUpdateDescription();
				this.setUpdateRequired();
			}
		}
	}

	@Override
	public List<LaserInGame> getInputLasers() {
		return Arrays.asList(this.laser);
	}

	public boolean noLaserInput() {
		return this.laser == null;
	}
}
