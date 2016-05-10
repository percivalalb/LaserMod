package lasermod.item;

import lasermod.LaserMod;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class ItemLaserSeekingGoogles extends ItemArmor  {
	
	public ItemLaserSeekingGoogles() {
		super(ArmorMaterial.CHAIN, 1, 0);
		this.setCreativeTab(LaserMod.tabLaser);
	}

	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
		return armorType == 0;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
	    return "lasermod:textures/armor/laserGoogles.png";
	}
}
