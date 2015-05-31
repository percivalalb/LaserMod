package lasermod.api;

import java.util.List;

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
	public int getDistance(int side);
	public boolean isForgeMultipart();
	
	public boolean isSendingSignalFromSide(World world, int askerX, int askerY, int askerZ, int side);
	public List<LaserInGame> getOutputLasers();
}
