package lasermod.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import lasermod.api.LaserInGame;
import lasermod.helper.PacketHelper;
import lasermod.network.IPacket;
import lasermod.tileentity.TileEntityAdvancedLaser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
	public void read(DataInputStream data) throws IOException {
		this.x = data.readInt();
		this.y = data.readInt();
		this.z = data.readInt();
		this.upgrades = new ArrayList<ItemStack>();
	    int upgradeCount = data.readInt();
	    for(int i = 0; i < upgradeCount; ++i)
	    	upgrades.add(PacketHelper.readItemStack(data));
	}
	
	@Override
	public void write(DataOutputStream data) throws IOException {
		data.writeInt(this.x);
		data.writeInt(this.y);
		data.writeInt(this.z);
		data.writeInt(this.upgrades.size());
	    for(int i = 0; i < this.upgrades.size(); ++i) {
	    	PacketHelper.writeItemStack(this.upgrades.get(i), data);
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
