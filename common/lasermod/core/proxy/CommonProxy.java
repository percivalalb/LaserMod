package lasermod.core.proxy;

import lasermod.packet.PacketBasicLaserUpdate;
import lasermod.packet.PacketReflectorUpdate;
import lasermod.tileentity.TileEntityReflector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
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

	public void handleBasicLaserPacket(PacketBasicLaserUpdate packetBasicLaserUpdate) {}
	
	public void registerHandlers() {}

	public void onPreLoad() {}

}
