package lasermod.api;

/**
 * @author ProPercivalalb
 */
public class LaserInGame {

	private double strength = 100D;
	private ILaser laserType = LaserRegistry.getLaserFromId("default");
	
	public void setStrength(double strength) {
		if(strength < 0.0D)
			strength = 0.0D;
		this.strength = strength;
	}
	
	public double getStrength() {
		return this.strength;
	}
	
}
