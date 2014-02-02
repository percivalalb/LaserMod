package lasermod.tileentity;

import lasermod.api.ILaserReciver;
import lasermod.api.LaserInGame;
import lasermod.util.LaserUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class TileEntityLaserDetector extends TileEntityLaserDevice implements ILaserReciver {

	private int lagReduce = -1;
	
	@Override
	public void func_145845_h() {
		if(this.field_145850_b.isRemote) return;
		
		this.lagReduce += 1;
		if(this.lagReduce % LaserUtil.TICK_RATE != 0) return;
		
		int count = 0;
		for(int i = 0; i < 6; ++i)
			if(this.func_145832_p() == 1 && !LaserUtil.isValidSourceOfPowerOnSide(this, i))
				count++;
		
		if(count == 6)
			this.field_145850_b.setBlockMetadataWithNotify(this.field_145851_c, this.field_145848_d, this.field_145849_e, 0, 3);
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
		return world.getBlockMetadata(this.field_145851_c, this.field_145848_d, this.field_145849_e) != 1;
	}

	@Override
	public void passLaser(World world, int orginX, int orginY, int orginZ, LaserInGame laserInGame) {
		if(!world.isRemote)
			world.setBlockMetadataWithNotify(this.field_145851_c, this.field_145848_d, this.field_145849_e, 1, 3);
	}

	@Override
	public void removeLasersFromSide(World world, int orginX, int orginY, int orginZ, int side) {
		
	}
}
