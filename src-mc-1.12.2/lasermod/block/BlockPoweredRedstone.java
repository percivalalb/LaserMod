package lasermod.block;

import java.util.Random;

import lasermod.LaserMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockPoweredRedstone extends BlockContainer {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	
	public BlockPoweredRedstone(Material material) {
		super(material);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false));
		this.setCreativeTab(LaserMod.TAB_LASER);
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
	    return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

	@Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)).withProperty(POWERED, worldIn.isBlockPowered(pos)), 2);
    }

	@Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta)).withProperty(POWERED, Boolean.valueOf((meta & 8) > 0));
    }

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = 0;
		meta = meta | state.getValue(FACING).getIndex();

		if(state.getValue(POWERED).booleanValue())
			meta |= 8;

		return meta;
	}

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {FACING, POWERED});
    }
    
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if(!worldIn.isRemote) {
			if(state.getValue(POWERED) && !worldIn.isBlockPowered(pos))
				worldIn.scheduleUpdate(pos, this, 4);
			else if(!state.getValue(POWERED) && worldIn.isBlockPowered(pos))
				cycleState(worldIn, pos, state);
		}
	}

    @Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		if(!world.isRemote)
			if(state.getValue(POWERED) && !world.isBlockPowered(pos))
				cycleState(world, pos, state);
			else if(!state.getValue(POWERED) && world.isBlockPowered(pos))
				cycleState(world, pos, state);

	}
    
    public static void cycleState(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        
        worldIn.setBlockState(pos, state.cycleProperty(POWERED), 3);

        if(tileentity != null){
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }
}
