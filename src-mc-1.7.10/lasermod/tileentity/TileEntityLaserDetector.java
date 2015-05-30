package lasermod.tileentity;

import java.util.Arrays;
import java.util.List;

import lasermod.ModBlocks;
import lasermod.api.ILaserReceiver;
import lasermod.api.LaserInGame;
import lasermod.api.base.TileEntityMultiSidedReciever;
import lasermod.network.PacketDispatcher;
import lasermod.network.packet.client.LaserDetectorMessage;
import lasermod.util.LaserUtil;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;

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