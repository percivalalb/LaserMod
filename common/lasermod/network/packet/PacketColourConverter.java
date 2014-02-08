package lasermod.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import lasermod.api.LaserInGame;
import lasermod.helper.PacketHelper;
import lasermod.network.IPacket;
import lasermod.tileentity.TileEntityColourConverter;

/**
 * @author ProPercivalalb
 */
public class PacketColourConverter extends IPacket {

	public int x, y, z;
    public LaserInGame laser;
    public int colour;
    
    public PacketColourConverter() {}
    public PacketColourConverter(TileEntityColourConverter colourConverter) {
        this.x = colourConverter.xCoord;
        this.y = colourConverter.yCoord;
        this.z = colourConverter.zCoord;
        this.laser = colourConverter.getOutputLaser(colourConverter.getBlockMetadata());
        this.colour = colourConverter.colour;
    }
    
	@Override
	public void read(DataInputStream data) throws IOException {
		this.x = data.readInt();
		this.y = data.readInt();
		this.z = data.readInt();
		if(data.readBoolean())
			this.laser = new LaserInGame().readFromPacket(data);
		this.colour = data.readInt();
	}
	
	@Override
	public void write(DataOutputStream data) throws IOException {
		data.writeInt(this.x);
		data.writeInt(this.y);
		data.writeInt(this.z);
		data.writeBoolean(this.laser != null);
		if(this.laser != null)
			this.laser.writeToPacket(data);
		data.writeInt(this.colour);
	}
	
	@Override
	public void execute(EntityPlayer player) {
		World world = player.worldObj;
		TileEntity tileEntity = world.getTileEntity(this.x, this.y, this.z);
		
		if(!(tileEntity instanceof TileEntityColourConverter)) 
			return;
		TileEntityColourConverter colourConverter = (TileEntityColourConverter)tileEntity;
		colourConverter.setLaser(this.laser);
		colourConverter.colour = this.colour;
		world.markBlockForUpdate(this.x, this.y, this.z);
	}
}
