package lasermod.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
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
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
	    this.writeToNBT(nbttagcompound);
	    return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, -1, nbttagcompound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.func_148857_g());
		this.setNeedsUpdate();
	}

}
