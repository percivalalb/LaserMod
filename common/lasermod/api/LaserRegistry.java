package lasermod.api;

import java.util.Hashtable;

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
}
