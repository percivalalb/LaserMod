package lasermod.compat.forgemultipart;

import lasermod.ModBlocks;
import codechicken.multipart.MultiPartRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * @author ProPercivalalb
 */
public class ForgeMultipartCompat {

	public static void init() {
		PartRegister part = new PartRegister();
		MultiPartRegistry.registerConverter(part);
		MultiPartRegistry.registerParts(part, new String[] {"lasermod:smallcolorconverter"});
		GameRegistry.registerBlock(ModBlocks.smallColourConverter, ItemSmallColourConverter.class, "smallcolorconverter");
	}
}
