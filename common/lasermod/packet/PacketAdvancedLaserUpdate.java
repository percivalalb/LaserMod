package lasermod.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import lasermod.LaserMod;
import lasermod.api.LaserInGame;
import lasermod.api.LaserRegistry;
import lasermod.tileentity.TileEntityAdvancedLaser;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.tileentity.TileEntityReflector;

import net.minecraft.item.ItemStack;
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
public class PacketAdvancedLaserUpdate extends PacketBase {

    public int x, y, z;
    public ArrayList<ItemStack> upgrades;

    public PacketAdvancedLaserUpdate() {}

    public PacketAdvancedLaserUpdate(int x, int y, int z, TileEntityAdvancedLaser advancedLaser) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.upgrades = advancedLaser.upgrades;
    }

	@Override
	public void readPacketData(DataInputStream data, Player p) throws IOException {
	    x = data.readInt();
	    y = data.readInt();
	    z = data.readInt();
	    upgrades = new ArrayList<ItemStack>();
	    int upgradeCount = data.readInt();
	    for(int i = 0; i < upgradeCount; ++i)
	    	upgrades.add(Packet.readItemStack(data));
	}

	@Override
	public void writePacketData(DataOutputStream data)  throws IOException {
		data.writeInt(x);
	    data.writeInt(y);
	    data.writeInt(z);
	    data.writeInt(upgrades.size());
	    for(int i = 0; i < upgrades.size(); ++i) {
	    	Packet.writeItemStack(upgrades.get(i), data);
	    }
	   
	}

	@Override
	public void processPacket() {
		LaserMod.proxy.handleAdvancedLaserPacket(this);
	}

	@Override
	public String getChannel() {
		return "laser:advanced";
	}
}
