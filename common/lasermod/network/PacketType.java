package lasermod.network;

import lasermod.network.packet.*;

/**
 * @author ProPercivalalb
 */
public enum PacketType {

	TEMP(PacketTemp.class),
	COLOUR_CONVERTER(PacketColourConverter.class),
	REFLECTOR(PacketReflector.class);
	
	public Class<? extends IPacket> packetClass;
	
	PacketType(Class<? extends IPacket> packetClass) {
		this.packetClass = packetClass;
	}
}
