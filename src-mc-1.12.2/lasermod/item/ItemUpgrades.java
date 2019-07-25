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
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if(this.isInCreativeTab(tab))
			for(int i = 0; i < 9; i++)
				items.add(new ItemStack(this, 1, i));
    }
	
	@Override
	public String getTranslationKey(ItemStack stack) {
	    int i = stack.getItemDamage();
	    return super.getTranslationKey() + "." + i;
	}
}
