package lasermod.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lasermod.api.ILaser;
import lasermod.api.ILaserReceiver;
import lasermod.api.LaserInGame;
import lasermod.network.PacketDispatcher;
import lasermod.network.packet.client.LuminousLampMessage;
import lasermod.util.LaserUtil;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class TileEntityLuminousLamp extends TileEntityLaserDevice implements ILaserReceiver {

	public ArrayList<LaserInGame> lasers = new ArrayList<LaserInGame>();
	
	@Override
	public void updateEntity() {
		if(this.getWorldObj().getWorldInfo().getWorldTotalTime() % LaserUtil.TICK_RATE != 0) return;
		
		if(!this.worldObj.isRemote) {
			if(this.getBlockMetadata() == 1) {
				boolean change = false;
				
				if(this.lasers != null && this.lasers.size() > 0) {
					for(int i = 0; i < 6; ++i)
						if(!LaserUtil.isValidSourceOfPowerOnSide(this, i)) {
							if(this.removeAllLasersFromSide(i))
								change = true;
						}
				}
				
				if(change)
					PacketDispatcher.sendToAllAround(new LuminousLampMessage(this), this.worldObj.provider.dimensionId, this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, 512);
				
				if(this.lasers == null || this.lasers.size() == 0) {
					this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 0, 3);
				}
			}
		}
	}
	
	public boolean addLaser(LaserInGame laserInGame, int side) {
		int i = getIndexOfLaserSide(side);
		if(i == -1)
			lasers.add(laserInGame);
		else
			lasers.set(i, laserInGame);
		return true;
		
	}
	
	public int getIndexOfLaserSide(int side) {
		for(int i = 0; i < this.lasers.size(); ++i)
			if(lasers.get(i).getSide() == side)
				return i;
		return -1;
	}
	
	public LaserInGame getLaserFromSide(int side) {
		for(int i = 0; i < this.lasers.size(); ++i)
			if(lasers.get(i).getSide() == side)
				return lasers.get(i);
		return null;
	}
	
	public boolean removeAllLasersFromSide(int side) {
		
		boolean flag = false;
		for(int i = 0; i < lasers.size(); ++i) {
			LaserInGame old = lasers.get(i);
			if(old.getSide() == side) {
				lasers.remove(i);
				flag = true;
			}
		}
		
		return flag;
	}
	
	public LaserInGame getCombinedOutputLaser() {
		if(this.lasers == null || this.lasers.size() == 0)
			return null;
		
		ArrayList<ILaser> laserList = new ArrayList<ILaser>();
		for(LaserInGame lig : this.lasers) {
			for(ILaser laser : lig.getLaserType()) {
				if(!laserList.contains(laser))
					laserList.add(laser);
			}
		}
		
		LaserInGame laserInGame = new LaserInGame(laserList);
		double totalPower = 0.0D;
		int red = lasers.get(0).red;
		int green = lasers.get(0).green;
		int blue = lasers.get(0).blue;
		double ratio = 0.5D;
		
		for(int i = 1; i < lasers.size(); ++i) {
			red = (int)((red * ratio) + (lasers.get(i).red * 0.5D));
			green = (int)((green * ratio) + (lasers.get(i).green * 0.5D));
			blue = (int)((blue * ratio) + (lasers.get(i).blue * 0.5D));
		}
	
		laserInGame.red = red;
		laserInGame.green = green;
		laserInGame.blue = blue;
				
		for(LaserInGame laser : lasers)
			totalPower += laser.getStrength();
		
		laserInGame.setStrength(totalPower);
		
		return laserInGame;
	}
	
	@Override
	public Packet getDescriptionPacket() {
	    return PacketDispatcher.getPacket(new LuminousLampMessage(this));
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
	public boolean canPassOnSide(World world, int orginX, int orginY, int orginZ, int side, LaserInGame laserInGame) {
		return !Objects.equals(laserInGame, this.getLaserFromSide(side));
	}
	
	@Override
	public void passLaser(World world, int orginX, int orginY, int orginZ, int side, LaserInGame laserInGame) {
		this.addLaser(laserInGame, side);
		PacketDispatcher.sendToAllAround(new LuminousLampMessage(this), this.worldObj.provider.dimensionId, this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, 512);
		this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 1, 3);
	}

	@Override
	public void removeLasersFromSide(World world, int orginX, int orginY, int orginZ, int side) {
		
		boolean flag = this.removeAllLasersFromSide(side);
		
		if(flag) {
			PacketDispatcher.sendToAllAround(new LuminousLampMessage(this), this.worldObj.provider.dimensionId, this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, 512);
		}
	}

	@Override
	public List<LaserInGame> getInputLasers() {
		return this.lasers;
	}
}
