package lasermod.tileentity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lasermod.api.ILaser;
import lasermod.api.ILaserProvider;
import lasermod.api.LaserCollisionBoxes;
import lasermod.api.LaserInGame;
import lasermod.api.LaserRegistry;
import lasermod.api.LaserToRender;
import lasermod.api.base.TileEntityLaserDevice;
import lasermod.helper.ClientHelper;
import lasermod.network.PacketDispatcher;
import lasermod.network.packet.client.AdvancedLaserMessage;
import lasermod.util.BlockActionPos;
import lasermod.util.LaserUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class TileEntityAdvancedLaser extends TileEntityLaserDevice implements ILaserProvider {

	public ArrayList<ItemStack> upgrades = new ArrayList<ItemStack>();
	
	@Override
	public void updateLasers(boolean client) {
		if(!client) {
			if(this.worldObj.isBlockIndirectlyGettingPowered(this.pos) > 0) {
				BlockActionPos reciver = LaserUtil.getFirstBlock(this, EnumFacing.getFront(this.getBlockMetadata()));
		    	if(reciver != null && reciver.isLaserReceiver(EnumFacing.getFront(this.getBlockMetadata()))) {
		    		  	
		    		LaserInGame laserInGame = this.getOutputLaser(EnumFacing.getFront(this.getBlockMetadata()));
		
		    		if(reciver.getLaserReceiver(EnumFacing.getFront(this.getBlockMetadata())).canPassOnSide(this.worldObj, this.pos, EnumFacing.getFront(this.getBlockMetadata()).getOpposite(), laserInGame))
		    			reciver.getLaserReceiver(EnumFacing.getFront(this.getBlockMetadata())).passLaser(this.worldObj, this.pos, EnumFacing.getFront(this.getBlockMetadata()).getOpposite(), laserInGame);
		    	}
		    	else if(reciver != null) {
		    		LaserInGame laserInGame = this.getOutputLaser(EnumFacing.getFront(this.getBlockMetadata()));
		    		
		    		if(laserInGame != null)
			    		for(ILaser laser : laserInGame.getLaserType())
			    			laser.actionOnBlock(reciver);
		
		    	}
			}
		}
	}
	
	@Override
	public void updateLaserAction(boolean client) {
		boolean hasSignal = (this.worldObj.isBlockIndirectlyGettingPowered(this.pos) > 0);
		
		if(hasSignal)
			LaserUtil.performLaserAction(this, EnumFacing.getFront(this.getBlockMetadata()), this.pos);
	}
	
	public void applyLaserRender() {
		if(this.getWorld().isBlockIndirectlyGettingPowered(this.getPos()) == 0)
    		return;
    	
    	LaserInGame laserInGame = this.getOutputLaser(EnumFacing.getFront(this.getBlockMetadata()));
    	float alpha = laserInGame.shouldRenderLaser(ClientHelper.getPlayer());

    	if(alpha == 0.0F)
    		return;


        AxisAlignedBB laserOutline = LaserUtil.getLaserOutline(this, EnumFacing.getFront(this.getBlockMetadata()), this.getPos().getX(), this.getPos().getY(), this.getPos().getZ());
        LaserCollisionBoxes.addLaserCollision(new LaserToRender(laserInGame, laserOutline, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), this.getPos(), EnumFacing.getFront(this.getBlockMetadata()), alpha, true));
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
	public LaserInGame getOutputLaser(EnumFacing dir) {
		LaserInGame laser = new LaserInGame().setDirection(dir.getOpposite());

		for(ItemStack stack : this.upgrades) {
			ILaser ilaser = LaserRegistry.getLaserFromItem(stack);
			if(laser != null)
				laser.addLaserType(ilaser);
		}
		return laser;
	}

	@Override
	public World getWorld() {
		return this.worldObj;
	}
	
	@Override
	public boolean isSendingSignalFromSide(World world, BlockPos askerPos, EnumFacing dir) {
		return this.worldObj.isBlockIndirectlyGettingPowered(this.pos) > 0 && this.getBlockMetadata() == dir.ordinal();
	}
	
	@Override
	public int getDistance(EnumFacing dir) {
		return 64;
	}
	
	@Override
	public boolean isForgeMultipart() {
		return false;
	}
	
	@Override
	public List<LaserInGame> getOutputLasers() {
		boolean hasSignal = (this.worldObj.isBlockIndirectlyGettingPowered(this.pos) > 0);
		if(hasSignal)
			return Arrays.asList(this.getOutputLaser(EnumFacing.getFront(this.getBlockMetadata())));
		return Arrays.asList();
	}
}
