package lasermod.tileentity;

import lasermod.api.base.TileEntityMultiSidedReciever;
import lasermod.network.PacketDispatcher;
import lasermod.network.packet.client.LuminousLampMessage;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class TileEntityLuminousLamp extends TileEntityMultiSidedReciever {
	
	@Override
	public void sendUpdateDescription() {
		PacketDispatcher.sendToAllAround(new LuminousLampMessage(this), this, 512);
	}

	@Override
	public void onLaserPass(World world) {

	}

	@Override
	public void onLaserRemoved(World world) {
		
	}
	
	@Override
	public Packet getDescriptionPacket() {
	    return PacketDispatcher.getPacket(new LuminousLampMessage(this));
	}
}
