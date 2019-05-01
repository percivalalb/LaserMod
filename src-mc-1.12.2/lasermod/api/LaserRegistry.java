package lasermod.api;

import java.util.Hashtable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * @author ProPercivalalb
 */
public class LaserRegistry {

	private static Hashtable<ResourceLocation, LaserType> mappingItems = new Hashtable<ResourceLocation, LaserType>();
	
	public static void registerItemToLaser(Item item, LaserType laser) {
		registerItemToLaser(ForgeRegistries.ITEMS.getKey(item), laser);
	}
	
	public static void registerItemToLaser(ResourceLocation resource, LaserType laser) {
		if(!mappingItems.containsKey(resource))
			mappingItems.put(resource, laser);
	}
	
	public static LaserType getLaserFromItem(ItemStack stack) {
		ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
		if(mappingItems.containsKey(id)) return mappingItems.get(id);
		return null;
	}
}
