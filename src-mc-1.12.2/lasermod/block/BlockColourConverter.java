package lasermod.block;

import lasermod.LaserMod;
import lasermod.network.PacketDispatcher;
import lasermod.network.packet.client.ColourConverterMessage;
import lasermod.tileentity.TileEntityColourConverter;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class BlockColourConverter extends BlockPoweredLaser {
	
	public static final PropertyEnum<EnumDyeColor> COLOUR = PropertyEnum.create("colour", EnumDyeColor.class);
	
	public BlockColourConverter() {
		super(Material.ROCK);
		this.setHardness(1.0F);
		this.setCreativeTab(LaserMod.TAB_LASER);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) { 
		return new TileEntityColourConverter(); 
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		if(stack.getItem() == Items.DYE) {
			if(!world.isRemote) {
				TileEntityColourConverter colourConverter = (TileEntityColourConverter)world.getTileEntity(pos);
				EnumDyeColor colour = EnumDyeColor.byDyeDamage(stack.getItemDamage());
				
				if(colour == colourConverter.colour) return true;
				
				colourConverter.colour = colour;
				if(!player.capabilities.isCreativeMode) stack.shrink(1);
				
				PacketDispatcher.sendToAllTracking(new ColourConverterMessage(colourConverter), colourConverter);
			}
			return true;
		}
		return false;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {FACING, POWERED});
	}
	
	/**
	@Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        if(te instanceof TileEntityColourConverter) {
        	TileEntityColourConverter colourConverter = (TileEntityColourConverter)te;
            return state.withProperty(COLOUR, EnumDyeColor.byMetadata(colourConverter.colour));
        }
        return super.getExtendedState(state, world, pos);
    }**/
}
