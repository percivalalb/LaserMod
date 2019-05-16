package lasermod.api;

import java.util.List;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public interface ILaserReceiver {

	public BlockPos getPos();
	public World getWorld();
	
	public boolean canReceive(World worldIn, BlockPos orginPos, EnumFacing dir, LaserInGame laserInGame);
	
	public void onLaserIncident(World worldIn, BlockPos orginPos, EnumFacing dir, LaserInGame laserInGame);

	public void removeLaser(World worldIn, BlockPos orginPos, EnumFacing dir);
	public List<LaserInGame> getInputLasers();
}
