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

@SideOnly(value = Side.CLIENT)
@EventBusSubscriber(modid = Reference.MOD_ID)
public class ModModels {
	
	@SubscribeEvent
	public static void setItemModels(ModelRegistryEvent event) {
		ModelHelper.setDefaultModel(ModBlocks.LASER_BASIC);
		ModelHelper.setDefaultModel(ModBlocks.LASER_ADVANCED);
		ModelHelper.setDefaultModel(ModBlocks.LASER_DETECTOR);
		ModelHelper.setDefaultModel(ModBlocks.REFLECTOR);
		ModelHelper.setDefaultModel(ModBlocks.COLOUR_CONVERTER);
		ModelHelper.setDefaultModel(ModBlocks.COLOUR_CONVERTER_SMALL);
		ModelHelper.setDefaultModel(ModBlocks.LUMINOUS_LAMP);
		ModelHelper.setDefaultModel(ModBlocks.LENS_WORKBENCH);
		
		ModelHelper.setDefaultModel(ModItems.SCREWDRIVER);
		ModelHelper.setDefaultModel(ModItems.HANDHELD_SENSOR);
		ModelHelper.setDefaultModel(ModItems.LASER_CRYSTAL);
		ModelHelper.setDefaultModel(ModItems.LASER_SEEKING_GOGGLES);
		ModelHelper.setDefaultModel(ModItems.UPGRADE_FIRE);
		ModelHelper.setDefaultModel(ModItems.UPGRADE_WATER);
		ModelHelper.setDefaultModel(ModItems.UPGRADE_ICE);
		ModelHelper.setDefaultModel(ModItems.UPGRADE_INVISIBLE);
		ModelHelper.setDefaultModel(ModItems.UPGRADE_MINING);
		ModelHelper.setDefaultModel(ModItems.UPGRADE_PUSH);
		ModelHelper.setDefaultModel(ModItems.UPGRADE_PULL);
		ModelHelper.setDefaultModel(ModItems.UPGRADE_DAMAGE);
		ModelHelper.setDefaultModel(ModItems.UPGRADE_HEAL);
	}
	
}
