package lasermod.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lasermod.LaserMod;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

/**
 * @author ProPercivalalb
 */
public class ItemUpgrades extends Item {

	public Icon upgradeFire;
	public Icon upgradeWater;
	public Icon upgradeIce;
	public Icon upgradeInvisable;
	public Icon upgradeMine;
	
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
	}
	
	@SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int meta) {
		if(meta == 0) return this.upgradeFire;
		if(meta == 1) return this.upgradeWater;
		if(meta == 2) return this.upgradeIce;
		if(meta == 3) return this.upgradeInvisable;
		if(meta == 4) return this.upgradeMine;
        return this.itemIcon;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(int id, CreativeTabs creativeTab, List tabList) {
        tabList.add(new ItemStack(id, 1, 0));
        tabList.add(new ItemStack(id, 1, 1));
        tabList.add(new ItemStack(id, 1, 2));
        tabList.add(new ItemStack(id, 1, 3));
        tabList.add(new ItemStack(id, 1, 4));
    }
}
