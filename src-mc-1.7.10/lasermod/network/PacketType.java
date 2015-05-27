package lasermod.network;

import lasermod.network.packet.PacketAdvancedLaser;
import lasermod.network.packet.PacketColourConverter;
import lasermod.network.packet.PacketLuminousLamp;
import lasermod.network.packet.PacketReflector;
import lasermod.network.packet.PacketSmallColourConverter;

/**
 * @author ProPercivalalb
 */
public enum PacketType {

	ADVANCED_LASER(PacketAdvancedLaser.class),
	COLOUR_CONVERTER(PacketColourConverter.class),
	SMALL_COLOUR_CONVERTER(PacketSmallColourConverter.class),
	REFLECTOR(PacketReflector.class),
	LUMINOUS_PANEL(PacketLuminousLamp.class);
	
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
