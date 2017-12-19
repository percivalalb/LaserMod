package lasermod.network.packet.client;

import java.io.IOException;

import lasermod.api.LaserInGame;
import lasermod.network.AbstractMessage.AbstractClientMessage;
import lasermod.tileentity.TileEntityColourConverter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author ProPercivalalb
 */
public class ColourConverterMessage extends AbstractClientMessage {
	
	public BlockPos pos;
    public LaserInGame laser;
    public int colour;
	
    public ColourConverterMessage() {}
    public ColourConverterMessage(TileEntityColourConverter colourConverter) {
        this.pos = colourConverter.getPos();
        this.laser = colourConverter.laser;
        this.colour = colourConverter.colour;
    }

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.pos = buffer.readBlockPos();
	
		if(buffer.readBoolean())
			this.laser = new LaserInGame().readFromPacket(buffer);
		this.colour = buffer.readInt();
		
	}
	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(this.pos);
		buffer.writeBoolean(this.laser != null);
		if(this.laser != null)
			this.laser.writeToPacket(buffer);
		buffer.writeInt(this.colour);
		
	}
	@Override
	public void process(EntityPlayer player, Side side) {
		World world = player.world;
		TileEntity tileEntity = world.getTileEntity(this.pos);
		
		if(!(tileEntity instanceof TileEntityColourConverter)) 
			return;
		TileEntityColourConverter colourConverter = (TileEntityColourConverter)tileEntity;
		colourConverter.laser = this.laser;
		colourConverter.colour = this.colour;
		world.markAndNotifyBlock(this.pos, world.getChunkFromBlockCoords(this.pos), world.getBlockState(this.pos), world.getBlockState(this.pos), 3);
		
	}
}
