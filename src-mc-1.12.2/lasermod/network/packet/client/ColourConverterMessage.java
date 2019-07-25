package lasermod.network.packet.client;

import lasermod.api.LaserInGame;
import lasermod.network.AbstractMessage.AbstractClientMessage;
import lasermod.network.IPacket;
import lasermod.tileentity.TileEntityColourConverter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class ColourConverterMessage extends AbstractClientMessage<ColourConverterMessage> implements IPacket<ColourConverterMessage> {
	
	public BlockPos pos;
    public LaserInGame laser;
    public EnumDyeColor colour;
	
    public ColourConverterMessage() {}
    public ColourConverterMessage(TileEntityColourConverter colourConverter) {
        this.pos = colourConverter.getPos();
        this.laser = colourConverter.laser;
        this.colour = colourConverter.colour;
    }

	@Override
	public ColourConverterMessage decode(PacketBuffer buffer) {
		this.pos = buffer.readBlockPos();
	
		if(buffer.readBoolean())
			this.laser = LaserInGame.readFromPacket(buffer);
		this.colour = EnumDyeColor.byMetadata(buffer.readInt());
		return this;
		
	}
	
	@Override
	public void encode(ColourConverterMessage msg, PacketBuffer buffer) {
		buffer.writeBlockPos(msg.pos);
		buffer.writeBoolean(msg.laser != null);
		if(msg.laser != null)
			msg.laser.writeToPacket(buffer);
		buffer.writeInt(msg.colour.getMetadata());
		
	}
	
	@Override
	public void handle(ColourConverterMessage msg, EntityPlayer player) {
		World world = player.world;
		TileEntity tileEntity = world.getTileEntity(msg.pos);
		
		if(!(tileEntity instanceof TileEntityColourConverter)) 
			return;
		TileEntityColourConverter colourConverter = (TileEntityColourConverter)tileEntity;
		colourConverter.laser = msg.laser;
		colourConverter.colour = msg.colour;
		world.markAndNotifyBlock(msg.pos, world.getChunk(msg.pos), world.getBlockState(msg.pos), world.getBlockState(msg.pos), 3);
		
	}
}
