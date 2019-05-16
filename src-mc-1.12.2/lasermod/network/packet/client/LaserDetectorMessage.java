package lasermod.network.packet.client;

import java.io.IOException;
import java.util.ArrayList;

import lasermod.api.LaserInGame;
import lasermod.network.AbstractMessage.AbstractClientMessage;
import lasermod.tileentity.TileEntityLaserDetector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author ProPercivalalb
 */
public class LaserDetectorMessage extends AbstractClientMessage<LaserDetectorMessage> {
	
	public BlockPos pos;
	public ArrayList<LaserInGame> lasers;
	
	public LaserDetectorMessage() {}
	public LaserDetectorMessage(TileEntityLaserDetector detector) {
	    this.pos = detector.getPos();
	    this.lasers = detector.lasers;
	}

	@Override
	protected LaserDetectorMessage encode(PacketBuffer buffer) throws IOException {
		this.pos = buffer.readBlockPos();

	    this.lasers = new ArrayList<LaserInGame>();
	    int count = buffer.readInt();
	    for(int i = 0; i < count; ++i)
	    	this.lasers.add(LaserInGame.readFromPacket(buffer));
	    return this;
		
	}
	
	@Override
	protected void decode(LaserDetectorMessage msg, PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(msg.pos);
		
		buffer.writeInt(msg.lasers.size());
		
		for(int i = 0; i < msg.lasers.size(); ++i) 
			msg.lasers.get(i).writeToPacket(buffer);
		
	}
	@Override
	public void process(LaserDetectorMessage msg, EntityPlayer player, Side side) {
		World world = player.world;
		TileEntity tileEntity = world.getTileEntity(msg.pos);
		
		if(!(tileEntity instanceof TileEntityLaserDetector)) 
			return;
		
		TileEntityLaserDetector colourConverter = (TileEntityLaserDetector)tileEntity;
		colourConverter.lasers = msg.lasers;
		colourConverter.setUpdateRequired();
		world.markAndNotifyBlock(msg.pos, world.getChunk(msg.pos), world.getBlockState(msg.pos), world.getBlockState(msg.pos), 2);
		
	}
}
