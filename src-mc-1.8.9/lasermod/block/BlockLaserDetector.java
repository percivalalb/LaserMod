package lasermod.block;

import lasermod.LaserMod;
import lasermod.tileentity.TileEntityLaserDetector;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class BlockLaserDetector extends BlockContainer {

	public BlockLaserDetector() {
		super(Material.rock);
		this.setHardness(1.0F);
		this.setCreativeTab(LaserMod.tabLaser);
	}

	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) { return new TileEntityLaserDetector(); }

	@Override
	public boolean canProvidePower() { return true;	}

	@Override
	public int getWeakPower(IBlockAccess world, BlockPos pos, IBlockState state, EnumFacing side) {
		TileEntityLaserDetector detector = (TileEntityLaserDetector)world.getTileEntity(pos);
	    return detector.noLaserInputs() ? 0 : 15;
	}
	
	@Override
	public int getRenderType() {
		return 3;
	}
}
