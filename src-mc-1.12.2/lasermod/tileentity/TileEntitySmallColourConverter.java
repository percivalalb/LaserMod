package lasermod.tileentity;

import java.util.Arrays;
import java.util.List;

import lasermod.api.ILaserProvider;
import lasermod.api.ILaserReceiver;
import lasermod.api.LaserInGame;
import lasermod.api.LaserType;
import lasermod.api.base.TileEntitySingleSidedReceiver;
import lasermod.network.PacketDispatcher;
import lasermod.network.packet.client.SmallColourConverterMessage;
import lasermod.util.BlockActionPos;
import lasermod.util.LaserUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class TileEntitySmallColourConverter extends TileEntitySingleSidedReceiver implements ILaserProvider {

	public boolean multipart = false;
	public int colour = 14;
	
	@Override
	public void tickLaserLogic() {
		super.tickLaserLogic();
			
		
		
		BlockActionPos action = LaserUtil.getFirstBlock(this, this.getInputSide().getOpposite());
		if(action != null && action.isLaserReceiver(this.getInputSide().getOpposite())) {
			LaserInGame laserInGame = this.getOutputLaser(this.getInputSide().getOpposite());
			ILaserReceiver receiver = action.getLaserReceiver(this.getInputSide().getOpposite());
	        if(receiver.canReceive(this.world, this.pos, this.getInputSide(), laserInGame)) {
	        	receiver.onLaserIncident(this.world, this.pos, this.getInputSide(), laserInGame);
			}
		}
		else if(action != null) {
			LaserInGame laserInGame = this.getOutputLaser(this.getInputSide().getOpposite());
				
			if(laserInGame != null) {
				for(LaserType laser : laserInGame.getLaserType()) {
					laser.actionOnBlock(action);
				}
			}
		}
	}
	
	@Override
	public void tickLaserAction(boolean client) {
		if(this.laser != null)
			LaserUtil.performLaserAction(this, EnumFacing.getFront(this.getBlockMetadata()), this.pos);
	}

	public TileEntitySmallColourConverter setColour(int colour) {
		this.colour = colour;
		return this;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		if(tag.hasKey("colour"))
			this.colour = tag.getInteger("colour");
		if(tag.hasKey("laser"))
			this.laser = LaserInGame.readFromNBT(tag.getCompoundTag("laser"));
		else 
			this.laser = null;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("colour", this.colour);
		if(this.laser != null)
			tag.setTag("laser", this.laser.writeToNBT(new NBTTagCompound()));
		
		return tag;
	}
	
	//TODO
			/**
	@Override
	public Packet getDescriptionPacket() {
	    return PacketDispatcher.getPacket(new SmallColourConverterMessage(this));
	}**/
	
	@Override
	public LaserInGame getOutputLaser(EnumFacing dir) {
		if(this.laser != null) {
			LaserInGame outputLaser = this.laser.copy();
			outputLaser.setDirection(dir.getOpposite());
			outputLaser.red = (int)(LaserUtil.LASER_COLOUR_TABLE[this.colour][0] * 255);
			outputLaser.green = (int)(LaserUtil.LASER_COLOUR_TABLE[this.colour][1] * 255);
			outputLaser.blue = (int)(LaserUtil.LASER_COLOUR_TABLE[this.colour][2] * 255);
			return outputLaser;
		}
		return null;
	}

	@Override
	public boolean isEmittingFromSide(World world, BlockPos askerPos, EnumFacing dir) {
		return this.getOutputLaser(dir) != null && dir == this.getInputSide();
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
		return Arrays.asList(this.getOutputLaser(this.getInputSide().getOpposite()));
	}

	@Override
	public void sendUpdateDescription() {
		PacketDispatcher.sendToAllTracking(new SmallColourConverterMessage(this), this);
		
	}

	@Override
	public void onLaserPass(World world) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLaserRemoved(World world) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EnumFacing getInputSide() {
		return EnumFacing.getFront(this.getBlockMetadata()).getOpposite();
	}
}
