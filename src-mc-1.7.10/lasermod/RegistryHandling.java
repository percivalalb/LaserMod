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
import cpw.mods.fml.common.registry.GameRegistry;

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
				
		GameRegistry.addRecipe(new ItemStack(ModItems.laserCrystal), " r ", "rgr", " r ", 'g', Blocks.glass, 'r', Items.redstone);
		GameRegistry.addRecipe(new ItemStack(ModItems.screwdriver, 1), new Object[] {"  I", "YB ", "BY ", 'B', new ItemStack(Items.dye, 1, 0), 'Y', new ItemStack(Items.dye, 1, 11), 'I', Items.iron_ingot});
		GameRegistry.addRecipe(new ItemStack(ModItems.screwdriver, 1), new Object[] {"  I", "BY ", "YB ", 'B', new ItemStack(Items.dye, 1, 0), 'Y', new ItemStack(Items.dye, 1, 11), 'I', Items.iron_ingot});
		GameRegistry.addRecipe(new ItemStack(ModItems.laserSeekingGoogles, 1), new Object[] {"I I", "I I", "GLG", 'G', Blocks.glass, 'L', ModItems.laserCrystal, 'I', Items.iron_ingot});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.advancedLaser, 1), new Object[] {"OGO", "SLS", "OGO", 'G', Blocks.glass, 'L', ModItems.laserCrystal, 'S', Items.iron_ingot, 'O', Blocks.obsidian});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.basicLaser, 1), new Object[] {"CGC", "GLG", "CGC", 'G', Blocks.glass, 'L', ModItems.laserCrystal, 'C', Blocks.cobblestone});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.reflector, 1), new Object[] {"CGC", "GSG", "CGC", 'G', Blocks.glass, 'C', Blocks.cobblestone, 'S', Items.glowstone_dust});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.colourConverter, 1), new Object[] {"CGC", "GLG", "CGC", 'G', Items.glowstone_dust, 'L', ModItems.laserCrystal, 'C', Blocks.cobblestone});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.laserDetector, 1), new Object[] {"SSS", "SBS", "SSS", 'B', Blocks.redstone_block, 'S', Blocks.stone});
		GameRegistry.addRecipe(new ItemStack(ModItems.handheldSensor, 1), new Object[] {" T ", "ICI", " I ", 'T', Blocks.redstone_torch, 'I', Items.iron_ingot, 'C', ModItems.laserCrystal});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.luminousLamp, 1), new Object[] {" G ", "GDG", " I ", 'G', Blocks.glass, 'D', Items.glowstone_dust});
				
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.colourConverter, 1, 0), new Object[] {ModBlocks.smallColourConverter});    
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.smallColourConverter, 1, 0), new Object[] {ModBlocks.colourConverter});
		
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.upgrades, 1, 0), new Object[] {Blocks.obsidian, Items.fire_charge, Items.blaze_powder, Items.glowstone_dust});
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.upgrades, 1, 3), new Object[] {Blocks.obsidian, Items.golden_carrot, Items.fermented_spider_eye, Items.glowstone_dust});
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.upgrades, 1, 5), new Object[] {Blocks.obsidian, Blocks.piston, Items.feather, Items.glowstone_dust});
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.upgrades, 1, 6), new Object[] {Blocks.obsidian, Blocks.sticky_piston, Items.feather, Items.glowstone_dust});
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.upgrades, 1, 7), new Object[] {Blocks.obsidian, Items.golden_sword, Items.spider_eye, Items.glowstone_dust});
		
		// List of blocks lasers can go through.
		Block[] blocksWhitelistPassThrough = new Block[] { Blocks.air, Blocks.ice, Blocks.tallgrass, Blocks.double_plant, Blocks.leaves, 
				Blocks.leaves2, Blocks.lever, Blocks.torch, Blocks.ladder, Blocks.redstone_torch, Blocks.unlit_redstone_torch, 
				Blocks.redstone_wire, Blocks.glass, Blocks.glass_pane, Blocks.stained_glass, Blocks.stained_glass_pane };
		
		whitelistLaserPassThrough(blocksWhitelistPassThrough);
		whitelistLaserPassThrough("TConstruct:decoration.stonetorch");
		
		// List of blocks that cannot be mined with the mining laser.
		Block[] blocksBlacklistMiningLaser = new Block[] { ModBlocks.basicLaser, ModBlocks.advancedLaser, ModBlocks.colourConverter, 
				ModBlocks.laserDetector, ModBlocks.luminousLamp, ModBlocks.reflector, Blocks.bedrock, ModBlocks.basicLaser, 
				ModBlocks.advancedLaser, ModBlocks.colourConverter, ModBlocks.laserDetector, ModBlocks.luminousLamp, ModBlocks.reflector,
				ModBlocks.smallColourConverter };
		
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
	public static void whitelistLaserPassThrough(String blockName) { LaserModAPI.LASER_WHITELIST.addToList(blockName); }
	
	/**
	 * Registers a laser and it's associated item (always ModItems.upgrade).
	 * @param name Laser's unique name;
	 * @param laser The laser itself (must implement ILaser);
	 * @param meta The meta of the upgrade;
	 */
	public static void registerLaser(String name, ILaser laser, int meta) { registerLaser(name, laser, ModItems.upgrades, meta); }
	
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
