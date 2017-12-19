package lasermod.item;

import lasermod.LaserMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class ItemUpgrades extends Item {

	public ItemUpgrades() {
		this.setCreativeTab(LaserMod.TAB_LASER);
		this.setHasSubtypes(true);
	}

	/**
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
    }**/
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		for(int i = 0; i < 9; i++)
			items.add(new ItemStack(this, 1, i));
    }
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
	    int i = stack.getItemDamage();
	    return super.getUnlocalizedName() + "." + i;
	}
}
