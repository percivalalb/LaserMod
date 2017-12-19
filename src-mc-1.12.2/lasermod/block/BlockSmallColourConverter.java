package lasermod.block;

import lasermod.LaserMod;
import lasermod.network.PacketDispatcher;
import lasermod.network.packet.client.SmallColourConverterMessage;
import lasermod.tileentity.TileEntitySmallColourConverter;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class BlockSmallColourConverter extends BlockContainer {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	protected static final AxisAlignedBB AABB_1 = new AxisAlignedBB(0.2F, 0.8F, 0.2F, 0.8F, 1.0F, 0.8F);
	protected static final AxisAlignedBB AABB_2 = new AxisAlignedBB(0.2F, 0.0F, 0.2F, 0.8F, 0.2F, 0.8F);
	protected static final AxisAlignedBB AABB_3 = new AxisAlignedBB(0.2F, 0.2F, 0.0F, 0.8F, 0.8F, 0.2F);
	protected static final AxisAlignedBB AABB_4 = new AxisAlignedBB(0.2F, 0.2F, 0.8F, 0.8F, 0.8F, 1.0F);
	protected static final AxisAlignedBB AABB_5 = new AxisAlignedBB(0.0F, 0.2F, 0.2F, 0.2F, 0.8F, 0.8F);
	protected static final AxisAlignedBB AABB_6 = new AxisAlignedBB(0.8F, 0.2F, 0.2F, 1.0F, 0.8F, 0.8F);
	protected static final AxisAlignedBB AABB_7 = new AxisAlignedBB(0.4F, 0.4F, 0.4F, 0.6F, 0.6F, 0.6F);
	
	public BlockSmallColourConverter() {
		super(Material.ROCK);
		this.setHardness(1.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setCreativeTab(LaserMod.TAB_LASER);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) { return new TileEntitySmallColourConverter();	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch(state.getValue(FACING)) {
		case DOWN: 	return AABB_1;
		case UP: 	return AABB_2;
		case SOUTH: return AABB_3;
		case NORTH: return AABB_4;
		case EAST: 	return AABB_5;
		case WEST: 	return AABB_6;
		default: return AABB_7;
		}
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing face) {
		return world.isSideSolid(pos.offset(face.getOpposite()), face);
    }

	@Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return (world.isSideSolid(pos.west(), EnumFacing.EAST)) ||
               (world.isSideSolid(pos.east(), EnumFacing.WEST)) ||
               (world.isSideSolid(pos.north(), EnumFacing.SOUTH)) ||
               (world.isSideSolid(pos.south(), EnumFacing.NORTH)) ||
               (world.isSideSolid(pos.down(), EnumFacing.UP)) ||
               (world.isSideSolid(pos.up(), EnumFacing.DOWN));
    }
	
	
	protected boolean canBlockStay(IBlockAccess worldIn, BlockPos pos, EnumFacing facing) {
		return worldIn.isSideSolid(pos.offset(facing.getOpposite()), facing, true);
	}
	
	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
		IBlockState state = world.getBlockState(pos);
		
        EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

        if(!this.canBlockStay(world, pos, enumfacing)) {
        	if(world instanceof World) {
        		this.dropBlockAsItem((World)world, pos, state, 0);
        		((World)world).setBlockToAir(pos);
        	}
        }
        
        super.onNeighborChange(world, pos, neighbor);
    }
	
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		if(!world.isRemote && stack != null) {
			if(stack.getItem() == Items.DYE) {
				TileEntitySmallColourConverter colourConverter = (TileEntitySmallColourConverter)world.getTileEntity(pos);
				
				int colour = MathHelper.clamp(15 - stack.getItemDamage(), 0, 15);
				
				if(colour == colourConverter.colour) return true;
				
				colourConverter.colour = colour;
	      		if(!player.capabilities.isCreativeMode) stack.shrink(1);
				
				PacketDispatcher.sendToAllAround(new SmallColourConverterMessage(colourConverter), colourConverter, 512);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) { return false; }
	

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

	@Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)), 2);
    }
	
	@SideOnly(Side.CLIENT)
    public IBlockState getStateForEntityRender(IBlockState state) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
    }

	@Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {

        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }
    
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
    
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
	    return EnumBlockRenderType.MODEL;
	}
}
