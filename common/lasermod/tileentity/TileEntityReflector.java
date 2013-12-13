package lasermod.tileentity;

import lasermod.packet.PacketReflectorUpdate;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;

/**
 * @author ProPercivalalb
 */
public class TileEntityReflector extends TileEntity {

	public boolean[] openSides = new boolean[] {true, true, true, true, true, true};
	
	public boolean isSideOpen(ForgeDirection direction) {
		return this.isSideOpen(direction.ordinal());
	}
	
	public boolean isSideOpen(int side) {
		return this.openSides[side];
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		for(int i = 0; i < openSides.length; ++i) {
			openSides[i] = tag.getBoolean("openSide" + i);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		
		for(int i = 0; i < openSides.length; ++i) {
			tag.setBoolean("openSide" + i, openSides[i]);
		}
	}

	@Override
	public Packet getDescriptionPacket() {
	    return new PacketReflectorUpdate(this.xCoord, this.yCoord, this.zCoord, this).buildPacket();
	}

	
	@Override
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
    	return INFINITE_EXTENT_AABB;
    }
}
