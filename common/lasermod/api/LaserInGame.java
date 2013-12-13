package lasermod.api;

/**
 * @author ProPercivalalb
 */
public class LaserInGame {

	private double strength = 100D;
	private ILaser laserType = LaserRegistry.getLaserFromId("default");
	private int side = -1;
	
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
		this.laserType = laser;
		return this;
	}
	
	public LaserInGame setLaserType(String laser) {
		return setLaserType(LaserRegistry.getLaserFromId(laser));
	}
	
	public double getStrength() {
		return this.strength;
	}
	
	public ILaser getLaserType() {
		return this.laserType;
	}
	
}
