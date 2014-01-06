package lasermod.item;

import java.util.List;

import lasermod.LaserMod;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class ItemUpgrades extends Item {
	public Icon upgradeFire, upgradeWater, upgradeIce, upgradeInvisable, upgradeMine, upgradePush, upgradePull, upgradeDamage;

	public ItemUpgrades(int id) {
		super(id);
		this.setCreativeTab(LaserMod.laserTab);
		this.setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.upgradeFire = iconRegister.registerIcon("lasermod:upgradeFire");
		this.upgradeWater = iconRegister.registerIcon("lasermod:upgradeWater");
		this.upgradeIce = iconRegister.registerIcon("lasermod:upgradeIce");
		this.upgradeInvisable = iconRegister.registerIcon("lasermod:upgradeInvisable");
		this.upgradeMine = iconRegister.registerIcon("lasermod:upgradeMine");
		this.upgradePush = iconRegister.registerIcon("lasermod:upgradePush");
		this.upgradePull = iconRegister.registerIcon("lasermod:upgradePull");
		this.upgradeDamage = iconRegister.registerIcon("lasermod:upgradeDamage");
	}

	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta) {
		if (meta == 0)
			return this.upgradeFire;
		if (meta == 1)
			return this.upgradeWater;
		if (meta == 2)
			return this.upgradeIce;
		if (meta == 3)
			return this.upgradeInvisable;
		if (meta == 4)
			return this.upgradeMine;
		if (meta == 5)
			return this.upgradePush;
		if (meta == 6)
			return this.upgradePull;
		if (meta == 7)
			return this.upgradeDamage;
		return this.itemIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs creativeTab, List tabList) {
		tabList.add(new ItemStack(id, 1, 0));
		tabList.add(new ItemStack(id, 1, 3));
		tabList.add(new ItemStack(id, 1, 5));
		tabList.add(new ItemStack(id, 1, 6));
		tabList.add(new ItemStack(id, 1, 7));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int i = stack.getItemDamage();
		return super.getUnlocalizedName() + "." + i;
	}
}