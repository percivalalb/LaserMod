package lasermod.item;

import java.util.List;

import lasermod.LaserMod;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class ItemUpgrades extends Item {

	public IIcon upgradeFire;
	public IIcon upgradeWater;
	public IIcon upgradeIce;
	public IIcon upgradeInvisible;
	public IIcon upgradeMine;
	public IIcon upgradePush;
	public IIcon upgradePull;
	public IIcon upgradeDamage;
	public IIcon upgradeHealing;
	
	public ItemUpgrades() {
		this.setCreativeTab(LaserMod.tabLaser);
		this.setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
	    this.upgradeFire = iconRegister.registerIcon("lasermod:upgradeFire");
	    this.upgradeWater = iconRegister.registerIcon("lasermod:upgradeWater");
	    this.upgradeIce = iconRegister.registerIcon("lasermod:upgradeIce");
	    this.upgradeInvisible = iconRegister.registerIcon("lasermod:upgradeInvisable");
	    this.upgradeMine = iconRegister.registerIcon("lasermod:upgradeMine");
	    this.upgradePush = iconRegister.registerIcon("lasermod:upgradePush");
	    this.upgradePull = iconRegister.registerIcon("lasermod:upgradePull");
	    this.upgradeDamage = iconRegister.registerIcon("lasermod:upgradeDamage");
	    this.upgradeHealing = iconRegister.registerIcon("lasermod:upgradeHealing");
	}
	
	@SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
		if(meta == 0) return this.upgradeFire;
		if(meta == 1) return this.upgradeWater;
		if(meta == 2) return this.upgradeIce;
		if(meta == 3) return this.upgradeInvisible;
		if(meta == 4) return this.upgradeMine;
		if(meta == 5) return this.upgradePush;
		if(meta == 6) return this.upgradePull;
		if(meta == 7) return this.upgradeDamage;
		if(meta == 8) return this.upgradeHealing;
        return this.itemIcon;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, List tabList) {
        tabList.add(new ItemStack(item, 1, 0));
        tabList.add(new ItemStack(item, 1, 1));
        tabList.add(new ItemStack(item, 1, 2));
        tabList.add(new ItemStack(item, 1, 3));
        tabList.add(new ItemStack(item, 1, 4));
        tabList.add(new ItemStack(item, 1, 5));
        tabList.add(new ItemStack(item, 1, 6));
        tabList.add(new ItemStack(item, 1, 7));
        tabList.add(new ItemStack(item, 1, 8));
    }
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
	    int i = stack.getItemDamage();
	    return super.getUnlocalizedName() + "." + i;
	}
}
