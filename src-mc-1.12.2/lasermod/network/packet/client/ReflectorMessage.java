package lasermod.network.packet.client;

import java.io.IOException;
import java.util.ArrayList;

import lasermod.api.LaserInGame;
import lasermod.network.AbstractMessage.AbstractClientMessage;
import lasermod.tileentity.TileEntityReflector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author ProPercivalalb
 */
public class ReflectorMessage extends AbstractClientMessage<ReflectorMessage> {
	
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
	protected ReflectorMessage encode(PacketBuffer buffer) throws IOException {
		this.pos = buffer.readBlockPos();
		this.closedSides = new boolean[6];
	    for(int i = 0; i < 6; ++i)
	    	this.closedSides[i] = buffer.readBoolean();
		

	    this.lasers = new ArrayList<LaserInGame>();
	    int count = buffer.readInt();
	    for(int i = 0; i < count; ++i)
	    	this.lasers.add(LaserInGame.readFromPacket(buffer));
	    return this;
	}
	
	@Override
	protected void decode(ReflectorMessage msg, PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(msg.pos);
		
		for(int i = 0; i < 6; ++i)
			buffer.writeBoolean(msg.closedSides[i]);
		
		buffer.writeInt(msg.lasers.size());
		
		for(int i = 0; i < msg.lasers.size(); ++i) 
			msg.lasers.get(i).writeToPacket(buffer);	
	}
	
	@Override
	public void process(ReflectorMessage msg, EntityPlayer player, Side side) {
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
