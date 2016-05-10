package lasermod.network.packet.client;

import java.io.IOException;
import java.util.ArrayList;

import lasermod.network.AbstractMessage.AbstractClientMessage;
import lasermod.tileentity.TileEntityAdvancedLaser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class AdvancedLaserMessage extends AbstractClientMessage {
	
	public BlockPos pos;
	public ArrayList<ItemStack> upgrades;
	
	public AdvancedLaserMessage() {}
    public AdvancedLaserMessage(TileEntityAdvancedLaser advancedLaser) {
        this.pos = advancedLaser.getPos();
        this.upgrades = advancedLaser.upgrades;
    }

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.pos = buffer.readBlockPos();
		this.upgrades = new ArrayList<ItemStack>();
	    int upgradeCount = buffer.readInt();
	    for(int i = 0; i < upgradeCount; ++i)
	    	upgrades.add(ByteBufUtils.readItemStack(buffer));
		
	}
	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(this.pos);
		buffer.writeInt(this.upgrades.size());
	    for(int i = 0; i < this.upgrades.size(); ++i)
	    	ByteBufUtils.writeItemStack(buffer, this.upgrades.get(i));
	
		
	}
	@Override
	public void process(EntityPlayer player, Side side) {
		World world = player.worldObj;
		TileEntity tileEntity = world.getTileEntity(this.pos);
		
		if(!(tileEntity instanceof TileEntityAdvancedLaser)) 
			return;
		
		TileEntityAdvancedLaser advancedLaser = (TileEntityAdvancedLaser)tileEntity;
		advancedLaser.upgrades = this.upgrades;
		
	}
}
