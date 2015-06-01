package lasermod.tileentity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lasermod.ModBlocks;
import lasermod.api.ILaser;
import lasermod.api.ILaserProvider;
import lasermod.api.LaserInGame;
import lasermod.api.LaserRegistry;
import lasermod.api.base.TileEntityLaserDevice;
import lasermod.network.PacketDispatcher;
import lasermod.network.packet.client.AdvancedLaserMessage;
import lasermod.util.BlockActionPos;
import lasermod.util.LaserUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author ProPercivalalb
 */
public class TileEntityAdvancedLaser extends TileEntityLaserDevice implements ILaserProvider {

	public ArrayList<ItemStack> upgrades = new ArrayList<ItemStack>();
	
	@Override
	public void updateLasers(boolean client) {
		if(!client) {
			if(this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord)) {
				BlockActionPos reciver = LaserUtil.getFirstBlock(this, ForgeDirection.getOrientation(this.getBlockMetadata()));
		    	if(reciver != null && reciver.isLaserReceiver(ForgeDirection.getOrientation(this.getBlockMetadata()))) {
		    		  	
		    		LaserInGame laserInGame = this.getOutputLaser(ForgeDirection.getOrientation(this.getBlockMetadata()));
		
		    		if(reciver.getLaserReceiver(ForgeDirection.getOrientation(this.getBlockMetadata())).canPassOnSide(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite(), laserInGame))
		    			reciver.getLaserReceiver(ForgeDirection.getOrientation(this.getBlockMetadata())).passLaser(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite(), laserInGame);
		    	}
		    	else if(reciver != null) {
		    		LaserInGame laserInGame = this.getOutputLaser(ForgeDirection.getOrientation(this.getBlockMetadata()));
		    		
		    		if(laserInGame != null)
			    		for(ILaser laser : laserInGame.getLaserType())
			    			laser.actionOnBlock(reciver);
		
		    	}
			}
		}
	}
	
	@Override
	public void updateLaserAction(boolean client) {
		boolean hasSignal = (this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord));
		
		if(hasSignal)
			LaserUtil.performLaserAction(this, ForgeDirection.getOrientation(this.getBlockMetadata()), this.xCoord, this.yCoord, this.zCoord);
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
	    return PacketDispatcher.getPacket(new AdvancedLaserMessage(this));
	}
	
	@Override
	public LaserInGame getOutputLaser(ForgeDirection dir) {
		LaserInGame laser = new LaserInGame().setDirection(dir.getOpposite());

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
	public boolean isSendingSignalFromSide(World world, int askerX, int askerY, int askerZ, ForgeDirection dir) {
		return this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord) && this.getBlockMetadata() == dir.ordinal();
	}
	
	@Override
	public int getDistance(ForgeDirection dir) {
		return 64;
	}
	
	@Override
	public boolean isForgeMultipart() {
		return false;
	}
	
	@Override
	public List<LaserInGame> getOutputLasers() {
		boolean hasSignal = (this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord));
		if(hasSignal)
			return Arrays.asList(this.getOutputLaser(ForgeDirection.getOrientation(this.getBlockMetadata())));
		return Arrays.asList();
	}
}
