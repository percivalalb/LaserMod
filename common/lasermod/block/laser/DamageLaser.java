package lasermod.block.laser;

import java.util.List;

import lasermod.api.ILaser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

/**
 * @author ProPercivalalb
 */
public class DamageLaser implements ILaser {
	public static DamageSource laserDamage = new DamageSourceLaser().setDamageBypassesArmor();

	@Override
	public void performActionOnEntitiesServer(List<Entity> entities, int direction) {
		for (Entity entity : entities)
			entity.attackEntityFrom(this.laserDamage, 3);
	}

	@Override
	public void performActionOnEntitiesClient(List<Entity> entities, int direction) {
		for (Entity entity : entities) {}
	}

	@Override
	public void performActionOnEntitiesBoth(List<Entity> entities, int direction) {
		for (Entity entity : entities) {}
	}

	@Override
	public boolean shouldRenderLaser(EntityPlayer player, int direction) {
		return true;
	}
}