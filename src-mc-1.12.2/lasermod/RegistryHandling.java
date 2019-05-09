package lasermod;

import lasermod.api.LaserModAPI;
import lasermod.api.LaserRegistry;
import lasermod.api.LaserType;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class RegistryHandling {
	
	public static void init() {
		//Registers all lasers
		registerLaser(ModLasers.FIRE, ModItems.UPGRADE_FIRE);
		registerLaser(ModLasers.WATER, ModItems.UPGRADE_WATER);
		registerLaser(ModLasers.ICE, ModItems.UPGRADE_ICE);
		registerLaser(ModLasers.INVISIBLE, ModItems.UPGRADE_INVISIBLE);
		registerLaser(ModLasers.MINING, ModItems.UPGRADE_MINING);
		registerLaser(ModLasers.PUSH, ModItems.UPGRADE_PUSH);
		registerLaser(ModLasers.PULL, ModItems.UPGRADE_PULL);
		registerLaser(ModLasers.DAMAGE, ModItems.UPGRADE_DAMAGE);
		registerLaser(ModLasers.HEAL, ModItems.UPGRADE_HEAL);
		
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
	 * Registers a laser and it's associated item.
	 * @param laser The laser itself (must implement ILaser);
	 * @param itemUpgrade The upgrade to use on the emitter;
	 */
	public static void registerLaser(LaserType laser, Item itemUpgrade) { 
		LaserRegistry.registerItemToLaser(itemUpgrade, laser); 
	}
}
