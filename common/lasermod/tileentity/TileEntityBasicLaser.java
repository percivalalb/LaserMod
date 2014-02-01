package lasermod.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lasermod.api.ILaserProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class TileEntityBasicLaser extends TileEntity implements ILaserProvider {

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
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
    	return INFINITE_EXTENT_AABB;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public double func_145833_n() {
		double distance = 128D;
        return distance * distance;
    }
}
