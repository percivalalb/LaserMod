package lasermod.tileentity;

import lasermod.LaserMod;
import lasermod.ModBlocks;
import lasermod.api.ILaserProvider;
import lasermod.api.ILaserReceiver;
import lasermod.api.LaserInGame;
import lasermod.network.packet.PacketColourConverter;
import lasermod.util.LaserUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class TileEntityColourConverter extends TileEntityLaserDevice implements ILaserProvider, ILaserReceiver {
	
	public LaserInGame laser;
	public int colour = 14;
	
	@Override
	public void updateEntity() {

		if(this.getWorldObj().getWorldInfo().getWorldTotalTime() % LaserUtil.TICK_RATE == 0)  {
		
			if(this.laser != null && !LaserUtil.isValidSourceOfPowerOnSide(this, Facing.oppositeSide[this.getBlockMetadata()])) {
				this.setLaser(null);
				LaserMod.NETWORK_MANAGER.sendPacketToAllAround(new PacketColourConverter(this), this.worldObj.provider.dimensionId, this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, 512);
			}
			
			this.worldObj.scheduleBlockUpdate(this.xCoord, this.yCoord, this.zCoord, ModBlocks.colourConverter, 4);
		}
		
		if(this.getWorldObj().getWorldInfo().getWorldTotalTime() % LaserUtil.LASER_RATE == 0) {
			if(this.laser != null)
				LaserUtil.performLaserAction(this, this.getBlockMetadata(), this.xCoord, this.yCoord, this.zCoord);
		}
	}
	
	public void setLaser(LaserInGame laser) {
		this.laser = laser;
		this.setNeedsUpdate();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		if(tag.hasKey("colour"))
			this.colour = tag.getInteger("colour");
		if(tag.hasKey("laser"))
			this.laser = new LaserInGame(tag.getCompoundTag("laser"));
		else 
			this.laser = null;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("colour", this.colour);
		if(this.laser != null)
			tag.setTag("laser", this.laser.writeToNBT(new NBTTagCompound()));
	}
	
	@Override
	public Packet getDescriptionPacket() {
	    return new PacketColourConverter(this).getPacket();
	}
	
	@Override
	public LaserInGame getOutputLaser(int side) {
		if(this.laser != null) {
			this.laser.setSide(Facing.oppositeSide[side]);
			this.laser.red = (int)(LaserUtil.LASER_COLOUR_TABLE[this.colour][0] * 255);
			this.laser.green = (int)(LaserUtil.LASER_COLOUR_TABLE[this.colour][1] * 255);
			this.laser.blue = (int)(LaserUtil.LASER_COLOUR_TABLE[this.colour][2] * 255);
			return this.laser.copy();
		}
		return null;
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
	public boolean canPassOnSide(World world, int orginX, int orginY, int orginZ, int side, LaserInGame laserInGame) {
		return side == Facing.oppositeSide[this.getBlockMetadata()];
	}
	
	@Override
	public void passLaser(World world, int orginX, int orginY, int orginZ, int side, LaserInGame laserInGame) {
		if(this.getOutputLaser(side) == null) {
			this.setLaser(laserInGame);
			LaserMod.NETWORK_MANAGER.sendPacketToAllAround(new PacketColourConverter(this), world.provider.dimensionId, this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, 512);
			world.scheduleBlockUpdate(this.xCoord, this.yCoord, this.zCoord, ModBlocks.colourConverter, 4);
		}
	}

	@Override
	public void removeLasersFromSide(World world, int orginX, int orginY, int orginZ, int side) {
		if(side == Facing.oppositeSide[this.getBlockMetadata()]) {
			boolean change = this.laser != null;
			this.setLaser(null);
			if(change) {
				LaserMod.NETWORK_MANAGER.sendPacketToAllAround(new PacketColourConverter(this), world.provider.dimensionId, this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, 512);
				world.scheduleBlockUpdate(this.xCoord, this.yCoord, this.zCoord, ModBlocks.colourConverter, 4);
			}
		}
	}
	
	@Override
	public boolean isSendingSignalFromSide(World world, int askerX, int askerY, int askerZ, int side) {
		return this.getOutputLaser(side) != null && side == this.getBlockMetadata();
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
