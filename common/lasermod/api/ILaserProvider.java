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

}
