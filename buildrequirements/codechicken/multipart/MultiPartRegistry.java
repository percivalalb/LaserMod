package codechicken.multipart;

import codechicken.lib.vec.BlockCoord;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import lasermod.compat.forgemultipart.PartRegister;

public class MultiPartRegistry {

	public static void registerConverter(PartRegister part) {}
	public static void registerParts(PartRegister part, String[] strings) {}
	public static TMultiPart createPart(String string, boolean b) { return null; }

	public static interface IPartFactory {

		public Iterable<Block> blockTypes();

		public TMultiPart createPart(String name, boolean client);
		
	}
	
	public static interface IPartConverter {

		public TMultiPart convert(World world, BlockCoord pos);
		
	}
}
