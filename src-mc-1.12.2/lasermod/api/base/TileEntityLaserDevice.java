package lasermod.api.base;

import lasermod.util.LaserUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public abstract class TileEntityLaserDevice extends TileEntity implements ITickable {

	private boolean needsUpdate;
	
	public abstract void updateLasers(boolean client);
	public abstract void updateLaserAction(boolean client);
	public void applyLaserRender() {}
	
	@Override
	public void update() {
		if(this.world.getWorldInfo().getWorldTotalTime() % LaserUtil.TICK_RATE == 0)
			this.updateLasers(this.world.isRemote);
		
		if(this.world.getWorldInfo().getWorldTotalTime() % LaserUtil.LASER_RATE == 0)
			this.updateLaserAction(this.world.isRemote);
		
		if(this.world.isRemote)
			this.applyLaserRender();
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
	
	/**
	@Override
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
    	return INFINITE_EXTENT_AABB;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }**/
}
