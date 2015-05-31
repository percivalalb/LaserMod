package lasermod.network.packet.client;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;

import lasermod.api.LaserInGame;
import lasermod.network.AbstractClientMessageHandler;
import lasermod.tileentity.TileEntityReflector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class ReflectorMessage implements IMessage {
	
	public int x, y, z;
	public boolean[] closedSides;
	public ArrayList<LaserInGame> lasers;
	
	public ReflectorMessage() {}
	public ReflectorMessage(TileEntityReflector reflector) {
	    this.x = reflector.xCoord;
	    this.y = reflector.yCoord;
	    this.z = reflector.zCoord;
	    this.closedSides = reflector.closedSides;
	    this.lasers = reflector.lasers;
	}
	
	@Override
	public void fromBytes(ByteBuf buffer) {
		this.x = buffer.readInt();
		this.y = buffer.readInt();
		this.z = buffer.readInt();
		
		this.closedSides = new boolean[6];
	    for(int i = 0; i < 6; ++i)
	    	this.closedSides[i] = buffer.readBoolean();
		

	    this.lasers = new ArrayList<LaserInGame>();
	    int count = buffer.readInt();
	    for(int i = 0; i < count; ++i)
	    	this.lasers.add(new LaserInGame().readFromPacket(buffer));
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeInt(this.x);
		buffer.writeInt(this.y);
		buffer.writeInt(this.z);
		
		for(int i = 0; i < 6; ++i)
			buffer.writeBoolean(this.closedSides[i]);
		
		buffer.writeInt(this.lasers.size());
		
		for(int i = 0; i < this.lasers.size(); ++i) 
			this.lasers.get(i).writeToPacket(buffer);
	}
	
	public static class Handler extends AbstractClientMessageHandler<ReflectorMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage handleClientMessage(EntityPlayer player, ReflectorMessage message, MessageContext ctx) {
			World world = player.worldObj;
			TileEntity tileEntity = world.getTileEntity(message.x, message.y, message.z);
			
			if(!(tileEntity instanceof TileEntityReflector)) 
				return null;
			TileEntityReflector reflector = (TileEntityReflector)tileEntity;
			reflector.closedSides = message.closedSides;
			reflector.lasers = message.lasers;
			world.markBlockForUpdate(message.x, message.y, message.z);
			return null;
		}
	}
}
