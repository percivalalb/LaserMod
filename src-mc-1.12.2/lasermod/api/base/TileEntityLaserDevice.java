package lasermod.api.base;

import lasermod.util.LaserUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public abstract class TileEntityLaserDevice extends TileEntity implements ITickable {

	private boolean needsUpdate;
	private byte ticker;
	
	public abstract void tickLaserLogic();
	public abstract void tickLaserAction(boolean client);
	
	@Override
	public void update() {
		if(!this.world.isRemote && this.ticker++ % LaserUtil.TICK_RATE == 0) {
			this.tickLaserLogic();
			this.ticker = 0;
		}
		
		if(this.world.getWorldInfo().getWorldTotalTime() % LaserUtil.LASER_RATE == 0)
			this.tickLaserAction(this.world.isRemote);
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
