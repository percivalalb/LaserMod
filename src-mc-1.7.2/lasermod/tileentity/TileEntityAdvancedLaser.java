package lasermod.tileentity;

import java.util.ArrayList;

import lasermod.api.ILaserProvider;
import lasermod.api.ILaserReciver;
import lasermod.api.LaserInGame;
import lasermod.api.LaserRegistry;
import lasermod.network.packet.PacketAdvancedLaser;
import lasermod.util.LaserUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
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
		if(this.worldObj.isRemote) return;
		
		this.lagReduce += 1;
		if(this.lagReduce % LaserUtil.TICK_RATE != 0) return;
		
		ILaserReciver reciver = LaserUtil.getFirstReciver(this, this.getBlockMetadata());
		if(reciver != null) {
		  	boolean hasSignal = (this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord));
		  	
		  	LaserInGame laserInGame = this.getOutputLaser(this.getBlockMetadata());
		  	if(!hasSignal) {
		  		reciver.removeLasersFromSide(this.worldObj, this.xCoord, this.yCoord, this.zCoord, Facing.oppositeSide[this.getBlockMetadata()]);
		  	}
		  	else if(reciver.canPassOnSide(this.worldObj, this.xCoord, this.yCoord, this.zCoord, Facing.oppositeSide[this.getBlockMetadata()], laserInGame)) {
				reciver.passLaser(this.worldObj, this.xCoord, this.yCoord, this.zCoord, Facing.oppositeSide[this.getBlockMetadata()], laserInGame);
			}
		}
		this.lagReduce += 1;
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
		return new LaserInGame(LaserRegistry.getLaserFromId("default")).setSide(Facing.oppositeSide[side]);
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
