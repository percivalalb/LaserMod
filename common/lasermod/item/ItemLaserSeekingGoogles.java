package lasermod.item;

import lasermod.LaserMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

/**
 * @author ProPercivalalb
 */
public class ItemLaserSeekingGoogles extends ItemArmor {

	public String texture;
	
	public ItemLaserSeekingGoogles(int id, String texture) {
		super(id, EnumArmorMaterial.CHAIN, LaserMod.proxy.armorRender("laserSeekingGoogles"), 3);
		this.texture = texture;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
	    this.itemIcon = iconRegister.registerIcon("lasermod:" + texture);
	}
	
	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
		return armorType == 0;
	}
	
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
	    return "lasermod:textures/armor/laserGoogles.png";
	}
}
