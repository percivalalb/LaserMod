package lasermod.tileentity;

import java.util.Arrays;
import java.util.List;

import lasermod.api.ILaser;
import lasermod.api.ILaserProvider;
import lasermod.api.ILaserReceiver;
import lasermod.api.LaserInGame;
import lasermod.api.base.TileEntitySingleSidedReciever;
import lasermod.network.PacketDispatcher;
import lasermod.network.packet.client.SmallColourConverterMessage;
import lasermod.util.BlockActionPos;
import lasermod.util.LaserUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author ProPercivalalb
 */
public class TileEntitySmallColourConverter extends TileEntitySingleSidedReciever implements ILaserProvider {

	public boolean multipart = false;
	public int colour = 14;
	
	@Override
	public void updateLasers(boolean client) {
		super.updateLasers(client);
			
		BlockActionPos action = LaserUtil.getFirstBlock(this, this.getInputSide().getOpposite().ordinal());
		if(action != null && action.isLaserReceiver(this.getInputSide().getOpposite().ordinal())) {
			LaserInGame laserInGame = this.getOutputLaser(this.getInputSide().getOpposite().ordinal());
			ILaserReceiver receiver = action.getLaserReceiver(this.getInputSide().getOpposite().ordinal());
        	if(receiver.canPassOnSide(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.getInputSide().ordinal(), laserInGame)) {
        		receiver.passLaser(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.getInputSide().ordinal(), laserInGame);
			}
		}
		else if(action != null) {
			LaserInGame laserInGame = this.getOutputLaser(this.getInputSide().getOpposite().ordinal());
			
			if(laserInGame != null) {
				for(ILaser laser : laserInGame.getLaserType()) {
					laser.actionOnBlock(action);
				}
			}
		}
	}
	
	@Override
	public void updateLaserAction(boolean client) {
		if(this.laser != null)
			LaserUtil.performLaserAction(this, this.getBlockMetadata(), this.xCoord, this.yCoord, this.zCoord);
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
			this.laser = new LaserInGame(tag.getCompoundTag("laser"));
		else 
			this.laser = null;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("colour", this.colour);
		if(this.laser != null)
			tag.setTag("laser", this.laser.writeToNBT(new NBTTagCompound()));
	}
	
	@Override
	public Packet getDescriptionPacket() {
	    return PacketDispatcher.getPacket(new SmallColourConverterMessage(this));
	}
	
	@Override
	public LaserInGame getOutputLaser(int side) {
		if(this.laser != null) {
			LaserInGame outputLaser = this.laser.copy();
			outputLaser.setSide(Facing.oppositeSide[side]);
			outputLaser.red = (int)(LaserUtil.LASER_COLOUR_TABLE[this.colour][0] * 255);
			outputLaser.green = (int)(LaserUtil.LASER_COLOUR_TABLE[this.colour][1] * 255);
			outputLaser.blue = (int)(LaserUtil.LASER_COLOUR_TABLE[this.colour][2] * 255);
			return outputLaser;
		}
		return null;
	}

	@Override
	public boolean isSendingSignalFromSide(World world, int askerX, int askerY, int askerZ, int side) {
		return this.getOutputLaser(side) != null && side == this.getInputSide().ordinal();
	}
	
	@Override
	public int getDistance(int side) {
		return 64;
	}

	@Override
	public boolean isForgeMultipart() {
		return this.multipart;
	}
	
	@Override
	public List<LaserInGame> getOutputLasers() {
		return Arrays.asList(this.getOutputLaser(this.getInputSide().getOpposite().ordinal()));
	}

	@Override
	public void sendUpdateDescription() {
		PacketDispatcher.sendToAllAround(new SmallColourConverterMessage(this), this, 512);
		
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
	public ForgeDirection getInputSide() {
		return ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite();
	}
}
