package lasermod.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;

/**
 * @author ProPercivalalb
 */
public class TileEntityReflector extends TileEntity {

	public boolean[] openSides = new boolean[6];
	
	public boolean isSideOpen(ForgeDirection direction) {
		return this.isSideOpen(direction.ordinal());
	}
	
	public boolean isSideOpen(int side) {
		return this.openSides[side];
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
    	return INFINITE_EXTENT_AABB;
    }
}
