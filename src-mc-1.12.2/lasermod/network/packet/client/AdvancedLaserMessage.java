package lasermod.network.packet.client;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import lasermod.api.LaserModAPI;
import lasermod.api.LaserType;
import lasermod.network.AbstractMessage.AbstractClientMessage;
import lasermod.network.IPacket;
import lasermod.tileentity.TileEntityAdvancedLaser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AdvancedLaserMessage extends AbstractClientMessage<AdvancedLaserMessage> implements IPacket<AdvancedLaserMessage> {
	
	public BlockPos pos;
	public Set<LaserType> upgrades;
	
	public AdvancedLaserMessage() {}
    public AdvancedLaserMessage(TileEntityAdvancedLaser advancedLaser) {
        this.pos = advancedLaser.getPos();
        this.upgrades = advancedLaser.upgrades;
    }

	@Override
	public AdvancedLaserMessage decode(PacketBuffer buffer) {
		this.pos = buffer.readBlockPos();
		this.upgrades = new HashSet<>();
	    int upgradeCount = buffer.readInt();
	    for(int i = 0; i < upgradeCount; ++i)
	    	this.upgrades.add(LaserModAPI.LASER_TYPES.getValue(new ResourceLocation(buffer.readString(64))));
	    return this;
	}
	
	@Override
	public void encode(AdvancedLaserMessage msg, PacketBuffer buffer) {
		buffer.writeBlockPos(msg.pos);
		buffer.writeInt(msg.upgrades.size());
		Iterator<LaserType> lasers = msg.upgrades.iterator();
		while(lasers.hasNext()) {
			buffer.writeString(lasers.next().getRegistryName().toString());
		}
	}
	
	@Override
	public void handle(AdvancedLaserMessage msg, EntityPlayer player) {
		World world = player.world;
		TileEntity tileEntity = world.getTileEntity(msg.pos);
		
		if(!(tileEntity instanceof TileEntityAdvancedLaser)) 
			return;
		
		TileEntityAdvancedLaser advancedLaser = (TileEntityAdvancedLaser)tileEntity;
		advancedLaser.upgrades = msg.upgrades;
		
	}
}
