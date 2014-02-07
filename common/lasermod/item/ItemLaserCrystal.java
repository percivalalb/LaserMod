package lasermod.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * @author ProPercivalalb
 */
public class ItemLaserCrystal extends Item {
	
	public ItemLaserCrystal() {
		super();
		this.setCreativeTab(LaserMod.tabLaser);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
	    this.itemIcon = iconRegister.registerIcon("lasermod:laserCrystal");
	}
}
