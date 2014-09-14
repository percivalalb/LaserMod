package lasermod.tileentity;

import lasermod.ModBlocks;
import lasermod.api.ILaserReciver;
import lasermod.api.LaserInGame;
import lasermod.util.LaserUtil;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class TileEntityLaserDetector extends TileEntityLaserDevice implements ILaserReciver {

	private int lagReduce = -1;
	
	@Override
	public void updateEntity() {
		
		this.lagReduce += 1;
		if(this.lagReduce % LaserUtil.TICK_RATE != 0) return;
		
		int count = 0;
		for(int i = 0; i < 6; ++i)
			if(this.getBlockMetadata() == 1 && !LaserUtil.isValidSourceOfPowerOnSide(this, i))
				count++;
		
		if(count == 6)
			this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 0, 3);
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
	public boolean canPassOnSide(World world, int orginX, int orginY, int orginZ, int side,  LaserInGame laserInGame) {
		return world.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord) != 1;
	}

	@Override
	public void passLaser(World world, int orginX, int orginY, int orginZ, int side, LaserInGame laserInGame) {
		if(!world.isRemote)
			world.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 1, 3);
	}

	@Override
	public void removeLasersFromSide(World world, int orginX, int orginY, int orginZ, int side) {
		world.scheduleBlockUpdate(this.xCoord, this.yCoord, this.zCoord, ModBlocks.laserDetector, 4);
	}
}
