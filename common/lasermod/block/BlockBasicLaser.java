package lasermod.block;

import lasermod.tileentity.TileEntityBasicLaser;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class BlockBasicLaser extends BlockContainer {

	protected BlockBasicLaser(int id) {
		super(id, Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBasicLaser();
	}

}
