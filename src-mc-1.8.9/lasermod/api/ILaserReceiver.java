package lasermod.api;

import java.util.List;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public interface ILaserReceiver {

	public BlockPos getPos();
	public World getWorld();
	
	public boolean canPassOnSide(World world, BlockPos orginPos, EnumFacing dir, LaserInGame laserInGame);
	
	public void passLaser(World world, BlockPos orginPos, EnumFacing dir, LaserInGame laserInGame);

	public void removeLasersFromSide(World world, BlockPos orginPos, EnumFacing dir);
	public List<LaserInGame> getInputLasers();
}
