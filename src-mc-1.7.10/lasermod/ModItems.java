package lasermod;
import net.minecraft.block.Block;
import lasermod.item.ItemLaserCrystal;
import lasermod.item.ItemLaserSeekingGoogles;
import lasermod.item.ItemScrewdriver;
import lasermod.item.ItemUpgrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.crafting.ShapedRecipes;;

/**
 * @author ProPercivalalb
 */
public class ModItems {
	
	public static Item laserCrystal;
	public static Item laserSeekingGoogles;
	public static Item screwdriver;
	public static Item upgrades;
	
	public static void init() {
		laserCrystal = new ItemLaserCrystal().setUnlocalizedName("lasermod.laserCrystal");
		laserSeekingGoogles = new ItemLaserSeekingGoogles().setUnlocalizedName("lasermod.laserSeekingGoogles");
		screwdriver = new ItemScrewdriver().setUnlocalizedName("lasermod.screwdriver");
		upgrades = new ItemUpgrades().setUnlocalizedName("lasermod.upgrade");
		
		GameRegistry.registerItem(laserCrystal, "laserCrystal");
		GameRegistry.registerItem(laserSeekingGoogles, "laserSeekingGoogles");
		GameRegistry.registerItem(screwdriver, "screwdriver");
		GameRegistry.registerItem(upgrades, "upgrades");

		ItemStack glassStack = new ItemStack(Item.getItemFromBlock(Block.getBlockFromName("glass")));
		ItemStack redstoneStack = new ItemStack(Item.getItemById(331));

		GameRegistry.addRecipe(new ItemStack(laserCrystal),
		" y ",
		"yxy",
		" y ",
		'x', glassStack, 'y', redstoneStack);
	}
}
