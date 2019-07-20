package lasermod.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

public interface IPacket<T> {

	void encode(T msg, PacketBuffer buf);
	
	T decode(PacketBuffer buf);
	
	void handle(T msg, EntityPlayer player);
}
