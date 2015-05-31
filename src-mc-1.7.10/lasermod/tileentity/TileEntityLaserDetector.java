package lasermod.tileentity;

import lasermod.api.base.TileEntityMultiSidedReciever;
import lasermod.network.PacketDispatcher;
import lasermod.network.packet.client.LaserDetectorMessage;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class TileEntityLaserDetector extends TileEntityMultiSidedReciever {
	
	@Override
	public void sendUpdateDescription() {
		PacketDispatcher.sendToAllAround(new LaserDetectorMessage(this), this, 512);
	}

	@Override
	public void onLaserPass(World world) {
		world.notifyBlockChange(this.xCoord, this.yCoord, this.zCoord, world.getBlock(this.xCoord, this.yCoord, this.zCoord));
	}

	@Override
	public void onLaserRemoved(World world) {
		world.notifyBlockChange(this.xCoord, this.yCoord, this.zCoord, world.getBlock(this.xCoord, this.yCoord, this.zCoord));
	}
	
	@Override
	public Packet getDescriptionPacket() {
	    return PacketDispatcher.getPacket(new LaserDetectorMessage(this));
	}
}