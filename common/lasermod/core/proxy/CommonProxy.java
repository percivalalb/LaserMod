package lasermod.core.proxy;

import lasermod.packet.PacketAdvancedLaserUpdate;
import lasermod.packet.PacketColourConverterUpdate;
import lasermod.packet.PacketReflectorUpdate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

/**
 * @author ProPercivalalb
 */
public class CommonProxy implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	public void handleReflectorPacket(PacketReflectorUpdate packet) {}

	public void handleAdvancedLaserPacket(PacketAdvancedLaserUpdate packetAdvancedLaserUpdate) {}

	public void handleColourConverterPacket(PacketColourConverterUpdate packetColourConverterUpdate) {}

	public void registerHandlers() {}

	public void onPreLoad() {}

	public int armorRender(String str) {
		return 0;
	}
}