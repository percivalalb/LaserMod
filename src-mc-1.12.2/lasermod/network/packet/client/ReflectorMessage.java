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
public class ReflectorMessage extends AbstractClientMessage {
	
	public BlockPos pos;
	public boolean[] closedSides;
	public ArrayList<LaserInGame> lasers;
	
	public ReflectorMessage() {}
	public ReflectorMessage(TileEntityReflector reflector) {
		this.pos = reflector.getPos();
	    this.closedSides = reflector.closedSides;
	    this.lasers = reflector.lasers;
	}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.pos = buffer.readBlockPos();
		this.closedSides = new boolean[6];
	    for(int i = 0; i < 6; ++i)
	    	this.closedSides[i] = buffer.readBoolean();
		

	    this.lasers = new ArrayList<LaserInGame>();
	    int count = buffer.readInt();
	    for(int i = 0; i < count; ++i)
	    	this.lasers.add(new LaserInGame().readFromPacket(buffer));
		
	}
	
	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(this.pos);
		
		for(int i = 0; i < 6; ++i)
			buffer.writeBoolean(this.closedSides[i]);
		
		buffer.writeInt(this.lasers.size());
		
		for(int i = 0; i < this.lasers.size(); ++i) 
			this.lasers.get(i).writeToPacket(buffer);	
	}
	
	@Override
	public void process(EntityPlayer player, Side side) {
		World world = player.world;
		TileEntity tileEntity = world.getTileEntity(this.pos);
		
		if(!(tileEntity instanceof TileEntityReflector)) 
			return;
		TileEntityReflector reflector = (TileEntityReflector)tileEntity;
		reflector.closedSides = this.closedSides;
		reflector.lasers = this.lasers;
		world.markAndNotifyBlock(this.pos, world.getChunk(this.pos), world.getBlockState(this.pos), world.getBlockState(this.pos), 3);
		
	}
}
