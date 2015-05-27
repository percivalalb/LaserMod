package lasermod.tileentity;

import cpw.mods.fml.common.FMLLog;
import lasermod.LaserMod;
import lasermod.ModBlocks;
import lasermod.api.ILaserReceiver;
import lasermod.api.LaserInGame;
import lasermod.network.packet.PacketAdvancedLaser;
import lasermod.network.packet.PacketLuminousPanel;
import lasermod.network.packet.PacketReflector;
import lasermod.util.LaserUtil;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class TileEntityLuminousPanel extends TileEntityLaserDevice implements ILaserReceiver {

	public LaserInGame laser;
	
	@Override
	public void updateEntity() {
		if(this.getWorldObj().getWorldInfo().getWorldTotalTime() % LaserUtil.TICK_RATE != 0) return;
		
		if(!this.worldObj.isRemote) {
			if(this.getBlockMetadata() == 1) {
				int count = 0;
				for(int i = 0; i < 6; ++i)
					if(!LaserUtil.isValidSourceOfPowerOnSide(this, i))
						count++;
				
				if(count == 6) {
					this.laser = null;
					this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 0, 3);
					LaserMod.NETWORK_MANAGER.sendPacketToAllAround(new PacketLuminousPanel(this), this.getWorldObj().provider.dimensionId, this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, 512);
				}
			}
		}
	}
	
	@Override
	public Packet getDescriptionPacket() {
	    return new PacketLuminousPanel(this).getPacket();
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
		return this.laser == null;
	}

	@Override
	public void passLaser(World world, int orginX, int orginY, int orginZ, int side, LaserInGame laserInGame) {
		this.laser = laserInGame;
		world.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 1, 3);
		LaserMod.NETWORK_MANAGER.sendPacketToAllAround(new PacketLuminousPanel(this), world.provider.dimensionId, this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, 512);
	}

	@Override
	public void removeLasersFromSide(World world, int orginX, int orginY, int orginZ, int side) {
		boolean change = this.laser != null;
		this.laser = null;
		if(change) {
			LaserMod.NETWORK_MANAGER.sendPacketToAllAround(new PacketLuminousPanel(this), world.provider.dimensionId, this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, 512);
			world.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 0, 3);
		}
	}
}
