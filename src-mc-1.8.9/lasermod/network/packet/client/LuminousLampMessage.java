package lasermod.network.packet.client;

import java.io.IOException;
import java.util.ArrayList;

import lasermod.api.LaserInGame;
import lasermod.network.AbstractMessage.AbstractClientMessage;
import lasermod.tileentity.TileEntityLuminousLamp;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author ProPercivalalb
 */
public class LuminousLampMessage extends AbstractClientMessage {
	
	public BlockPos pos;
	public ArrayList<LaserInGame> lasers;
	
	public LuminousLampMessage() {}
	public LuminousLampMessage(TileEntityLuminousLamp luminousPanel) {
		this.pos = luminousPanel.getPos();
	    this.lasers = luminousPanel.lasers;
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
		World world = player.worldObj;
		TileEntity tileEntity = world.getTileEntity(this.pos);
		
		if(!(tileEntity instanceof TileEntityLuminousLamp)) 
			return;
		TileEntityLuminousLamp colourConverter = (TileEntityLuminousLamp)tileEntity;
		colourConverter.lasers = this.lasers;
		colourConverter.setUpdateRequired();
		world.markBlockForUpdate(this.pos);
		world.checkLightFor(EnumSkyBlock.BLOCK, this.pos);
		
	}
}
