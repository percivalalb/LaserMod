package lasermod.api.base;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import lasermod.api.ILaserReceiver;
import lasermod.api.LaserInGame;
import lasermod.util.LaserUtil;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntitySingleSidedReciever extends TileEntityLaserDevice implements ILaserReceiver {

	public LaserInGame laser;
	
	/**
	 * Override and will be called when there is an update to the lasers
	 */
	public abstract void sendUpdateDescription();
	
	public abstract void onLaserPass(World world);
	
	public abstract void onLaserRemoved(World world);
	
	public abstract ForgeDirection getInputSide();
	
	@Override
	public void updateLasers(boolean client) {
		
		if(!client) {
			if(!this.noLaserInput()) {

				if(!LaserUtil.isValidSourceOfPowerOnSide(this, this.getInputSide().ordinal())) {
					this.laser = null;
					this.sendUpdateDescription();
					this.onLaserRemoved(this.worldObj);
				}	
			}
		}
	}

	@Override
	public void updateLaserAction(boolean client) {
		
	}
	
	@Override
	public int getX() {
		return this.xCoord;
	}

	@Override
	public int getY() {
		return this.yCoord;
	}

	@Override
	public int getZ() {
		return this.zCoord;
	}

	@Override
	public World getWorld() {
		return this.worldObj;
	}
	
	@Override
	public boolean canPassOnSide(World world, int orginX, int orginY, int orginZ, int side, LaserInGame laserInGame) {
		return side == this.getInputSide().ordinal() && !Objects.equals(laserInGame, this.laser);
	}
	

	@Override
	public void passLaser(World world, int orginX, int orginY, int orginZ, int side, LaserInGame laserInGame) {
		this.laser = laserInGame;
		this.onLaserPass(world);
		this.sendUpdateDescription();
		this.setUpdateRequired();
	}

	@Override
	public void removeLasersFromSide(World world, int orginX, int orginY, int orginZ, int side) {
		if(side == this.getInputSide().ordinal()) {
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
