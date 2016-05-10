package lasermod.block;

import lasermod.LaserMod;
import lasermod.tileentity.TileEntityLuminousLamp;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class BlockLuminousLamp extends BlockContainer {

	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	
	public BlockLuminousLamp() {
		super(Material.glass);
		this.setHardness(1.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ACTIVE, false));
		this.setCreativeTab(LaserMod.tabLaser);
	}
	

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityLuminousLamp(); 
	}
	
	@Override
	public int getLightValue(IBlockAccess world, BlockPos pos) {
		return this.getMetaFromState(world.getBlockState(pos)) * 15;
	}
	
	@Override
	public boolean isOpaqueCube() { 
		return false; 
	}
	
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}
	

	@SideOnly(Side.CLIENT)
    public IBlockState getStateForEntityRender(IBlockState state) {
        return this.getDefaultState().withProperty(ACTIVE, false);
    }

	@Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(ACTIVE, meta % 2 == 1);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(ACTIVE) ? 1 : 0;
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] {ACTIVE});
    }

	@Override
	public boolean isFullCube() {
		return false;
	}
	 
	@Override
	public int getRenderType() {
		return 3;
	}
}
