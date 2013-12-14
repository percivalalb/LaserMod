package lasermod.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import lasermod.LaserMod;
import lasermod.api.LaserInGame;
import lasermod.api.LaserRegistry;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.tileentity.TileEntityReflector;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import cpw.mods.fml.common.network.Player;

/**
 * @author ProPercivalalb
 **/
public class PacketBasicLaserUpdate extends PacketBase {

    public int x, y, z;
    

    public PacketBasicLaserUpdate() {}

    public PacketBasicLaserUpdate(int x, int y, int z, TileEntityBasicLaser reflector) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

	@Override
	public void readPacketData(DataInputStream data, Player p) throws IOException {
	    x = data.readInt();
	    y = data.readInt();
	    z = data.readInt();

	}

	@Override
	public void writePacketData(DataOutputStream data)  throws IOException {
		data.writeInt(x);
	    data.writeInt(y);
	    data.writeInt(z);
	   
	}

	@Override
	public void processPacket() {
		LaserMod.proxy.handleBasicLaserPacket(this);
	}

	@Override
	public String getChannel() {
		return "ewa";
	}
}
