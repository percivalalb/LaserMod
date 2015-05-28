package codechicken.multipart;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Vector3;

public interface TItemMultiPart {

	public double getHitDepth(Vector3 vec3, int side);
	public TMultiPart newPart(ItemStack itemstack, EntityPlayer player, World world, BlockCoord blockCoord, int side, Vector3 vec3);

}
