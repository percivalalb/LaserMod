package lasermod.api;

import java.util.List;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author ProPercivalalb
 */
public interface ILaserReceiver {

	public int getX();
	public int getY();
	public int getZ();
	public World getWorld();
	
	public boolean canPassOnSide(World world, int orginX, int orginY, int orginZ, ForgeDirection dir, LaserInGame laserInGame);
	
	public void passLaser(World world, int orginX, int orginY, int orginZ, ForgeDirection dir, LaserInGame laserInGame);

	public void removeLasersFromSide(World world, int orginX, int orginY, int orginZ, ForgeDirection dir);
	public List<LaserInGame> getInputLasers();
}
