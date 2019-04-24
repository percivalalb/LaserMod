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
public class LaserDetectorMessage extends AbstractClientMessage {
	
	public BlockPos pos;
	public ArrayList<LaserInGame> lasers;
	
	public LaserDetectorMessage() {}
	public LaserDetectorMessage(TileEntityLaserDetector detector) {
	    this.pos = detector.getPos();
	    this.lasers = detector.lasers;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.pos = buffer.readBlockPos();

	    this.lasers = new ArrayList<LaserInGame>();
	    int count = buffer.readInt();
	    for(int i = 0; i < count; ++i)
	    	this.lasers.add(new LaserInGame().readFromPacket(buffer));
		
	}
	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(this.pos);
		
		buffer.writeInt(this.lasers.size());
		
		for(int i = 0; i < this.lasers.size(); ++i) 
			this.lasers.get(i).writeToPacket(buffer);
		
	}
	@Override
	public void process(EntityPlayer player, Side side) {
		World world = player.world;
		TileEntity tileEntity = world.getTileEntity(this.pos);
		
		if(!(tileEntity instanceof TileEntityLaserDetector)) 
			return;
		
		TileEntityLaserDetector colourConverter = (TileEntityLaserDetector)tileEntity;
		colourConverter.lasers = this.lasers;
		colourConverter.setUpdateRequired();
		world.markAndNotifyBlock(this.pos, world.getChunk(this.pos), world.getBlockState(this.pos), world.getBlockState(this.pos), 2);
		
	}
}
