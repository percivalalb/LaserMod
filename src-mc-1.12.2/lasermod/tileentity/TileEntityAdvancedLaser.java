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
import lasermod.block.BlockBasicLaser;
import lasermod.helper.ClientHelper;
import lasermod.network.PacketDispatcher;
import lasermod.network.packet.client.AdvancedLaserMessage;
import lasermod.util.BlockActionPos;
import lasermod.util.LaserUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class TileEntityAdvancedLaser extends TileEntityLaserDevice implements ILaserProvider {

	public ArrayList<ItemStack> upgrades = new ArrayList<ItemStack>();
	
	@Override
	public void updateLasers(boolean client) {
		if(!client) {
			IBlockState state = this.getWorld().getBlockState(this.pos);
			
			if(state.getValue(BlockBasicLaser.POWERED)) {
				EnumFacing facing = state.getValue(BlockBasicLaser.FACING);
				
				BlockActionPos reciver = LaserUtil.getFirstBlock(this, facing);
		    	if(reciver != null && reciver.isLaserReceiver(facing)) {
		    		  	
		    		LaserInGame laserInGame = this.getOutputLaser(facing);
		
		    		if(reciver.getLaserReceiver(facing).canPassOnSide(this.world, this.pos, facing.getOpposite(), laserInGame))
		    			reciver.getLaserReceiver(facing).passLaser(this.world, this.pos, facing.getOpposite(), laserInGame);
		    	}
		    	else if(reciver != null) {
		    		LaserInGame laserInGame = this.getOutputLaser(facing);
		    		
		    		if(laserInGame != null)
			    		for(ILaser laser : laserInGame.getLaserType())
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
	
	//TODO
	/**
	@Override
	public Packet getDescriptionPacket() {
	    return PacketDispatcher.getPacket(new AdvancedLaserMessage(this));
	}**/
	
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
		return this.world;
	}
	
	@Override
	public boolean isSendingSignalFromSide(World world, BlockPos askerPos, EnumFacing side) {
		IBlockState state = this.getWorld().getBlockState(this.pos);
		
		return state.getValue(BlockBasicLaser.POWERED) && state.getValue(BlockBasicLaser.FACING) == side;
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
		IBlockState state = this.getWorld().getBlockState(this.pos);
		
		if(state.getValue(BlockBasicLaser.POWERED))
			return Arrays.asList(this.getOutputLaser(state.getValue(BlockBasicLaser.FACING)));
		return Arrays.asList();
	}
	
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
