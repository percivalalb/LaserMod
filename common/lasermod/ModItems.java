package lasermod;

import net.minecraft.item.Item;

/**
 * @author ProPercivalalb
 */
public class ModItems {
	
	public static Item screwdriver;
	
	public static void inti() {
		screwdriver = new Item().setUnlocalizedName("lasermod.screwdriver").setTextureName("lasermod:screwdriver");
	}
}
