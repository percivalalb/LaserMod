package lasermod.api;

import java.util.List;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public interface ILaserProvider {

	public BlockPos getPos();
	public World getWorld();
	public LaserInGame getOutputLaser(EnumFacing dir);
	public int getDistance(EnumFacing dir);
	public boolean isForgeMultipart();
	
	public boolean isSendingSignalFromSide(World world, BlockPos askerPos, EnumFacing dir);
	public List<LaserInGame> getOutputLasers();
}
