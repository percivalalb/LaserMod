package lasermod.tileentity;

import lasermod.api.ILaserProvider;
import lasermod.api.ILaserReciver;
import lasermod.api.LaserInGame;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class TileEntityReflector extends TileEntityLaserDevice implements ILaserProvider, ILaserReciver {

	@Override
	public LaserInGame getOutputLaser() {
		return null;
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
		return !this.openSides[side] && !this.containsInputSide(side);
	}
	
	@Override
	public void passLaser(World world, int orginX, int orginY, int orginZ, LaserInGame laserInGame) {
		TileEntityReflector reflector = (TileEntityReflector)world.getBlockTileEntity(blockX, blockY, blockZ);
		reflector.addLaser(laserInGame);
		if(!world.isRemote)
			PacketDispatcher.sendPacketToAllAround(blockX + 0.5D, blockY + 0.5D, blockZ + 0.5D, 512, world.provider.dimensionId, reflector.getDescriptionPacket());
		
	}

	@Override
	public void removeLasersFromSide(World world, int orginX, int orginY, int orginZ, int side) {
		
		boolean flag = reflector.removeAllLasersFromSide(side);
		
		//if(flag && !world.isRemote)
		//	PacketDispatcher.sendPacketToAllAround(blockX + 0.5D, blockY + 0.5D, blockZ + 0.5D, 512, world.provider.dimensionId, reflector.getDescriptionPacket());
	}
	
	@Override
	public boolean isSendingSignalFromSide(World world, int askerX, int askerY, int askerZ, int side) {
		return !this.openSides[side] && this.lasers.size() > 0;
	}
}
