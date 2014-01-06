package lasermod.api;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import net.minecraft.item.ItemStack;

/**
 * @author ProPercivalalb
 */
public class LaserRegistry {
	private static Hashtable<String, ILaser> mappings = new Hashtable<String, ILaser>();
	private static Hashtable<List<Integer>, ILaser> mappingItems = new Hashtable<List<Integer>, ILaser>();

	public static void registerLaser(String id, ILaser laser) {
		if (!mappings.keySet().contains(id))
			mappings.put(id, laser);
	}

	public static void registerItemToLaser(int id, int meta, ILaser laser) {
		List<Integer> list = Arrays.asList(id, meta);
		if (!mappingItems.keySet().contains(list))
			mappingItems.put(list, laser);
	}

	public static ILaser getLaserFromItem(ItemStack stack) {
		List<Integer> list = Arrays.asList(stack.itemID, stack.getItemDamage());
		if (mappingItems.keySet().contains(list))
			return mappingItems.get(list);
		return null;
	}

	public static ILaser getLaserFromId(String id) {
		return mappings.get(id);
	}

	public static String getIdFromLaser(ILaser laser) {
		Set<String> keySet = mappings.keySet();
		for (String id : keySet) {
			if (mappings.get(id) == laser)
				return id;
		}
		return "default";
	}
}