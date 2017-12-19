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
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
		
		//TODO Add recipes
		/**
		GameRegistry.addRecipe(new ItemStack(ModItems.LASER_CRYSTAL), " r ", "rgr", " r ", 'g', Blocks.glass, 'r', Items.REDSTONE);
		GameRegistry.addRecipe(new ItemStack(ModItems.SCREWDRIVER, 1), new Object[] {"  I", "YB ", "BY ", 'B', new ItemStack(Items.DYE, 1, 0), 'Y', new ItemStack(Items.DYE, 1, 11), 'I', Items.IRON_INGOT});
		GameRegistry.addRecipe(new ItemStack(ModItems.SCREWDRIVER, 1), new Object[] {"  I", "BY ", "YB ", 'B', new ItemStack(Items.DYE, 1, 0), 'Y', new ItemStack(Items.DYE, 1, 11), 'I', Items.IRON_INGOT});
		GameRegistry.addRecipe(new ItemStack(ModItems.LASER_SEEKING_GOOGLES, 1), new Object[] {"I I", "I I", "GLG", 'G', Blocks.glass, 'L', ModItems.LASER_CRYSTAL, 'I', Items.IRON_INGOT});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.LASER_ADVANCED, 1), new Object[] {"OGO", "SLS", "OGO", 'G', Blocks.glass, 'L', ModItems.LASER_CRYSTAL, 'S', Items.IRON_INGOT, 'O', Blocks.obsidian});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.LASER_BASIC, 1), new Object[] {"CGC", "GLG", "CGC", 'G', Blocks.glass, 'L', ModItems.LASER_CRYSTAL, 'C', Blocks.cobblestone});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.REFLECTOR, 1), new Object[] {"CGC", "GSG", "CGC", 'G', Blocks.glass, 'C', Blocks.cobblestone, 'S', Items.glowstone_dust});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.COLOUR_CONVERTER, 1), new Object[] {"CGC", "GLG", "CGC", 'G', Items.glowstone_dust, 'L', ModItems.LASER_CRYSTAL, 'C', Blocks.cobblestone});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.LASER_DETECTOR, 1), new Object[] {"SSS", "SBS", "SSS", 'B', Blocks.redstone_block, 'S', Blocks.stone});
		GameRegistry.addRecipe(new ItemStack(ModItems.HANDHELD_SENSOR, 1), new Object[] {" T ", "ICI", " I ", 'T', Blocks.redstone_torch, 'I', Items.IRON_INGOT, 'C', ModItems.LASER_CRYSTAL});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.LUMINOUS_LAMP, 1), new Object[] {" G ", "GDG", " I ", 'G', Blocks.glass, 'D', Items.glowstone_dust});
				
		//GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.colourConverter, 1, 0), new Object[] {ModBlocks.smallColourConverter});    
		//GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.smallColourConverter, 1, 0), new Object[] {ModBlocks.colourConverter});
		
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.UPGRADES, 1, 0), new Object[] {Blocks.obsidian, Items.fire_charge, Items.blaze_powder, Items.glowstone_dust});
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.UPGRADES, 1, 3), new Object[] {Blocks.obsidian, Items.golden_carrot, Items.fermented_spider_eye, Items.glowstone_dust});
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.UPGRADES, 1, 5), new Object[] {Blocks.obsidian, Blocks.piston, Items.feather, Items.glowstone_dust});
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.UPGRADES, 1, 6), new Object[] {Blocks.obsidian, Blocks.sticky_piston, Items.feather, Items.glowstone_dust});
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.UPGRADES, 1, 7), new Object[] {Blocks.obsidian, Items.golden_sword, Items.SPIDER_EYE, Items.glowstone_dust});
		**/
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
