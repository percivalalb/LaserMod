package lasermod.item;

import lasermod.LaserMod;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

/**
 * @author ProPercivalalb
 */
public class ItemLaserSeekingGoggles extends ItemArmor  {
	
	public static ArmorMaterial LASER_GOGGLES_MATERIAL = EnumHelper.addArmorMaterial("laser_goggles", "lasermod:laser_goggles", 64, new int[] {0, 0, 0, 2}, 30, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.0F);
	
	public ItemLaserSeekingGoggles() {
		super(LASER_GOGGLES_MATERIAL, 1, EntityEquipmentSlot.HEAD);
		this.setCreativeTab(LaserMod.TAB_LASER);
	}
	

	@Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return "lasermod:textures/model/armor/laser_goggles.png";
    }
}
