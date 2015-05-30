package lasermod.compat.forgemultipart;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.TItemMultiPart;
import codechicken.multipart.TMultiPart;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;

/**
 * @author ProPercivalalb
 */
public class ItemSmallColourConverter extends ItemBlock implements TItemMultiPart {

	public ItemSmallColourConverter(Block block) {
		super(block);
	}
	
	@Override
	public double getHitDepth(Vector3 vec3, int side) {
	    return 0;
	}
	    
	@Override
	public TMultiPart newPart(ItemStack itemstack, EntityPlayer player, World world, BlockCoord blockCoord, int side, Vector3 vec3) {
		FMLLog.info("Create");
	    return MultiPartRegistry.createPart("lasermod:smallcolorconverter", FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT);
	}

}
