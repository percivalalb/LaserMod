package lasermod.tileentity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import codechicken.lib.math.MathHelper;
import lasermod.ModBlocks;
import lasermod.api.ILaser;
import lasermod.api.ILaserProvider;
import lasermod.api.ILaserReceiver;
import lasermod.api.LaserInGame;
import lasermod.api.base.TileEntityLaserDevice;
import lasermod.api.base.TileEntityMultiSidedReciever;
import lasermod.network.PacketDispatcher;
import lasermod.network.packet.client.ReflectorMessage;
import lasermod.util.BlockActionPos;
import lasermod.util.LaserUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class TileEntityReflector extends TileEntityMultiSidedReciever implements ILaserProvider {

	public boolean[] closedSides = new boolean[] {true, true, true, true, true, true};
	
	@Override
	public void updateLasers(boolean client) {
		super.updateLasers(client);
		
		if(!client) {
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				
				
				if(this.closedSides[dir.ordinal()] && this.containsInputSide(dir.ordinal()) && this.noLaserInputs())
					continue;
				BlockActionPos action = LaserUtil.getFirstBlock(this, dir.ordinal());
				if(action != null && action.isLaserReceiver(dir.ordinal())) {
					LaserInGame laserInGame = this.getOutputLaser(dir.ordinal());
					ILaserReceiver receiver = action.getLaserReceiver(dir.ordinal());
				  	if(receiver.canPassOnSide(this.worldObj, this.xCoord, this.yCoord, this.zCoord, dir.getOpposite().ordinal(), laserInGame))
				  		receiver.passLaser(this.worldObj, this.xCoord, this.yCoord, this.zCoord, dir.getOpposite().ordinal(), laserInGame);
				}
				else if(action != null) {
	    			LaserInGame laserInGame = this.getOutputLaser(dir.ordinal());
	    			
	    			if(laserInGame != null)
		    			for(ILaser laser : laserInGame.getLaserType())
		    				laser.actionOnBlock(action);
	    		}
			}
		}
	}
	
	@Override
	public boolean checkPowerOnSide(ForgeDirection dir) {
		return !this.closedSides[dir.ordinal()] && this.containsInputSide(dir.ordinal());
	}
	
	@Override
	public void updateLaserAction(boolean client) {
		for(int i = 0; i < this.closedSides.length; ++i) {
			if(this.closedSides[i] || this.containsInputSide(i) || this.noLaserInputs())
				continue;
			LaserUtil.performLaserAction(this, i, this.xCoord, this.yCoord, this.zCoord);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		
		int[] close = tag.getIntArray("closeSides");
		for(int i = 0; i < close.length; ++i)
			this.closedSides[i] = close[i] == 1;
		
		int amount = tag.getInteger("laserCount");
		 for(int i = 0; i < amount; ++i)
			 this.lasers.add(new LaserInGame(tag.getCompoundTag("laser" + i)));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		
		int[] close = new int[this.closedSides.length];
		for(int i = 0; i < this.closedSides.length; ++i)
			close[i] = this.closedSides[i] ? 1 : 0;
		tag.setIntArray("closeSides", close);
		
		tag.setInteger("laserCount", this.lasers.size());
		
		 for(int i = 0; i < lasers.size(); ++i)
			 tag.setTag("laser" + i, this.lasers.get(i).writeToNBT(new NBTTagCompound()));
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
	public LaserInGame getOutputLaser(int side) {
		return this.getCombinedOutputLaser(side);
	}
	
	@Override
	public boolean canPassOnSide(World world, int orginX, int orginY, int orginZ, int side, LaserInGame laserInGame) {
		return !this.closedSides[side] && (!this.containsInputSide(side) || super.canPassOnSide(world, orginX, orginY, orginZ, side, laserInGame));
	}

	
	@Override
	public boolean isSendingSignalFromSide(World world, int askerX, int askerY, int askerZ, int side) {
		return !this.closedSides[side] && this.lasers.size() > 0;
	}
	
	@Override
	public int getDistance(int side) {
		int total = 0;
		
		for(int i = 0; i < 6; i++)
			if(!this.closedSides[i] && !this.containsInputSide(i)) 
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
		for(int i = 0; i < this.closedSides.length; ++i) {
			if(this.closedSides[i] || this.containsInputSide(i) || this.noLaserInputs())
				continue;
			list.add(this.getOutputLaser(i));
		}
		
		return list;
	}
}