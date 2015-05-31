package lasermod.network.packet.client;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;

import lasermod.network.AbstractClientMessageHandler;
import lasermod.tileentity.TileEntityAdvancedLaser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AdvancedLaserMessage implements IMessage {
	
	public int x, y, z;
	public ArrayList<ItemStack> upgrades;
	
	public AdvancedLaserMessage() {}
    public AdvancedLaserMessage(TileEntityAdvancedLaser advancedLaser) {
        this.x = advancedLaser.xCoord;
        this.y = advancedLaser.yCoord;
        this.z = advancedLaser.zCoord;
        this.upgrades = advancedLaser.upgrades;
    }
    
	@Override
	public void fromBytes(ByteBuf buffer) {
		this.x = buffer.readInt();
		this.y = buffer.readInt();
		this.z = buffer.readInt();
		this.upgrades = new ArrayList<ItemStack>();
	    int upgradeCount = buffer.readInt();
	    for(int i = 0; i < upgradeCount; ++i)
	    	upgrades.add(ByteBufUtils.readItemStack(buffer));
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeInt(this.x);
		buffer.writeInt(this.y);
		buffer.writeInt(this.z);
		buffer.writeInt(this.upgrades.size());
	    for(int i = 0; i < this.upgrades.size(); ++i) {
	    	ByteBufUtils.writeItemStack(buffer, this.upgrades.get(i));
	    }
		//FMLLog.info("test");
	}
	
	public static class Handler extends AbstractClientMessageHandler<AdvancedLaserMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage handleClientMessage(EntityPlayer player, AdvancedLaserMessage message, MessageContext ctx) {
			World world = player.worldObj;
			TileEntity tileEntity = world.getTileEntity(message.x, message.y, message.z);
			
			if(!(tileEntity instanceof TileEntityAdvancedLaser)) 
				return null;
			FMLLog.info("testtawe");
			TileEntityAdvancedLaser advancedLaser = (TileEntityAdvancedLaser)tileEntity;
			advancedLaser.upgrades = message.upgrades;
			return null;
		}
	}
}
