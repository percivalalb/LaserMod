package lasermod.network.packet.client;

import java.util.ArrayList;

import lasermod.api.LaserInGame;
import lasermod.network.AbstractMessage.AbstractClientMessage;
import lasermod.network.IPacket;
import lasermod.tileentity.TileEntityReflector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class ReflectorMessage extends AbstractClientMessage<ReflectorMessage> implements IPacket<ReflectorMessage> {
	
	public BlockPos pos;
	public boolean[] closedSides;
	public ArrayList<LaserInGame> lasers;
	
	public ReflectorMessage() {}
	public ReflectorMessage(TileEntityReflector reflector) {
		this.pos = reflector.getPos();
	    this.closedSides = reflector.sideClosed;
	    this.lasers = reflector.lasers;
	}
	
	@Override
	public ReflectorMessage decode(PacketBuffer buf) {
		this.pos = buf.readBlockPos();
		this.closedSides = new boolean[6];
	    for(int i = 0; i < 6; ++i)
	    	this.closedSides[i] = buf.readBoolean();
		

	    this.lasers = new ArrayList<LaserInGame>();
	    int count = buf.readInt();
	    for(int i = 0; i < count; ++i)
	    	this.lasers.add(LaserInGame.readFromPacket(buf));
	    return this;
	}
	
	@Override
	public void encode(ReflectorMessage msg, PacketBuffer buf) {
		buf.writeBlockPos(msg.pos);
		
		for(int i = 0; i < 6; ++i)
			buf.writeBoolean(msg.closedSides[i]);
		
		buf.writeInt(msg.lasers.size());
		
		for(int i = 0; i < msg.lasers.size(); ++i) 
			msg.lasers.get(i).writeToPacket(buf);	
	}
	
	@Override
	public void handle(ReflectorMessage msg, EntityPlayer player) {
		World world = player.world;
		TileEntity tileEntity = world.getTileEntity(msg.pos);
		
		if(!(tileEntity instanceof TileEntityReflector)) 
			return;
		TileEntityReflector reflector = (TileEntityReflector)tileEntity;
		reflector.sideClosed = msg.closedSides;
		reflector.lasers = msg.lasers;
		world.markAndNotifyBlock(msg.pos, world.getChunk(msg.pos), world.getBlockState(msg.pos), world.getBlockState(msg.pos), 3);
		
	}
}
