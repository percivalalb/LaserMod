package lasermod.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lasermod.api.ILaserProvider;
import lasermod.api.ILaserReciver;
import lasermod.api.LaserInGame;
import lasermod.api.LaserRegistry;
import lasermod.util.LaserUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class TileEntityBasicLaser extends TileEntityLaserDevice implements ILaserProvider {

	private int lagReduce = -1;
	
	@Override
	public void func_145845_h() {
		if(this.field_145850_b.isRemote) return;
		
		this.lagReduce += 1;
		if(this.lagReduce % LaserUtil.TICK_RATE != 0) return;
		
		ILaserReciver reciver = LaserUtil.getFirstReciver(this, this.func_145832_p());
		if(reciver != null) {
		  	boolean hasSignal = (this.field_145850_b.isBlockIndirectlyGettingPowered(this.field_145851_c, this.field_145848_d, this.field_145849_e));
		  	
		  	if(!hasSignal) {
		  		reciver.removeLasersFromSide(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, Facing.oppositeSide[this.func_145832_p()]);
		  	}
		  	else if(reciver.canPassOnSide(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, Facing.oppositeSide[this.func_145832_p()])) {
				reciver.passLaser(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, this.getOutputLaser());
			}
		}
		this.lagReduce += 1;
	}
	
	@Override
	public int getX() { return this.field_145851_c; }
	@Override
	public int getY() { return this.field_145848_d; }
	@Override
	public int getZ() { return this.field_145849_e; }

	@Override
	public World getWorld() {
		return this.field_145850_b;
	}
	
	@Override
	public boolean isSendingSignalFromSide(World world, int askerX, int askerY, int askerZ, int side) {
		return this.field_145850_b.isBlockIndirectlyGettingPowered(this.field_145851_c, this.field_145848_d, this.field_145849_e) && this.func_145832_p() == side;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
    	return INFINITE_EXTENT_AABB;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public double func_145833_n() {
        return 65536.0D;
    }

	@Override
	public LaserInGame getOutputLaser() {
		return new LaserInGame(LaserRegistry.getLaserFromId("default"));
	}
}
