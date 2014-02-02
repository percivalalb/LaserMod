package lasermod.api;

import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public interface ILaserReciver {

	public int getX();
	public int getY();
	public int getZ();
	public World getWorld();
	
	public boolean canPassOnSide(World world, int orginX, int orginY, int orginZ, int side);
	
	public void passLaser(World world, int orginX, int orginY, int orginZ, LaserInGame laserInGame);

	public void removeLasersFromSide(World world, int orginX, int orginY, int orginZ, int side);
}
