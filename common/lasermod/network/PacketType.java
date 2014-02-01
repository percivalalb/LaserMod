package lasermod.network;

import lasermod.network.packet.*;

/**
 * @author ProPercivalalb
 */
public enum PacketType {

	TEMP(PacketTemp.class);
	
	public Class<? extends IPacket> packetClass;
	
	PacketType(Class<? extends IPacket> packetClass) {
		this.packetClass = packetClass;
	}
}
