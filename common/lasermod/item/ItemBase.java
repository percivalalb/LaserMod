package lasermod.item;

import lasermod.LaserMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

/**
 * @author ProPercivalalb
 */
public class ItemBase extends Item {

	public String texture;
	
	public ItemBase(int id, String texture) {
		super(id);
		this.texture = texture;
		this.setCreativeTab(LaserMod.laserTab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
	    this.itemIcon = iconRegister.registerIcon("lasermod:" + texture);
	}
}
