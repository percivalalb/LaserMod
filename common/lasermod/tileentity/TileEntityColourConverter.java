package lasermod.tileentity;

import cpw.mods.fml.common.FMLLog;
import lasermod.api.ILaserProvider;
import lasermod.api.ILaserReciver;
import lasermod.api.LaserInGame;
import lasermod.util.LaserUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class TileEntityColourConverter extends TileEntityLaserDevice implements ILaserProvider, ILaserReciver {

	private int lagReduce = -1;
	
	private LaserInGame laser;
	public int colour = 14;
	
	@Override
	public void func_145845_h() {
		if(this.field_145850_b.isRemote) return;
		
		this.lagReduce += 1;
		if(this.lagReduce % LaserUtil.TICK_RATE != 0) return;
		
	}
	
	public void setLaser(LaserInGame laser) {
		this.laser = laser;
		this.setNeedsUpdate();
	}
	
	@Override
	public LaserInGame getOutputLaser() {
		if(this.requiresUpdate() && this.laser != null) {
			FMLLog.info("Update");
			this.laser.red = (int)(LaserUtil.LASER_COLOUR_TABLE[this.colour][0] * 255);
			this.laser.green = (int)(LaserUtil.LASER_COLOUR_TABLE[this.colour][1] * 255);
			this.laser.blue = (int)(LaserUtil.LASER_COLOUR_TABLE[this.colour][2] * 255);
			this.setNoUpdate();
		}
		return this.laser;
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
	public boolean canPassOnSide(World world, int orginX, int orginY, int orginZ, int side) {
		return side == Facing.oppositeSide[this.func_145832_p()];
	}
	
	@Override
	public void passLaser(World world, int orginX, int orginY, int orginZ, LaserInGame laserInGame) {
		if(this.getOutputLaser() == null) {
			this.setLaser(laserInGame);
			///if(!world.isRemote)
				//PacketDispatcher.sendPacketToAllAround(blockX + 0.5D, blockY + 0.5D, blockZ + 0.5D, 512, world.provider.dimensionId, colourConverter.getDescriptionPacket());
		}
	}

	@Override
	public void removeLasersFromSide(World world, int orginX, int orginY, int orginZ, int side) {
		if(side == Facing.oppositeSide[this.func_145832_p()]) {
			this.setLaser(null);
			//if(!world.isRemote)
			//	PacketDispatcher.sendPacketToAllAround(blockX + 0.5D, blockY + 0.5D, blockZ + 0.5D, 512, world.provider.dimensionId, colourConverter.getDescriptionPacket());
		}
	}
	
	@Override
	public boolean isSendingSignalFromSide(World world, int askerX, int askerY, int askerZ, int side) {
		return this.getOutputLaser() != null && side == this.func_145832_p();
	}
}
