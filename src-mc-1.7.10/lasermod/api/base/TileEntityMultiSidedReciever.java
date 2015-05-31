package lasermod.api.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cpw.mods.fml.common.FMLLog;
import lasermod.api.ILaser;
import lasermod.api.ILaserReceiver;
import lasermod.api.LaserInGame;
import lasermod.network.PacketDispatcher;
import lasermod.util.LaserUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author ProPercivalalb
 */
public abstract class TileEntityMultiSidedReciever extends TileEntityLaserDevice implements ILaserReceiver {

	public LaserInGame lastCombinedLaser;
	public ArrayList<LaserInGame> lasers = new ArrayList<LaserInGame>();
	
	/**
	 * Override and will be called when there is an update to the lasers
	 */
	public abstract void sendUpdateDescription();
	
	public abstract void onLaserPass(World world);
	
	public abstract void onLaserRemoved(World world);
	
	public boolean checkPowerOnSide(ForgeDirection dir) {
		return true;
	}
	
	@Override
	public void updateLasers(boolean client) {
		
		if(!client) {
			if(!this.noLaserInputs()) {
				boolean change = false;
				
				for(ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
					if(this.checkPowerOnSide(direction) && !LaserUtil.isValidSourceOfPowerOnSide(this, direction.ordinal())) {
						if(this.removeAllLasersFromSide(direction.ordinal())) {
							change = true;
						}
					}
				}

				if(change) {
					this.sendUpdateDescription();
					this.onLaserRemoved(this.worldObj);
				}
					
			}
		}
	}
	
	@Override
	public void updateLaserAction(boolean client) {
		
	}
	
	@Override
	public int getX() {
		return this.xCoord;
	}

	@Override
	public int getY() {
		return this.yCoord;
	}

	@Override
	public int getZ() {
		return this.zCoord;
	}

	@Override
	public World getWorld() {
		return this.worldObj;
	}

	@Override
	public boolean canPassOnSide(World world, int orginX, int orginY, int orginZ, int side, LaserInGame laserInGame) {
		return !Objects.equals(laserInGame, this.getLaserFromSide(side));
	}

	@Override
	public void passLaser(World world, int orginX, int orginY, int orginZ, int side, LaserInGame laserInGame) {
		this.addLaser(laserInGame, side);
		this.onLaserPass(world);
		this.sendUpdateDescription();
		this.setUpdateRequired();
	}

	@Override
	public void removeLasersFromSide(World world, int orginX, int orginY, int orginZ, int side) {
		boolean flag = this.removeAllLasersFromSide(side);
		
		if(flag) {
			this.onLaserRemoved(world);
			this.sendUpdateDescription();
			this.setUpdateRequired();
		}
	}

	@Override
	public List<LaserInGame> getInputLasers() {
		return this.lasers;
	}

	//** TileEntityMultiSidedReciever helper methods **//
	
	public LaserInGame getLaserFromSide(int side) {
		for(int i = 0; i < this.lasers.size(); ++i)
			if(this.lasers.get(i).getSide() == side)
				return this.lasers.get(i);
		return null;
	}
	
	public int getIndexOfLaserSide(int side) {
		for(int i = 0; i < this.lasers.size(); ++i)
			if(this.lasers.get(i).getSide() == side)
				return i;
		return -1;
	}
	
	public boolean addLaser(LaserInGame laserInGame, int side) {
		int i = this.getIndexOfLaserSide(side);
		if(i == -1)
			lasers.add(laserInGame);
		else
			lasers.set(i, laserInGame);
		return true;
	}
	
	public boolean removeAllLasersFromSide(int side) {
		boolean change = false;
		for(int i = 0; i < lasers.size(); ++i) {
			if(lasers.get(i).getSide() == side) {
				lasers.remove(i);
				change = true;
			}
		}
		return change;
	}
	
	public boolean containsInputSide(int side) {
		for(int i = 0; i < this.lasers.size(); ++i)
			if(this.lasers.get(i).getSide() == side)
				return true;
		return false;
	}
	
	public boolean noLaserInputs() {
		return this.lasers.size() == 0;
	}
	
	public LaserInGame getCombinedOutputLaser(int side) {
		if(this.lasers.size() == 0)
			return null;
		
		int facing = Facing.oppositeSide[side];
		ArrayList<ILaser> laserList = new ArrayList<ILaser>();
		for(LaserInGame lig : this.lasers) {
			for(ILaser laser : lig.getLaserType()) {
				if(!laserList.contains(laser))
					laserList.add(laser);
			}
		}
		
		LaserInGame laserInGame = new LaserInGame(laserList);
		int red = lasers.get(0).red;
		int green = lasers.get(0).green;
		int blue = lasers.get(0).blue;
		
		for(int i = 1; i < lasers.size(); ++i) {
			red = (int)((red * 0.5D) + (lasers.get(i).red * 0.5D));
			green = (int)((green * 0.5D) + (lasers.get(i).green * 0.5D));
			blue = (int)((blue * 0.5D) + (lasers.get(i).blue * 0.5D));
		}
	
		laserInGame.red = red;
		laserInGame.green = green;
		laserInGame.blue = blue;
				
		double totalPower = 0.0D;
		for(LaserInGame laser : lasers)
			totalPower += laser.getStrength();
		
		laserInGame.setSide(facing);
		laserInGame.setStrength(totalPower / lasers.size());
		
		return laserInGame;
	}
}
