package lasermod.block;

import lasermod.LaserMod;
import lasermod.network.PacketDispatcher;
import lasermod.network.packet.client.SmallColourConverterMessage;
import lasermod.tileentity.TileEntitySmallColourConverter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class BlockSmallColourConverter extends BlockContainer {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	
	public BlockSmallColourConverter() {
		super(Material.rock);
		this.setHardness(1.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setCreativeTab(LaserMod.tabLaser);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) { return new TileEntitySmallColourConverter();	}

	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos) {
        int meta = this.getMetaFromState(world.getBlockState(pos));
        if(meta == EnumFacing.DOWN.ordinal()) this.setBlockBounds(0.2F, 0.8F, 0.2F, 0.8F, 1.0F, 0.8F);
        else if(meta == EnumFacing.UP.ordinal()) this.setBlockBounds(0.2F, 0.0F, 0.2F, 0.8F, 0.2F, 0.8F);
    	else if(meta == EnumFacing.SOUTH.ordinal()) this.setBlockBounds(0.2F, 0.2F, 0.0F, 0.8F, 0.8F, 0.2F);
       	else if(meta == EnumFacing.NORTH.ordinal())this.setBlockBounds(0.2F, 0.2F, 0.8F, 0.8F, 0.8F, 1.0F);
    	else if(meta == EnumFacing.EAST.ordinal()) this.setBlockBounds(0.0F, 0.2F, 0.2F, 0.2F, 0.8F, 0.8F);
       	else if(meta == EnumFacing.WEST.ordinal()) this.setBlockBounds(0.8F, 0.2F, 0.2F, 1.0F, 0.8F, 0.8F);
       	else this.setBlockBounds(0.4F, 0.4F, 0.4F, 0.6F, 0.6F, 0.6F);
    }

	@Override
    public void setBlockBoundsForItemRender() { 
		this.setBlockBounds(0.2F, 0.2F, 0.3F, 0.8F, 0.8F, 0.5F); 
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos pos, IBlockState state) {
		//this.setBlockBoundsBasedOnState(world, pos);
        return super.getCollisionBoundingBox(world, pos, state);
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos pos) {
		//this.setBlockBoundsBasedOnState(world, pos);
		return super.getSelectedBoundingBox(world, pos);
    }

	@Override
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing face) {
        EnumFacing dir = face;
        return (dir == EnumFacing.NORTH && world.isSideSolid(pos.south(), EnumFacing.NORTH)) ||
               (dir == EnumFacing.SOUTH && world.isSideSolid(pos.north(), EnumFacing.SOUTH)) ||
               (dir == EnumFacing.WEST  && world.isSideSolid(pos.east(), EnumFacing.WEST)) ||
               (dir == EnumFacing.EAST  && world.isSideSolid(pos.west(), EnumFacing.EAST)) ||
               (dir == EnumFacing.UP  && world.isSideSolid(pos.down(), EnumFacing.UP)) ||
               (dir == EnumFacing.DOWN  && world.isSideSolid(pos.up(), EnumFacing.DOWN));
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
	
	
	protected boolean canBlockStay(World worldIn, BlockPos pos, EnumFacing facing) {
		return worldIn.isSideSolid(pos.offset(facing.getOpposite()), facing, true);
	}
	
	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
    {
        EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

        if(!this.canBlockStay(worldIn, pos, enumfacing))
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }

        super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
    }
	
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getCurrentEquippedItem();
		if(!world.isRemote && stack != null) {
			if(stack.getItem() == Items.DYE) {
				TileEntitySmallColourConverter colourConverter = (TileEntitySmallColourConverter)world.getTileEntity(pos);
				
				int colour = MathHelper.clamp_int(15 - stack.getItemDamage(), 0, 15);
				
				if(colour == colourConverter.colour) return true;
				
				colourConverter.colour = colour;
	      		if(!player.capabilities.isCreativeMode && --stack.stackSize <= 0) player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
				
				PacketDispatcher.sendToAllAround(new SmallColourConverterMessage(colourConverter), colourConverter, 512);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() { return false; }
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, facing);
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
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] {FACING});
    }
}
