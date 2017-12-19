package lasermod.client;

import lasermod.ModBlocks;
import lasermod.ModItems;
import lasermod.client.model.block.ModelHelper;
import lasermod.lib.Reference;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber(modid = Reference.MOD_ID)
public class ModModels {
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void setItemModels(ModelRegistryEvent event) {
		ModelHelper.setDefaultModel(ModBlocks.LASER_BASIC);
		ModelHelper.setDefaultModel(ModBlocks.LASER_ADVANCED);
		ModelHelper.setDefaultModel(ModBlocks.LASER_DETECTOR);
		ModelHelper.setDefaultModel(ModBlocks.REFLECTOR);
		ModelHelper.setDefaultModel(ModBlocks.COLOUR_CONVERTER);
		ModelHelper.setDefaultModel(ModBlocks.COLOUR_CONVERTER_SMALL);
		ModelHelper.setDefaultModel(ModBlocks.LUMINOUS_LAMP);
		
		ModelHelper.setDefaultModel(ModItems.SCREWDRIVER);
		ModelHelper.setDefaultModel(ModItems.HANDHELD_SENSOR);
		ModelHelper.setDefaultModel(ModItems.LASER_CRYSTAL);
		ModelHelper.setDefaultModel(ModItems.LASER_SEEKING_GOOGLES);
		ModelHelper.setModel(ModItems.UPGRADES, 0, Reference.MOD_ID + ":upgrade_fire");
		ModelHelper.setModel(ModItems.UPGRADES, 1, Reference.MOD_ID + ":upgrade_water");
		ModelHelper.setModel(ModItems.UPGRADES, 2, Reference.MOD_ID + ":upgrade_ice");
		ModelHelper.setModel(ModItems.UPGRADES, 3, Reference.MOD_ID + ":upgrade_invisible");
		ModelHelper.setModel(ModItems.UPGRADES, 4, Reference.MOD_ID + ":upgrade_mine");
		ModelHelper.setModel(ModItems.UPGRADES, 5, Reference.MOD_ID + ":upgrade_push");
		ModelHelper.setModel(ModItems.UPGRADES, 6, Reference.MOD_ID + ":upgrade_pull");
		ModelHelper.setModel(ModItems.UPGRADES, 7, Reference.MOD_ID + ":upgrade_damage");
		ModelHelper.setModel(ModItems.UPGRADES, 8, Reference.MOD_ID + ":upgrade_heal");
	}
	
}
