package lasermod.compat.forgemultipart;

import lasermod.ModBlocks;
import lasermod.api.ILaserProvider;
import lasermod.api.ILaserReceiver;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * @author ProPercivalalb
 */
public class ForgeMultipartCompat {

	public static void registerBlock() {
		GameRegistry.registerBlock(ModBlocks.smallColourConverter, ItemSmallColourConverter.class, "smallcolorconverter");
	}
	
	public static void init() {
		PartRegister part = new PartRegister();
		codechicken.multipart.MultiPartRegistry.registerConverter(part);
		codechicken.multipart.MultiPartRegistry.registerParts(part, new String[] {"lasermod:smallcolorconverter"});
	}

	public static boolean isTileMultipart(TileEntity tileEntity, int side) {
		if(tileEntity instanceof codechicken.multipart.TileMultipart) {
			codechicken.multipart.TileMultipart tem = (codechicken.multipart.TileMultipart)tileEntity;

            for(codechicken.multipart.TMultiPart t : tem.jPartList()) {
                if(t instanceof SmallColourConverterPart) {
                	if(((SmallColourConverterPart) t).meta == side) {
                		return true;
                	}
                }
            }
		}
        return false;
	}
	
	public static ILaserReceiver getLaserReceiverFromPart(TileEntity tileEntity, int side) {
		if(tileEntity instanceof codechicken.multipart.TileMultipart) {
			codechicken.multipart.TileMultipart tem = (codechicken.multipart.TileMultipart)tileEntity;

            for(codechicken.multipart.TMultiPart t : tem.jPartList()) {
                if(t instanceof SmallColourConverterPart) {
                	if(((SmallColourConverterPart) t).meta == side)
                		return (SmallColourConverterPart)t;
                }
            }
		}
        return null;
	}
	
	public static ILaserProvider getLaserProviderFromPart(TileEntity tileEntity, int side) {
		if(tileEntity instanceof codechicken.multipart.TileMultipart) {
			codechicken.multipart.TileMultipart tem = (codechicken.multipart.TileMultipart)tileEntity;

            for(codechicken.multipart.TMultiPart t : tem.jPartList()) {
                if(t instanceof SmallColourConverterPart) {
                	if(((SmallColourConverterPart) t).meta == side)
                		return (SmallColourConverterPart)t;
                }
            }
		}
        return null;
	}
}
