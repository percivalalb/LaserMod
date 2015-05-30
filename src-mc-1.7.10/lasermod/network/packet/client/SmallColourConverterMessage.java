package lasermod.network.packet.client;

import io.netty.buffer.ByteBuf;
import lasermod.api.LaserInGame;
import lasermod.network.AbstractClientMessageHandler;
import lasermod.tileentity.TileEntitySmallColourConverter;
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
public class SmallColourConverterMessage implements IMessage {
	
	public int x, y, z;
    public LaserInGame laser;
    public int colour;
	
    public SmallColourConverterMessage() {}
    public SmallColourConverterMessage(TileEntitySmallColourConverter colourConverter) {
        this.x = colourConverter.xCoord;
        this.y = colourConverter.yCoord;
        this.z = colourConverter.zCoord;
        this.laser = colourConverter.getOutputLaser(colourConverter.getBlockMetadata());
        this.colour = colourConverter.colour;
    }
    
    
	@Override
	public void fromBytes(ByteBuf buffer) {
		this.x = buffer.readInt();
		this.y = buffer.readInt();
		this.z = buffer.readInt();
		if(buffer.readBoolean())
			this.laser = new LaserInGame().readFromPacket(buffer);
		this.colour = buffer.readInt();
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeInt(this.x);
		buffer.writeInt(this.y);
		buffer.writeInt(this.z);
		buffer.writeBoolean(this.laser != null);
		if(this.laser != null)
			this.laser.writeToPacket(buffer);
		buffer.writeInt(this.colour);
	}
	
	public static class Handler extends AbstractClientMessageHandler<SmallColourConverterMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage handleClientMessage(EntityPlayer player, SmallColourConverterMessage message, MessageContext ctx) {
			World world = player.worldObj;
			TileEntity tileEntity = world.getTileEntity(message.x, message.y, message.z);
			
			if(!(tileEntity instanceof TileEntitySmallColourConverter)) 
				return null;
			TileEntitySmallColourConverter colourConverter = (TileEntitySmallColourConverter)tileEntity;
			colourConverter.setLaser(message.laser);
			colourConverter.colour = message.colour;
			world.markBlockForUpdate(message.x, message.y, message.z);
			return null;
		}
	}
}
