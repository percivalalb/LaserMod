package lasermod.tileentity;

import lasermod.ModBlocks;
import lasermod.api.ILaserProvider;
import lasermod.api.LaserInGame;
import lasermod.util.LaserUtil;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class TileEntityBasicLaser extends TileEntityLaserDevice implements ILaserProvider {

	@Override
	public void updateEntity() {
		
		if(this.getWorldObj().getWorldInfo().getWorldTotalTime() % LaserUtil.TICK_RATE != 0) return;
		this.worldObj.scheduleBlockUpdate(this.xCoord, this.yCoord, this.zCoord, ModBlocks.basicLaser, 0);
	}
	
	@Override
	public LaserInGame getOutputLaser(int side) {
		return new LaserInGame().setSide(Facing.oppositeSide[side]);
	}
	
	@Override
	public int getX() { return this.xCoord; }
	@Override
	public int getY() { return this.yCoord; }
	@Override
	public int getZ() { return this.zCoord; }

	@Override
	public World getWorld() {
		return this.worldObj;
	}
	
	@Override
	public boolean isSendingSignalFromSide(World world, int askerX, int askerY, int askerZ, int side) {
		return this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord) && this.getBlockMetadata() == side;
	}
	
	@Override
	public int getDistance(int side) {
		return 64;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
    	return INFINITE_EXTENT_AABB;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }

	@Override
	public boolean isForgeMultipart() {
		return false;
	}
}
