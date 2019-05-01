package lasermod.api;

import net.minecraftforge.registries.IForgeRegistry;

/**
 * @author ProPercivalalb
 */
public class LaserModAPI {
	
	public static BlockListBase LASER_BLACKLIST = new BlockListBase();
	public static BlockListBase LASER_WHITELIST = new BlockListBase();
	
	public static BlockListBase MINING_BLACKLIST = new BlockListBase();
	
	public static IForgeRegistry<LaserType> LASER_TYPES;
}
