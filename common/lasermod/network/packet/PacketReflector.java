package lasermod.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import lasermod.api.LaserInGame;
import lasermod.network.IPacket;
import lasermod.tileentity.TileEntityReflector;
import net.minecraft.entity.player.EntityPlayer;
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
	public void read(DataInputStream data) throws IOException {
		this.x = data.readInt();
		this.y = data.readInt();
		this.z = data.readInt();
		this.openSides = new boolean[6];
	    for(int i = 0; i < 6; ++i)
	    	this.openSides[i] = data.readBoolean();
		
	    this.lasers = new ArrayList<LaserInGame>();
	    int count = data.readInt();
	    for(int i = 0; i < count; ++i)
	    	this.lasers.add(new LaserInGame().readFromPacket(data));
	}
	
	@Override
	public void write(DataOutputStream data) throws IOException {
		data.writeInt(this.x);
		data.writeInt(this.y);
		data.writeInt(this.z);
		for(int i = 0; i < 6; ++i)
			data.writeBoolean(this.openSides[i]);
		
		data.writeInt(this.lasers.size());
		
		for(int i = 0; i < this.lasers.size(); ++i) 
			this.lasers.get(i).writeToPacket(data);
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
		FMLLog.info("LOADDING");
	}
}
