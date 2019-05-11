package lasermod.tileentity;

import java.util.Arrays;
import java.util.List;

import lasermod.ModBlocks;
import lasermod.api.ILaserProvider;
import lasermod.api.ILaserReceiver;
import lasermod.api.LaserInGame;
import lasermod.api.LaserType;
import lasermod.api.base.TileEntitySingleSidedReceiver;
import lasermod.block.BlockPoweredLaser;
import lasermod.block.BlockPoweredRedstone;
import lasermod.network.PacketDispatcher;
import lasermod.network.packet.client.ColourConverterMessage;
import lasermod.util.BlockActionPos;
import lasermod.util.LaserUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class TileEntityColourConverter extends TileEntitySingleSidedReceiver implements ILaserProvider {

	public boolean multipart = false;
	public EnumDyeColor colour = EnumDyeColor.RED;
	
	@Override
	public void updateLasers(boolean client) {
		super.updateLasers(client);
		if(!client) {
			EnumFacing facing = this.getInputSide().getOpposite();
			
			BlockActionPos action = LaserUtil.getFirstBlock(this, facing);
			if(action != null && action.isLaserReceiver(facing)) {
				LaserInGame laserInGame = this.getOutputLaser(facing);
				ILaserReceiver receiver = action.getLaserReceiver(facing);
	        	if(receiver.canReceive(this.world, this.pos, this.getInputSide(), laserInGame))
	        		receiver.onLaserIncident(this.world, this.pos, this.getInputSide(), laserInGame);
			}
			else if(action != null) {
				LaserInGame laserInGame = this.getOutputLaser(facing);
				
				if(laserInGame != null) {
					for(LaserType laser : laserInGame.getLaserType()) {
						laser.actionOnBlock(action);
					}
				}
			}
		}
	}
	
	@Override
	public void updateLaserAction(boolean client) {
		if(this.laser != null) {
			IBlockState state = this.getWorld().getBlockState(this.pos);
			
			LaserUtil.performLaserAction(this, state.getValue(BlockPoweredLaser.FACING), this.pos);
		}
	}

	public TileEntityColourConverter setColour(EnumDyeColor colour) {
		this.colour = colour;
		return this;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		if(tag.hasKey("colour"))
			this.colour = EnumDyeColor.byMetadata(tag.getInteger("colour"));
		if(tag.hasKey("laser"))
			this.laser = new LaserInGame(tag.getCompoundTag("laser"));
		else 
			this.laser = null;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("colour", this.colour.getMetadata());
		if(this.laser != null)
			tag.setTag("laser", this.laser.writeToNBT(new NBTTagCompound()));
		
		return tag;
	}
	
	@Override
	public LaserInGame getOutputLaser(EnumFacing dir) {
		if(this.laser != null) {
			LaserInGame outputLaser = this.laser.copy();
			outputLaser.red = (int)(LaserUtil.LASER_COLOUR_TABLE[this.colour.getMetadata()][0] * 255);
			outputLaser.green = (int)(LaserUtil.LASER_COLOUR_TABLE[this.colour.getMetadata()][1] * 255);
			outputLaser.blue = (int)(LaserUtil.LASER_COLOUR_TABLE[this.colour.getMetadata()][2] * 255);
			return outputLaser;
		}
		return null;
	}

	@Override
	public boolean isEmittingFromSide(World world, BlockPos askerPos, EnumFacing dir) {
		return this.laser != null && dir == this.getInputSide().getOpposite();
	}
	
	@Override
	public int getRange(EnumFacing dir) {
		return 64;
	}

	@Override
	public boolean isForgeMultipart() {
		return this.multipart;
	}
	
	@Override
	public List<LaserInGame> getOutputLasers() {
		IBlockState state = this.getWorld().getBlockState(this.pos);
		
		return Arrays.asList(this.getOutputLaser(state.getValue(BlockPoweredRedstone.FACING)));
	}

	@Override
	public void sendUpdateDescription() {
		PacketDispatcher.sendToAllTracking(new ColourConverterMessage(this), this);
		
	}

	@Override
	public void onLaserPass(World world) {
		world.scheduleUpdate(this.pos, ModBlocks.COLOUR_CONVERTER, 4);
	}

	@Override
	public void onLaserRemoved(World world) {
		world.scheduleUpdate(this.pos, ModBlocks.COLOUR_CONVERTER, 4);
	}

	@Override
	public EnumFacing getInputSide() {
		IBlockState state = this.getWorld().getBlockState(this.pos);
		
		return state.getValue(BlockPoweredRedstone.FACING).getOpposite();
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}
	
	@Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
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
