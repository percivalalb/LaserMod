package lasermod.network.packet.client;

import java.util.ArrayList;

import lasermod.api.LaserInGame;
import lasermod.network.AbstractMessage.AbstractClientMessage;
import lasermod.network.IPacket;
import lasermod.tileentity.TileEntityLuminousLamp;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class LuminousLampMessage extends AbstractClientMessage<LuminousLampMessage> implements IPacket<LuminousLampMessage> {
	
	public BlockPos pos;
	public ArrayList<LaserInGame> lasers;
	
	public LuminousLampMessage() {}
	public LuminousLampMessage(TileEntityLuminousLamp luminousPanel) {
		this.pos = luminousPanel.getPos();
	    this.lasers = luminousPanel.lasers;
	}

	@Override
	public LuminousLampMessage decode(PacketBuffer buf) {
		this.pos = buf.readBlockPos();

	    this.lasers = new ArrayList<LaserInGame>();
	    int count = buf.readInt();
	    for(int i = 0; i < count; ++i)
	    	this.lasers.add(LaserInGame.readFromPacket(buf));
	    return this;
		
	}
	
	@Override
	public void encode(LuminousLampMessage msg, PacketBuffer buf) {
		buf.writeBlockPos(msg.pos);
		
		buf.writeInt(msg.lasers.size());
		
		for(int i = 0; i < msg.lasers.size(); ++i) 
			msg.lasers.get(i).writeToPacket(buf);
		
	}
	
	@Override
	public void handle(LuminousLampMessage msg, EntityPlayer player) {
		World world = player.world;
		TileEntity tileEntity = world.getTileEntity(msg.pos);
		
		if(!(tileEntity instanceof TileEntityLuminousLamp)) 
			return;
		TileEntityLuminousLamp colourConverter = (TileEntityLuminousLamp)tileEntity;
		colourConverter.lasers = msg.lasers;
		colourConverter.setUpdateRequired();
		world.markAndNotifyBlock(this.pos, world.getChunkFromBlockCoords(msg.pos), world.getBlockState(msg.pos), world.getBlockState(msg.pos), 2);
		world.checkLightFor(EnumSkyBlock.BLOCK, msg.pos);
		
	}
}
