package lasermod.block;

import lasermod.api.ILaserReciver;
import lasermod.api.LaserInGame;
import lasermod.tileentity.TileEntityLaserDetector;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class BlockLaserDetector extends BlockContainer implements ILaserReciver {

	public BlockLaserDetector(int id) {
		super(id, Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityLaserDetector();
	}
	@Override
	public boolean isOpaqueCube() {
        return false;
    }

	@Override
    public boolean renderAsNormalBlock() {
        return false;
    }

	@Override
	public boolean canPassOnSide(World world, int blockX, int blockY, int blockZ, int orginX, int orginY, int orginZ, int side) {
		return world.getBlockMetadata(blockX, blockY, blockZ) != 1;
	}

	@Override
	public void passLaser(World world, int blockX, int blockY, int blockZ, int orginX, int orginY, int orginZ, LaserInGame laserInGame) {
		world.setBlockMetadataWithNotify(blockX, blockY, blockZ, 0, 3);
	}

	@Override
	public void removeLasersFromSide(World world, int blockX, int blockY, int blockZ, int orginX, int orginY, int orginZ, int side) {
		world.setBlockMetadataWithNotify(blockX, blockY, blockZ, 1, 3);
	}
}