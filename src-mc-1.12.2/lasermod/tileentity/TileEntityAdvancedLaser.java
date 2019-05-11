package lasermod.tileentity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lasermod.LaserMod;
import lasermod.api.ILaserProvider;
import lasermod.api.LaserInGame;
import lasermod.api.LaserRegistry;
import lasermod.api.LaserType;
import lasermod.api.base.TileEntityLaserDevice;
import lasermod.block.BlockBasicLaser;
import lasermod.block.BlockPoweredRedstone;
import lasermod.util.BlockActionPos;
import lasermod.util.LaserUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class TileEntityAdvancedLaser extends TileEntityLaserDevice implements ILaserProvider {

	public ArrayList<ItemStack> upgrades = new ArrayList<ItemStack>();
	
	@Override
	public void updateLasers(boolean client) {
		if(!client) {
			IBlockState state = this.getWorld().getBlockState(this.pos);
			
			if(state.getValue(BlockPoweredRedstone.POWERED)) {
				EnumFacing facing = state.getValue(BlockBasicLaser.FACING);
				
				BlockActionPos reciver = LaserUtil.getFirstBlock(this, facing);
		    	if(reciver != null && reciver.isLaserReceiver(facing)) {
		    		  	
		    		LaserInGame laserInGame = this.getOutputLaser(facing);
		
		    		if(reciver.getLaserReceiver(facing).canReceive(this.world, this.pos, facing.getOpposite(), laserInGame))
		    			reciver.getLaserReceiver(facing).onLaserIncident(this.world, this.pos, facing.getOpposite(), laserInGame);
		    	}
		    	else if(reciver != null) {
		    		LaserInGame laserInGame = this.getOutputLaser(facing);
		    		LaserMod.LOGGER.debug("TEST");
		    		if(laserInGame != null)
			    		for(LaserType laser : laserInGame.getLaserType())
			    			laser.actionOnBlock(reciver);
		
		    	}
			}
		}
	}
	
	@Override
	public void updateLaserAction(boolean client) {
		IBlockState state = this.getWorld().getBlockState(this.pos);
		
		if(state.getValue(BlockBasicLaser.POWERED))
			LaserUtil.performLaserAction(this, state.getValue(BlockBasicLaser.FACING), this.pos);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		
		NBTTagList itemList = tag.getTagList("upgrades", 10);
		for(int i = 0; i < itemList.tagCount(); ++i)
			this.upgrades.add(new ItemStack(itemList.getCompoundTagAt(i)));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		
		NBTTagList itemList = new NBTTagList();
		for(int i = 0; i < this.upgrades.size(); ++i) {
			NBTTagCompound itemTag = new NBTTagCompound();
			this.upgrades.get(i).writeToNBT(itemTag);
			itemList.appendTag(itemTag);
		}
		tag.setTag("upgrades", itemList);
		
		return tag;
	}
	
	@Override
	public LaserInGame getOutputLaser(EnumFacing dir) {
		LaserInGame laser = new LaserInGame().setDirection(dir);

		for(ItemStack stack : this.upgrades) {
			LaserType type = LaserRegistry.getLaserFromItem(stack);
			if(type != null)
				laser.addLaserType(type);
		}
		return laser;
	}

	@Override
	public World getWorld() {
		return this.world;
	}
	
	@Override
	public boolean isEmittingFromSide(World world, BlockPos askerPos, EnumFacing side) {
		IBlockState state = this.getWorld().getBlockState(this.pos);
		
		return state.getValue(BlockBasicLaser.POWERED) && state.getValue(BlockBasicLaser.FACING) == side;
	}
	
	@Override
	public int getRange(EnumFacing dir) {
		return 64;
	}
	
	@Override
	public boolean isForgeMultipart() {
		return false;
	}
	
	@Override
	public List<LaserInGame> getOutputLasers() {
		IBlockState state = this.getWorld().getBlockState(this.pos);
		
		if(state.getValue(BlockBasicLaser.POWERED))
			return Arrays.asList(this.getOutputLaser(state.getValue(BlockBasicLaser.FACING)));
		
		return Collections.<LaserInGame>emptyList();
	}
	
	//@Override
   // public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
   //     return oldState.getBlock() != newState.getBlock();
    //}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, -1, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
    }
}
