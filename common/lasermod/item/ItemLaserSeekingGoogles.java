package lasermod.item;

import lasermod.LaserMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

/**
 * @author ProPercivalalb
 */
public class ItemLaserSeekingGoogles extends ItemArmor {
	
	public ItemLaserSeekingGoogles() {
		super(ArmorMaterial.CHAIN, LaserMod.proxy.armorRender("laserSeekingGoogles"), 0);
		this.setCreativeTab(CreativeTabs.tabBrewing);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
	    this.itemIcon = iconRegister.registerIcon("lasermod:laserSeekingGoogles");
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
