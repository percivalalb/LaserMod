package lasermod.api;

import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public interface ILaserReciver {

	public boolean canPassOnSide(World world, int blockX, int blockY, int blockZ, int orginX, int orginY, int orginZ, int side);
	
	public boolean isSendingSignalFromSide(World world, int blockX, int blockY, int blockZ, int orginX, int orginY, int orginZ, int side);
	
	public void passLaser(World world, int blockX, int blockY, int blockZ, int orginX, int orginY, int orginZ, LaserInGame laserInGame);

	public void removeLasersFromSide(World world, int blockX, int blockY, int blockZ, int orginX, int orginY, int orginZ, int side);
}
