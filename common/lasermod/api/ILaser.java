package lasermod.api;

import java.util.List;

import lasermod.tileentity.TileEntityAdvancedLaser;

import net.minecraft.entity.Entity;

/**
 * @author ProPercivalalb
 */
public interface ILaser {

	public void performActionOnEntities(TileEntityAdvancedLaser tileEntityAdvancedLaser, List<Entity> entities);
	
	
}
