package lasermod.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class ItemScrewdriver extends ItemBase {

	public ItemScrewdriver(int id, String texture) {
		super(id, texture);
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List lore, boolean detailed) {
		//lore.add(EnumChatFormatting.GREEN + "Laser best friend");
	}

}
