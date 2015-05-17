package lasermod.laser;

import net.minecraft.util.DamageSource;

/**
 * @author ProPercivalalb
 */
public class DamageSourceLaser extends DamageSource {

	public DamageSourceLaser() {
		super("lasermod.damage");
		this.setDamageBypassesArmor();
	}
	
	public DamageSource setDamageBypassesArmor() {
        return super.setDamageBypassesArmor();
    }
}
