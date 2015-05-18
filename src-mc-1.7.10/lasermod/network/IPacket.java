package lasermod.network;

import java.io.IOException;

import lasermod.LaserMod;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;

/**
 * @author ProPercivalalb
 */
public abstract class IPacket {
	
	public abstract void read(PacketBuffer packetbuffer) throws IOException;
	public abstract void write(PacketBuffer packetbuffer) throws IOException;

	public abstract void execute(EntityPlayer player);
	
	public Packet getPacket() {
		if(FMLCommonHandler.instance().getEffectiveSide().isClient())
			return LaserMod.NETWORK_MANAGER.clientOutboundChannel.generatePacketFrom(this);
		else
			return LaserMod.NETWORK_MANAGER.serverOutboundChannel.generatePacketFrom(this);
	}
}
