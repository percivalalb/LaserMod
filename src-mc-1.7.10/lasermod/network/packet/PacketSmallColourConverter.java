package lasermod.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lasermod.api.LaserInGame;
import lasermod.network.IPacket;
import lasermod.tileentity.TileEntityColourConverter;
import lasermod.tileentity.TileEntitySmallColourConverter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class PacketSmallColourConverter extends IPacket {

	public int x, y, z;
    public LaserInGame laser;
    public int colour;
    
    public PacketSmallColourConverter() {}
    public PacketSmallColourConverter(TileEntitySmallColourConverter colourConverter) {
        this.x = colourConverter.xCoord;
        this.y = colourConverter.yCoord;
        this.z = colourConverter.zCoord;
        this.laser = colourConverter.getOutputLaser(colourConverter.getBlockMetadata());
        this.colour = colourConverter.colour;
    }
    
	@Override
	public void read(PacketBuffer packetbuffer) throws IOException {
		this.x = packetbuffer.readInt();
		this.y = packetbuffer.readInt();
		this.z = packetbuffer.readInt();
		if(packetbuffer.readBoolean())
			this.laser = new LaserInGame().readFromPacket(packetbuffer);
		this.colour = packetbuffer.readInt();
	}
	
	@Override
	public void write(PacketBuffer packetbuffer) throws IOException {
		packetbuffer.writeInt(this.x);
		packetbuffer.writeInt(this.y);
		packetbuffer.writeInt(this.z);
		packetbuffer.writeBoolean(this.laser != null);
		if(this.laser != null)
			this.laser.writeToPacket(packetbuffer);
		packetbuffer.writeInt(this.colour);
	}
	
	@Override
	public void execute(EntityPlayer player) {
		World world = player.worldObj;
		TileEntity tileEntity = world.getTileEntity(this.x, this.y, this.z);
		
		if(!(tileEntity instanceof TileEntitySmallColourConverter)) 
			return;
		TileEntitySmallColourConverter colourConverter = (TileEntitySmallColourConverter)tileEntity;
		colourConverter.setLaser(this.laser);
		colourConverter.colour = this.colour;
		world.markBlockForUpdate(this.x, this.y, this.z);
	}
}
