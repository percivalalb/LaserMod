package lasermod.block.laser;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.entity.player.EntityPlayer;
import lasermod.api.ILaser;

/**
 * @author ProPercivalalb
 */
public class PushLaser implements ILaser {

	public static final float SPEED_MULTIPLYER = 1.0F;
	
	@Override
	public void performActionOnEntities(List<Entity> entities, int direction) {
		for(Entity entity : entities) {
			
		}
	}
	
	@Override
	public boolean shouldRenderLaser(EntityPlayer player, int direction) {
		return true;
	}
}
