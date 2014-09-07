package lasermod.tileentity;

import net.minecraft.tileentity.TileEntity;

/**
 * @author ProPercivalalb
 */
public class TileEntityLaserDevice extends TileEntity {

	private boolean needsUpdate;
	
	public boolean requiresUpdate() {
		return this.needsUpdate;
	}
	
	public void setNeedsUpdate() {
		this.needsUpdate = true;
	}
	
	public void setNoUpdate() {
		this.needsUpdate = false;
	}
}
