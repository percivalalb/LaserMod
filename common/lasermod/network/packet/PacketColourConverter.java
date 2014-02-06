package lasermod.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

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
public class PacketColourConverter implements IPacket {

	public int x, y, z;
    public LaserInGame laser;
    public int colour;
    
    public PacketColourConverter() {}
    public PacketColourConverter(int x, int y, int z, TileEntityColourConverter colourConverter) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.laser = colourConverter.getOutputLaser(colourConverter.getBlockMetadata());
        this.colour = colourConverter.colour;
    }
    
	@Override
	public void read(ChannelHandlerContext ctx, ByteBuf bytes) throws IOException {
		this.x = bytes.readInt();
		this.y = bytes.readInt();
		this.z = bytes.readInt();
		if(bytes.readBoolean())
			this.laser = new LaserInGame(PacketHelper.readNBTTagCompound(bytes));
		this.colour = bytes.readInt();
	}
	
	@Override
	public void write(ChannelHandlerContext ctx, ByteBuf bytes) throws IOException {
		bytes.writeInt(this.x);
		bytes.writeInt(this.y);
		bytes.writeInt(this.z);
		bytes.writeBoolean(this.laser != null);
		if(this.laser != null)
			PacketHelper.writeNBTTagCompound(this.laser.writeToNBT(new NBTTagCompound()), bytes);
		bytes.writeInt(this.colour);
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
