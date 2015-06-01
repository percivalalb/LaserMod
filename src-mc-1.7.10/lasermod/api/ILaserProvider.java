package lasermod.api;

import java.util.List;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author ProPercivalalb
 */
public interface ILaserProvider {

	public int getX();
	public int getY();
	public int getZ();
	public World getWorld();
	public LaserInGame getOutputLaser(ForgeDirection dir);
	public int getDistance(ForgeDirection dir);
	public boolean isForgeMultipart();
	
	public boolean isSendingSignalFromSide(World world, int askerX, int askerY, int askerZ, ForgeDirection dir);
	public List<LaserInGame> getOutputLasers();
}
