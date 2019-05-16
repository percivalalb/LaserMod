package lasermod.network.packet.client;

import java.io.IOException;

import lasermod.api.LaserInGame;
import lasermod.network.AbstractMessage.AbstractClientMessage;
import lasermod.tileentity.TileEntitySmallColourConverter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author ProPercivalalb
 */
public class SmallColourConverterMessage extends AbstractClientMessage<SmallColourConverterMessage> {
	
	public BlockPos pos;
    public LaserInGame laser;
    public int colour;
	
    public SmallColourConverterMessage() {}
    public SmallColourConverterMessage(TileEntitySmallColourConverter colourConverter) {
    	this.pos = colourConverter.getPos();
        this.laser = colourConverter.laser;
        this.colour = colourConverter.colour;
    }

	@Override
	protected SmallColourConverterMessage encode(PacketBuffer buffer) throws IOException {
		this.pos = buffer.readBlockPos();
		if(buffer.readBoolean())
			this.laser = LaserInGame.readFromPacket(buffer);
		this.colour = buffer.readInt();
		return this;
		
	}
	
	@Override
	protected void decode(SmallColourConverterMessage msg, PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(msg.pos);
		buffer.writeBoolean(msg.laser != null);
		if(msg.laser != null)
			msg.laser.writeToPacket(buffer);
		buffer.writeInt(msg.colour);
		
	}
	@Override
	public void process(SmallColourConverterMessage msg, EntityPlayer player, Side side) {
		World world = player.world;
		TileEntity tileEntity = world.getTileEntity(msg.pos);
		
		if(!(tileEntity instanceof TileEntitySmallColourConverter)) 
			return;
		TileEntitySmallColourConverter colourConverter = (TileEntitySmallColourConverter)tileEntity;
		colourConverter.laser = msg.laser;
		colourConverter.colour = msg.colour;
		world.markAndNotifyBlock(msg.pos, world.getChunk(msg.pos), world.getBlockState(msg.pos), world.getBlockState(msg.pos), 2);
		
	}
}
