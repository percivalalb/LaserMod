package lasermod.api.base;

import lasermod.util.LaserUtil;
import net.minecraft.tileentity.TileEntity;

/**
 * @author ProPercivalalb
 */
public abstract class TileEntityLaserDevice extends TileEntity {

	private boolean needsUpdate;
	
	public abstract void updateLasers(boolean client);
	
	@Override
	public void updateEntity() {
		if(this.worldObj.getWorldInfo().getWorldTotalTime() % LaserUtil.TICK_RATE == 0)
			this.updateLasers(this.worldObj.isRemote);
	}
	
	public boolean requiresUpdate() {
		return this.needsUpdate;
	}
	
	public void setUpdateRequired() {
		this.needsUpdate = true;
	}
	
	public void setUpdateComplete() {
		this.needsUpdate = false;
	}
}
