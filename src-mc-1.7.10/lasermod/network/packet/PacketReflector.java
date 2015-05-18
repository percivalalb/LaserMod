package lasermod.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import lasermod.api.LaserInGame;
import lasermod.network.IPacket;
import lasermod.tileentity.TileEntityReflector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;

/**
 * @author ProPercivalalb
 */
public class PacketReflector extends IPacket {

	public int x, y, z;
	public boolean[] openSides;
	public ArrayList<LaserInGame> lasers;
    
    public PacketReflector() {}
    public PacketReflector(TileEntityReflector reflector) {
        this.x = reflector.xCoord;
        this.y = reflector.yCoord;
        this.z = reflector.zCoord;
        this.openSides = reflector.closedSides;
        this.lasers = reflector.lasers;
    }
    
	@Override
	public void read(PacketBuffer packetbuffer) throws IOException {
		this.x = packetbuffer.readInt();
		this.y = packetbuffer.readInt();
		this.z = packetbuffer.readInt();
		this.openSides = new boolean[6];
	    for(int i = 0; i < 6; ++i)
	    	this.openSides[i] = packetbuffer.readBoolean();
		
	    this.lasers = new ArrayList<LaserInGame>();
	    int count = packetbuffer.readInt();
	    for(int i = 0; i < count; ++i)
	    	this.lasers.add(new LaserInGame().readFromPacket(packetbuffer));
	}
	
	@Override
	public void write(PacketBuffer packetbuffer) throws IOException {
		packetbuffer.writeInt(this.x);
		packetbuffer.writeInt(this.y);
		packetbuffer.writeInt(this.z);
		for(int i = 0; i < 6; ++i)
			packetbuffer.writeBoolean(this.openSides[i]);
		
		packetbuffer.writeInt(this.lasers.size());
		
		for(int i = 0; i < this.lasers.size(); ++i) 
			this.lasers.get(i).writeToPacket(packetbuffer);
	}
	
	@Override
	public void execute(EntityPlayer player) {
		World world = player.worldObj;
		TileEntity tileEntity = world.getTileEntity(this.x, this.y, this.z);
		
		if(!(tileEntity instanceof TileEntityReflector)) 
			return;
		TileEntityReflector reflector = (TileEntityReflector)tileEntity;
		reflector.closedSides = this.openSides;
		reflector.lasers = this.lasers;
	}
}
