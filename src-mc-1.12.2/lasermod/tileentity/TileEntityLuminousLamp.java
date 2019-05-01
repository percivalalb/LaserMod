package lasermod.tileentity;

import lasermod.api.base.TileEntityMultiSidedReceiver;
import lasermod.block.BlockLuminousLamp;
import lasermod.network.PacketDispatcher;
import lasermod.network.packet.client.LuminousLampMessage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class TileEntityLuminousLamp extends TileEntityMultiSidedReceiver {
	
	@Override
	public void sendUpdateDescription() {
		PacketDispatcher.sendToAllAround(new LuminousLampMessage(this), this, 512);
	}

	@Override
	public void onLaserPass(World world) {
		world.setBlockState(this.pos, world.getBlockState(pos).withProperty(BlockLuminousLamp.ACTIVE, true));
	}

	@Override
	public void onLaserRemoved(World world) {
		if(this.lasers.isEmpty())
			world.setBlockState(this.pos, world.getBlockState(pos).withProperty(BlockLuminousLamp.ACTIVE, false));
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, -1, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
    }
}
