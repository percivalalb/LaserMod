package lasermod.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import lasermod.LaserMod;
import lasermod.api.ILaser;
import lasermod.api.LaserInGame;
import lasermod.api.LaserRegistry;
import lasermod.tileentity.TileEntityReflector;
import cpw.mods.fml.common.network.Player;

/**
 * @author ProPercivalalb
 **/
public class PacketReflectorUpdate extends PacketBase {
	public int x, y, z;
	public boolean[] openSides;
	public ArrayList<LaserInGame> lasers;

	public PacketReflectorUpdate() {}

	public PacketReflectorUpdate(int x, int y, int z, TileEntityReflector reflector) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.openSides = reflector.openSides;
		this.lasers = reflector.lasers;
	}

	@Override
	public void readPacketData(DataInputStream data, Player p) throws IOException {
		x = data.readInt();
		y = data.readInt();
		z = data.readInt();
		openSides = new boolean[6];
		for (int i = 0; i < 6; ++i)
			openSides[i] = data.readBoolean();
		lasers = new ArrayList<LaserInGame>();
		int amount = data.readInt();
		for (int i = 0; i < amount; ++i) {
			double strength = data.readDouble();
			int laserCount = data.readInt();
			ArrayList<ILaser> laserList = new ArrayList<ILaser>();
			for (int j = 0; j < laserCount; ++j)
				laserList.add(LaserRegistry.getLaserFromId(data.readUTF()));
			int side = data.readInt();
			LaserInGame laserInGame = new LaserInGame(laserList).setStrength(strength).setSide(side);
			laserInGame.red = data.readInt();
			laserInGame.green = data.readInt();
			laserInGame.blue = data.readInt();
			lasers.add(laserInGame);
		}
	}

	@Override
	public void writePacketData(DataOutputStream data) throws IOException {
		data.writeInt(x);
		data.writeInt(y);
		data.writeInt(z);
		for (int i = 0; i < 6; ++i)
			data.writeBoolean(openSides[i]);
		data.writeInt(lasers.size());
		for (int i = 0; i < lasers.size(); ++i) {
			data.writeDouble(lasers.get(i).getStrength());
			data.writeInt(lasers.get(i).laserCount());
			for (ILaser laser : lasers.get(i).getLaserType())
				data.writeUTF(LaserRegistry.getIdFromLaser(laser));
			data.writeInt(lasers.get(i).getSide());
			data.writeInt(lasers.get(i).red);
			data.writeInt(lasers.get(i).green);
			data.writeInt(lasers.get(i).blue);
		}
	}

	@Override
	public void processPacket() {
		LaserMod.proxy.handleReflectorPacket(this);
	}

	@Override
	public String getChannel() {
		return "laser:reflector";
	}
}