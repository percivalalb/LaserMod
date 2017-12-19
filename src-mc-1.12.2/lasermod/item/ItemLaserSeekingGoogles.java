package lasermod.item;

import lasermod.LaserMod;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class ItemLaserSeekingGoogles extends ItemArmor  {
	
	public ItemLaserSeekingGoogles() {
		super(ArmorMaterial.CHAIN, 1, EntityEquipmentSlot.HEAD);
		this.setCreativeTab(LaserMod.TAB_LASER);
	}
	
	//TODO
	/**
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
	    return "lasermod:textures/armor/laserGoogles.png";
	}**/
}
