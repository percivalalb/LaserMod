package lasermod.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import lasermod.network.IPacket;

/**
 * @author ProPercivalalb
 */
public class PacketTemp extends IPacket {

	@Override
	public void read(DataInputStream data) throws IOException {
		
	}

	@Override
	public void write(DataOutputStream data) throws IOException {
		
	}

	@Override
	public void execute(EntityPlayer player) {
		
	}

}
