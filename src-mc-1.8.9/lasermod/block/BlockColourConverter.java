package lasermod.block;

import lasermod.LaserMod;
import lasermod.network.PacketDispatcher;
import lasermod.network.packet.client.ColourConverterMessage;
import lasermod.tileentity.TileEntityColourConverter;
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
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class BlockColourConverter extends BlockContainer {
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	
	public BlockColourConverter() {
		super(Material.rock);
		this.setHardness(1.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setCreativeTab(LaserMod.tabLaser);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) { 
		return new TileEntityColourConverter(); 
	}
	
	@Override
	public int getRenderType() {
		return 3;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack item = player.getCurrentEquippedItem();
		if(!world.isRemote && item != null) {
			if(item.getItem() == Items.DYE) {
				TileEntityColourConverter colourConverter = (TileEntityColourConverter)world.getTileEntity(pos);
				
				int colour = 15 - item.getItemDamage();
				if(colour > 15) colour = 15;
				else if(colour < 0) colour = 0;
				
				if(colour == colourConverter.colour) return true;
				
				colourConverter.colour = colour;
				if(!player.capabilities.isCreativeMode) item.stackSize--;
				if(item.stackSize <= 0) player.setCurrentItemOrArmor(0, (ItemStack)null);
				
				
				PacketDispatcher.sendToAllAround(new ColourConverterMessage(colourConverter), colourConverter, 512);
				
				return true;
			}
		}
		return false;
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, BlockPistonBase.getFacingFromEntity(worldIn, pos, placer).getOpposite());
    }

	@Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, BlockPistonBase.getFacingFromEntity(worldIn, pos, placer).getOpposite()), 2);
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
