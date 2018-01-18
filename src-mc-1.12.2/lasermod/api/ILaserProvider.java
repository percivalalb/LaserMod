package lasermod.api;

import java.util.List;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public interface ILaserProvider {

	public BlockPos getPos();
	public World getWorld();
	public LaserInGame getOutputLaser(EnumFacing side);
	public int getRange(EnumFacing dir);
	public boolean isForgeMultipart();
	
	public boolean isSendingSignalFromSide(World worldIn, BlockPos askerPos, EnumFacing side);
	public List<LaserInGame> getOutputLasers();
}
