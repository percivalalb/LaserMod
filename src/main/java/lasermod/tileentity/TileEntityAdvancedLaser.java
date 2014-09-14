package lasermod.tileentity;

import java.util.ArrayList;
import java.util.List;

import lasermod.ModBlocks;
import lasermod.api.ILaser;
import lasermod.api.ILaserProvider;
import lasermod.api.ILaserReciver;
import lasermod.api.LaserInGame;
import lasermod.api.LaserRegistry;
import lasermod.network.packet.PacketAdvancedLaser;
import lasermod.util.LaserUtil;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class TileEntityAdvancedLaser extends TileEntityLaserDevice implements ILaserProvider {

	private int lagReduce = -1;
	public ArrayList<ItemStack> upgrades = new ArrayList<ItemStack>();
	
	@Override
	public void updateEntity() {
		this.lagReduce += 1;
		if(this.lagReduce % LaserUtil.TICK_RATE == 0) {
			this.worldObj.scheduleBlockUpdate(this.xCoord, this.yCoord, this.zCoord, ModBlocks.advancedLaser, 0);
		}
		if(this.lagReduce % LaserUtil.LASER_RATE == 0) {
			boolean hasSignal = (this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord));
		
			if(hasSignal)
				LaserUtil.performLaserAction(this, this.getBlockMetadata(), this.xCoord, this.yCoord, this.zCoord);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		
		NBTTagList itemList = tag.getTagList("upgrades", 10);
		for(int i = 0; i < itemList.tagCount(); ++i)
			this.upgrades.add(ItemStack.loadItemStackFromNBT(itemList.getCompoundTagAt(i)));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		
		NBTTagList itemList = new NBTTagList();
		for(int i = 0; i < this.upgrades.size(); ++i) {
			NBTTagCompound itemTag = new NBTTagCompound();
			this.upgrades.get(i).writeToNBT(itemTag);
			itemList.appendTag(itemTag);
		}
		tag.setTag("upgrades", itemList);
	}
	
	@Override
	public Packet getDescriptionPacket() {
	    return new PacketAdvancedLaser(this).getPacket();
	}
	
	@Override
	public LaserInGame getOutputLaser(int side) {
		LaserInGame laser = new LaserInGame(LaserRegistry.getLaserFromId("default")).setSide(Facing.oppositeSide[side]);

		for(ItemStack stack : this.upgrades) {
			ILaser ilaser = LaserRegistry.getLaserFromItem(stack);
			if(laser != null)
				laser.addLaserType(ilaser);
		}
		return laser;
	}
	
	@Override
	public int getX() { return this.xCoord; }
	@Override
	public int getY() { return this.yCoord; }
	@Override
	public int getZ() { return this.zCoord; }

	@Override
	public World getWorld() {
		return this.worldObj;
	}
	
	@Override
	public boolean isSendingSignalFromSide(World world, int askerX, int askerY, int askerZ, int side) {
		return this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord) && this.getBlockMetadata() == side;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
    	return INFINITE_EXTENT_AABB;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }
}
