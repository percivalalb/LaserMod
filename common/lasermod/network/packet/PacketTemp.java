package lasermod.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import lasermod.network.IPacket;

/**
 * @author ProPercivalalb
 */
public class PacketTemp implements IPacket {

	@Override
	public void read(ChannelHandlerContext ctx, ByteBuf bytes) throws IOException {
		
	}

	@Override
	public void write(ChannelHandlerContext ctx, ByteBuf bytes) throws IOException {
		
	}

	@Override
	public void execute(EntityPlayer player) {
		
	}

}
