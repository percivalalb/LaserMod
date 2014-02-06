package lasermod.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.util.ArrayList;

import cpw.mods.fml.common.FMLLog;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import lasermod.api.LaserInGame;
import lasermod.helper.PacketHelper;
import lasermod.network.IPacket;
import lasermod.tileentity.TileEntityReflector;

/**
 * @author ProPercivalalb
 */
public class PacketReflector implements IPacket {

	public int x, y, z;
	public boolean[] openSides;
	public ArrayList<LaserInGame> lasers;
    
    public PacketReflector() {}
    public PacketReflector(int x, int y, int z, TileEntityReflector reflector) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.openSides = reflector.openSides;
        this.lasers = reflector.lasers;
    }
    
	@Override
	public void read(ChannelHandlerContext ctx, ByteBuf bytes) throws IOException {
		this.x = bytes.readInt();
		this.y = bytes.readInt();
		this.z = bytes.readInt();
		this.openSides = new boolean[6];
	    for(int i = 0; i < 6; ++i)
	    	this.openSides[i] = bytes.readBoolean();
		
	    this.lasers = new ArrayList<LaserInGame>();
	    int count = bytes.readInt();
	    for(int i = 0; i < count; ++i)
	    	this.lasers.add(new LaserInGame(PacketHelper.readNBTTagCompound(bytes)));
	}
	
	@Override
	public void write(ChannelHandlerContext ctx, ByteBuf bytes) throws IOException {
		bytes.writeInt(this.x);
		bytes.writeInt(this.y);
		bytes.writeInt(this.z);
		for(int i = 0; i < 6; ++i)
			bytes.writeBoolean(this.openSides[i]);
		
		bytes.writeInt(this.lasers.size());
		
		for(int i = 0; i < this.lasers.size(); ++i) 
			PacketHelper.writeNBTTagCompound(this.lasers.get(i).writeToNBT(new NBTTagCompound()), bytes);
	}
	
	@Override
	public void execute(EntityPlayer player) {
		World world = player.worldObj;
		TileEntity tileEntity = world.getTileEntity(this.x, this.y, this.z);
		
		if(!(tileEntity instanceof TileEntityReflector)) 
			return;
		TileEntityReflector reflector = (TileEntityReflector)tileEntity;
		reflector.openSides = this.openSides;
		reflector.lasers = this.lasers;
	}
}
