package lasermod;

import lasermod.api.ILaser;
import lasermod.api.LaserModAPI;
import lasermod.api.LaserRegistry;
import lasermod.laser.DamageLaser;
import lasermod.laser.FireLaser;
import lasermod.laser.HealingLaser;
import lasermod.laser.IceLaser;
import lasermod.laser.InvisibleLaser;
import lasermod.laser.MiningLaser;
import lasermod.laser.PullLaser;
import lasermod.laser.PushLaser;
import lasermod.laser.WaterLaser;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class RegistryHandling {
	
	public static void init() {
		//Registers all lasers
		registerLaser("fire", new FireLaser(), 0);
		registerLaser("water", new WaterLaser(), 1);
		registerLaser("ice", new IceLaser(), 2);
		registerLaser("invisible", new InvisibleLaser(), 3);
		registerLaser("mining", new MiningLaser(), 4);
		registerLaser("push", new PushLaser(), 5);
		registerLaser("pull", new PullLaser(), 6);
		registerLaser("damage", new DamageLaser(), 7);
		registerLaser("healing", new HealingLaser(), 8);
		
		// List of blocks lasers can go through.
		Block[] blocksWhitelistPassThrough = new Block[] { Blocks.AIR, Blocks.ICE, Blocks.TALLGRASS, Blocks.DOUBLE_PLANT, Blocks.LEAVES, 
				Blocks.LEAVES2, Blocks.LEVER, Blocks.TORCH, Blocks.LADDER, Blocks.REDSTONE_TORCH, Blocks.UNLIT_REDSTONE_TORCH, 
				Blocks.REDSTONE_WIRE, Blocks.GLASS, Blocks.GLASS_PANE, Blocks.STAINED_GLASS, Blocks.STAINED_GLASS_PANE };
		
		whitelistLaserPassThrough(blocksWhitelistPassThrough);
		whitelistLaserPassThrough(new ResourceLocation("TConstruct:decoration.stonetorch"));
		
		// List of blocks that cannot be mined with the mining laser.
		Block[] blocksBlacklistMiningLaser = new Block[] { ModBlocks.LASER_BASIC, ModBlocks.LASER_ADVANCED, ModBlocks.COLOUR_CONVERTER, 
				ModBlocks.LASER_DETECTOR, ModBlocks.LUMINOUS_LAMP, ModBlocks.REFLECTOR, Blocks.BEDROCK, ModBlocks.LASER_BASIC, 
				ModBlocks.LASER_ADVANCED, ModBlocks.COLOUR_CONVERTER, ModBlocks.LASER_DETECTOR, ModBlocks.LUMINOUS_LAMP, ModBlocks.REFLECTOR,
				ModBlocks.COLOUR_CONVERTER_SMALL };
		
		blacklistMiningLaser(blocksBlacklistMiningLaser);
	}
	
	/** Adds a list of blocks to the mining blacklist. **/
	public static void blacklistMiningLaser(Block[] blocks) { for (Block block : blocks) { blacklistMiningLaser(block); } }
	
	/** Adds block to the mining blacklist. **/
	public static void blacklistMiningLaser(Block block) { LaserModAPI.LASER_BLACKLIST.addToList(block); }
	
	/** Adds a list of blocks to the laser pass-through whitelist. **/
	public static void whitelistLaserPassThrough(Block[] blocks) { for (Block block : blocks) { whitelistLaserPassThrough(block); } }
	
	/** Adds block to the laser pass-through whitelist. **/
	public static void whitelistLaserPassThrough(Block block) { LaserModAPI.LASER_WHITELIST.addToList(block); }
	
	/** Adds block's name to the laser pass-through whitelist. **/
	public static void whitelistLaserPassThrough(ResourceLocation blockName) { LaserModAPI.LASER_WHITELIST.addToList(blockName); }
	
	/**
	 * Registers a laser and it's associated item (always ModItems.upgrade).
	 * @param name Laser's unique name;
	 * @param laser The laser itself (must implement ILaser);
	 * @param meta The meta of the upgrade;
	 */
	public static void registerLaser(String name, ILaser laser, int meta) { registerLaser(name, laser, ModItems.UPGRADES, meta); }
	
	/**
	 * Registers a laser and it's associated item.
	 * @param name Laser's unique name;
	 * @param laser The laser itself (must implement ILaser);
	 * @param itemUpgrade The upgrade to use on the emitter;
	 * @param meta The meta of the upgrade;
	 */
	public static void registerLaser(String name, ILaser laser, Item itemUpgrade, int meta) { 
		LaserRegistry.registerLaser(name, laser);
		LaserRegistry.registerItemToLaser(itemUpgrade, meta, laser); 
	}
}
