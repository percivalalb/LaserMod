package lasermod.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import lasermod.api.LaserInGame;
import lasermod.network.IPacket;
import lasermod.tileentity.TileEntityAdvancedLaser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class PacketAdvancedLaser extends IPacket {

	public int x, y, z;
	public ArrayList<ItemStack> upgrades;
    
    public PacketAdvancedLaser() {}
    public PacketAdvancedLaser(TileEntityAdvancedLaser advancedLaser) {
        this.x = advancedLaser.xCoord;
        this.y = advancedLaser.yCoord;
        this.z = advancedLaser.zCoord;
        this.upgrades = advancedLaser.upgrades;
    }
    
	@Override
	public void read(PacketBuffer packetbuffer) throws IOException {
		this.x = packetbuffer.readInt();
		this.y = packetbuffer.readInt();
		this.z = packetbuffer.readInt();
		this.upgrades = new ArrayList<ItemStack>();
	    int upgradeCount = packetbuffer.readInt();
	    for(int i = 0; i < upgradeCount; ++i)
	    	upgrades.add(packetbuffer.readItemStackFromBuffer());
	}
	
	@Override
	public void write(PacketBuffer packetbuffer) throws IOException {
		packetbuffer.writeInt(this.x);
		packetbuffer.writeInt(this.y);
		packetbuffer.writeInt(this.z);
		packetbuffer.writeInt(this.upgrades.size());
	    for(int i = 0; i < this.upgrades.size(); ++i) {
	    	packetbuffer.writeItemStackToBuffer(this.upgrades.get(i));
	    }
	}
	
	@Override
	public void execute(EntityPlayer player) {
		World world = player.worldObj;
		TileEntity tileEntity = world.getTileEntity(this.x, this.y, this.z);
		
		if(!(tileEntity instanceof TileEntityAdvancedLaser)) 
			return;
		
		TileEntityAdvancedLaser advancedLaser = (TileEntityAdvancedLaser)tileEntity;
		advancedLaser.upgrades = this.upgrades;
	}
}
