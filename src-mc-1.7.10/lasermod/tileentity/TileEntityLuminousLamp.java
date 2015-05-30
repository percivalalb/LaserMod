package lasermod.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lasermod.api.ILaser;
import lasermod.api.ILaserReceiver;
import lasermod.api.LaserInGame;
import lasermod.api.base.TileEntityLaserDevice;
import lasermod.api.base.TileEntityMultiSidedReciever;
import lasermod.network.PacketDispatcher;
import lasermod.network.packet.client.LuminousLampMessage;
import lasermod.util.LaserUtil;
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
