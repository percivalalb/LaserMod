package lasermod.api;

import java.util.ArrayList;

import net.minecraft.util.AxisAlignedBB;

/**
 * @author ProPercivalalb
 */
public class LaserCollisionBoxes {

	public static ArrayList<AxisAlignedBB> lasers = new ArrayList<AxisAlignedBB>();
	
	public static void addLaserCollision(AxisAlignedBB aabb) {
		lasers.add(aabb);
	}
}
