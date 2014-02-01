package lasermod.network;

import java.io.IOException;

import lasermod.helper.PacketHelper;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author ProPercivalalb
 */
public class ChannelHandler extends FMLIndexedMessageToMessageCodec<IPacket>{
	
    public ChannelHandler() {
        for (int i = 0; i < PacketType.values().length; i++)
            addDiscriminator(i, PacketType.values()[i].packetClass);
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, IPacket msg, ByteBuf bytes) throws Exception {
    	if (FMLCommonHandler.instance().getEffectiveSide().isClient())
    		PacketHelper.writeString(FMLClientHandler.instance().getClientPlayerEntity().getCommandSenderName(), bytes);
        	 
    	msg.write(ctx, bytes);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf bytes, IPacket msg)  {
		try {
			EntityPlayer player;
				
			if(FMLCommonHandler.instance().getEffectiveSide().isClient())
				player = FMLClientHandler.instance().getClientPlayerEntity();
			else
				player = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().getPlayerForUsername(PacketHelper.readString(256, bytes));
				
			msg.read(ctx, bytes);
			msg.execute(player);
		} 
    	catch(IOException e) {
			e.printStackTrace();
		}
    }
}
