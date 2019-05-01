package lasermod.api;

import java.util.ArrayList;

/**
 * @author ProPercivalalb
 */
public class LaserCollisionBoxes {

	// Lasers that are required to be rendered at added to this list
	public static ArrayList<LaserToRender> RENDER_LASERS = new ArrayList<>();
	// Once lasers have been rendered they will be copied here for onscreen info
	public static ArrayList<LaserToRender> INFO_LASERS = new ArrayList<>();
	
	public static void addLaserCollision(LaserToRender laserToRender) {
		if(!RENDER_LASERS.contains(laserToRender))
			RENDER_LASERS.add(laserToRender);
		else
			RENDER_LASERS.set(RENDER_LASERS.indexOf(laserToRender), laserToRender);
	}
}
