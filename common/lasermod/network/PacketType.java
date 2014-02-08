package lasermod.network;

import lasermod.network.packet.*;

/**
 * @author ProPercivalalb
 */
public enum PacketType {

	COLOUR_CONVERTER(PacketColourConverter.class),
	REFLECTOR(PacketReflector.class);
	
	public Class<? extends IPacket> packetClass;
	
	PacketType(Class<? extends IPacket> packetClass) {
		this.packetClass = packetClass;
	}
	
	public static byte getIdFromClass(Class<? extends IPacket> packetClass) {
		for(PacketType type : values())
			if(type.packetClass == packetClass)
				return (byte)type.ordinal();
		return -1;
	}
}
