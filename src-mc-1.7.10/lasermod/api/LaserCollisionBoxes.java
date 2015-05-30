package lasermod.api;

import java.util.ArrayList;

/**
 * @author ProPercivalalb
 */
public class LaserCollisionBoxes {

	public static ArrayList<LaserToRender> lasers = new ArrayList<LaserToRender>();
	public static ArrayList<LaserToRender> lasers2 = new ArrayList<LaserToRender>();
	
	public static void addLaserCollision(LaserToRender laserToRender) {
		if(!lasers.contains(laserToRender))
			lasers.add(laserToRender);
		else
			lasers.set(lasers.indexOf(laserToRender), laserToRender);
	}
}
