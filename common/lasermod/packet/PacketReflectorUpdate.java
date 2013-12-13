package lasermod.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
public class PacketReflectorUpdate extends PacketBase {

    public int x, y, z;
    public boolean[] openSides;

    public PacketReflectorUpdate() {}

    public PacketReflectorUpdate(int x, int y, int z, TileEntityReflector reflector) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.openSides = reflector.openSides;
    }

	@Override
	public void readPacketData(DataInputStream data, Player p) throws IOException {
	    x = data.readInt();
	    y = data.readInt();
	    z = data.readInt();
	    openSides = new boolean[6];
	    for(int i = 0; i < 6; ++i) {
	    	openSides[i] = data.readBoolean();
	    }
	   
	}

	@Override
	public void writePacketData(DataOutputStream data)  throws IOException {
		data.writeInt(x);
	    data.writeInt(y);
	    data.writeInt(z);
	    for(int i = 0; i < 6; ++i) {
	    	data.writeBoolean(openSides[i]);
	    }
	}

	@Override
	public void processPacket() {
		net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getMinecraft();
		World world = mc.theWorld;
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(!(tileEntity instanceof TileEntityReflector)) 
			return;
		TileEntityReflector reflector = (TileEntityReflector)tileEntity;
		reflector.openSides = openSides;
		
		
	}

	@Override
	public String getChannel() {
		return "reflectorUpdate";
	}
}
