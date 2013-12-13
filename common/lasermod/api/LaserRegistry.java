package lasermod.api;

import java.util.Hashtable;
import java.util.Set;

/**
 * @author ProPercivalalb
 */
public class LaserRegistry {

	private static Hashtable<String, ILaser> mappings = new Hashtable<String, ILaser>();
	
	public static void registerLaser(String id, ILaser laser) {
		if(!mappings.keySet().contains(id))
			mappings.put(id, laser);
	}
	
	public static ILaser getLaserFromId(String id) {
		return mappings.get(id);
	}
	
	public static String getIdFromLaser(ILaser laser) {
		Set<String> keySet = mappings.keySet();
		for(String id : keySet) {
			if(mappings.get(id) == laser)
				return id;
		}
		return "default";
	}
}
