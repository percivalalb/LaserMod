package lasermod.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lasermod.api.LaserInGame;
import lasermod.network.IPacket;
import lasermod.tileentity.TileEntityColourConverter;
import lasermod.tileentity.TileEntityLuminousPanel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class PacketLuminousPanel extends IPacket {

	public int x, y, z;
    public LaserInGame laser;
    
    public PacketLuminousPanel() {}
    public PacketLuminousPanel(TileEntityLuminousPanel colourConverter) {
        this.x = colourConverter.xCoord;
        this.y = colourConverter.yCoord;
        this.z = colourConverter.zCoord;
        this.laser = colourConverter.laser;
    }
    
	@Override
	public void read(PacketBuffer packetbuffer) throws IOException {
		this.x = packetbuffer.readInt();
		this.y = packetbuffer.readInt();
		this.z = packetbuffer.readInt();
		if(packetbuffer.readBoolean())
			this.laser = new LaserInGame().readFromPacket(packetbuffer);
	}
	
	@Override
	public void write(PacketBuffer packetbuffer) throws IOException {
		packetbuffer.writeInt(this.x);
		packetbuffer.writeInt(this.y);
		packetbuffer.writeInt(this.z);
		packetbuffer.writeBoolean(this.laser != null);
		if(this.laser != null)
			this.laser.writeToPacket(packetbuffer);
	}
	
	@Override
	public void execute(EntityPlayer player) {
		World world = player.worldObj;
		TileEntity tileEntity = world.getTileEntity(this.x, this.y, this.z);
		
		if(!(tileEntity instanceof TileEntityLuminousPanel)) 
			return;
		TileEntityLuminousPanel colourConverter = (TileEntityLuminousPanel)tileEntity;
		colourConverter.laser = this.laser;
		world.markBlockForUpdate(this.x, this.y, this.z);
	}
}
