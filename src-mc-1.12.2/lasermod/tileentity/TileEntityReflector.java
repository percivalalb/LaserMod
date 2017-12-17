package lasermod.tileentity;

import java.util.ArrayList;
import java.util.List;

import lasermod.api.ILaser;
import lasermod.api.ILaserProvider;
import lasermod.api.ILaserReceiver;
import lasermod.api.LaserInGame;
import lasermod.api.base.TileEntityMultiSidedReciever;
import lasermod.network.PacketDispatcher;
import lasermod.network.packet.client.ReflectorMessage;
import lasermod.util.BlockActionPos;
import lasermod.util.LaserUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class TileEntityReflector extends TileEntityMultiSidedReciever implements ILaserProvider {

	public boolean[] closedSides = new boolean[] {true, true, true, true, true, true};
	
	@Override
	public void updateLasers(boolean client) {
		super.updateLasers(client);
		
		if(!client) {
			for(EnumFacing dir : EnumFacing.VALUES) {
				if(this.closedSides[dir.ordinal()] || this.containsInputSide(dir) || this.noLaserInputs())
					continue;
				
				BlockActionPos action = LaserUtil.getFirstBlock(this, dir);
				if(action != null && action.isLaserReceiver(dir)) {
					LaserInGame laserInGame = this.getOutputLaser(dir);
					ILaserReceiver receiver = action.getLaserReceiver(dir);
				  	if(receiver.canPassOnSide(this.world, this.pos, dir.getOpposite(), laserInGame))
				  		receiver.passLaser(this.world, this.pos, dir.getOpposite(), laserInGame);
				}
				else if(action != null) {
	    			LaserInGame laserInGame = this.getOutputLaser(dir);
	    			
	    			if(laserInGame != null)
		    			for(ILaser laser : laserInGame.getLaserType())
		    				laser.actionOnBlock(action);
	    		}
			}
		}
	}
	
	@Override
	public boolean checkPowerOnSide(EnumFacing dir) {
		return !this.closedSides[dir.ordinal()] && this.containsInputSide(dir);
	}
	
	@Override
	public void updateLaserAction(boolean client) {
		for(EnumFacing dir : EnumFacing.VALUES) {
			if(this.closedSides[dir.ordinal()] || this.containsInputSide(dir) || this.noLaserInputs())
				continue;
			LaserUtil.performLaserAction(this, dir, this.pos);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		
		int[] close = tag.getIntArray("closeSides");
		for(int i = 0; i < close.length; ++i)
			this.closedSides[i] = close[i] == 1;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		
		int[] close = new int[this.closedSides.length];
		for(int i = 0; i < this.closedSides.length; ++i)
			close[i] = this.closedSides[i] ? 1 : 0;
		tag.setIntArray("closeSides", close);
		
		return tag;
	}

	@Override
	public Packet getDescriptionPacket() {
	    return PacketDispatcher.getPacket(new ReflectorMessage(this));
	}
	
	public int openSides() {
		int count = 0;
		for(boolean bool : this.closedSides)
			if(!bool)
				count++;
		return count;
	}
	
	@Override
	public LaserInGame getOutputLaser(EnumFacing dir) {
		return this.getCombinedOutputLaser(dir);
	}
	
	@Override
	public boolean canPassOnSide(World world, BlockPos orginPos, EnumFacing dir, LaserInGame laserInGame) {
		return !this.closedSides[dir.ordinal()] && (!this.containsInputSide(dir) || super.canPassOnSide(world, orginPos, dir, laserInGame));
	}

	
	@Override
	public boolean isSendingSignalFromSide(World world, BlockPos askerPos, EnumFacing dir) {
		return !this.closedSides[dir.ordinal()] && !this.noLaserInputs();
	}
	
	@Override
	public int getDistance(EnumFacing dir) {
		int total = 0;
		
		for(EnumFacing dir2 : EnumFacing.VALUES)
			if(!this.closedSides[dir2.ordinal()] && !this.containsInputSide(dir2)) 
				total += 1;
		if(total == 0)
			total = 1;
		return MathHelper.floor_double(64 / total);
	}

	@Override
	public boolean isForgeMultipart() {
		return false;
	}

	@Override
	public void sendUpdateDescription() {
		PacketDispatcher.sendToAllAround(new ReflectorMessage(this), this, 512);
	}

	@Override
	public void onLaserPass(World world) {
		
	}

	@Override
	public void onLaserRemoved(World world) {
		
	}
	
	@Override
	public List<LaserInGame> getOutputLasers() {
		List<LaserInGame> list = new ArrayList<LaserInGame>();
		for(EnumFacing dir : EnumFacing.VALUES) {
			if(this.closedSides[dir.ordinal()] || this.containsInputSide(dir) || this.noLaserInputs())
				continue;
			list.add(this.getOutputLaser(dir));
		}
		
		return list;
	}
}