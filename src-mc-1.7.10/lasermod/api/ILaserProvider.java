package lasermod.api;

import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public interface ILaserProvider {

	public int getX();
	public int getY();
	public int getZ();
	public World getWorld();
	public LaserInGame getOutputLaser(int side);
	public int getDistance();
	
	public boolean isSendingSignalFromSide(World world, int askerX, int askerY, int askerZ, int side);
}
