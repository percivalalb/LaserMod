package lasermod.api.base;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lasermod.util.LaserUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

/**
 * @author ProPercivalalb
 */
public abstract class TileEntityLaserDevice extends TileEntity {

	private boolean needsUpdate;
	
	public abstract void updateLasers(boolean client);
	public abstract void updateLaserAction(boolean client);
	
	@Override
	public void updateEntity() {
		if(this.worldObj.getWorldInfo().getWorldTotalTime() % LaserUtil.TICK_RATE == 0)
			this.updateLasers(this.worldObj.isRemote);
		
		if(this.worldObj.getWorldInfo().getWorldTotalTime() % LaserUtil.LASER_RATE == 0)
			this.updateLaserAction(this.worldObj.isRemote);
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
	
	@Override
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
    	return INFINITE_EXTENT_AABB;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }
}
