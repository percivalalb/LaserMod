package lasermod.api;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author ProPercivalalb
 */
public class LaserInGame {

	private double strength = 100D;
	private ArrayList<ILaser> laserType = new ArrayList<ILaser>();
	private int side = -1;
	
	public LaserInGame(ILaser laser) {
		laserType.add(laser);
	}
	
	public LaserInGame(ArrayList<ILaser> lasers) {
		laserType.addAll(lasers);
	}
	
	public LaserInGame setStrength(double strength) {
		if(strength < 0.0D)
			strength = 0.0D;
		this.strength = strength; 
		return this;
	}
	
	public LaserInGame setSide(int side) {
		this.side = side;
		return this;
	}
	
	public LaserInGame setLaserType(ILaser laser) {
		this.laserType.clear();
		this.laserType.add(laser);
		return this;
	}
	
	public LaserInGame addLaserType(ILaser laser) {
		this.laserType.add(laser);
		return this;
	}
	
	public LaserInGame setLaserType(String laser) {
		return setLaserType(LaserRegistry.getLaserFromId(laser));
	}
	
	public LaserInGame addLaserType(String laser) {
		return setLaserType(LaserRegistry.getLaserFromId(laser));
	}
	
	public double getStrength() {
		return this.strength;
	}
	
	public ArrayList<ILaser> getLaserType() {
		return this.laserType;
	}
	
	public void readFromNBT(NBTTagCompound tag) {
		
	}
	
	public void writeToNBT(NBTTagCompound tag) {
		
	}
	
	public int getSide() {
		return this.side;
	}

	public int laserCount() {
		return this.laserType.size();
	}
}
