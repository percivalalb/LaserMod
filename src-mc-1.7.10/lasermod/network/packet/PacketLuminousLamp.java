package lasermod.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import lasermod.api.LaserInGame;
import lasermod.network.IPacket;
import lasermod.tileentity.TileEntityColourConverter;
import lasermod.tileentity.TileEntityLuminousLamp;
import lasermod.tileentity.TileEntityReflector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class PacketLuminousLamp extends IPacket {

	public int x, y, z;
	public ArrayList<LaserInGame> lasers;
    
    public PacketLuminousLamp() {}
    public PacketLuminousLamp(TileEntityLuminousLamp luminousPanel) {
        this.x = luminousPanel.xCoord;
        this.y = luminousPanel.yCoord;
        this.z = luminousPanel.zCoord;
        this.lasers = luminousPanel.lasers;
    }
    
	@Override
	public void read(PacketBuffer packetbuffer) throws IOException {
		this.x = packetbuffer.readInt();
		this.y = packetbuffer.readInt();
		this.z = packetbuffer.readInt();
		
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
		
		packetbuffer.writeInt(this.lasers.size());
		
		for(int i = 0; i < this.lasers.size(); ++i) 
			this.lasers.get(i).writeToPacket(packetbuffer);
	}
	
	@Override
	public void execute(EntityPlayer player) {
		World world = player.worldObj;
		TileEntity tileEntity = world.getTileEntity(this.x, this.y, this.z);
		
		if(!(tileEntity instanceof TileEntityLuminousLamp)) 
			return;
		TileEntityLuminousLamp colourConverter = (TileEntityLuminousLamp)tileEntity;
		colourConverter.lasers = this.lasers;
		world.markBlockForUpdate(this.x, this.y, this.z);
	}
}
