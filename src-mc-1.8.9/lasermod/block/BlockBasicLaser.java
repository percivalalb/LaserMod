package lasermod.block;

import java.util.Random;

import lasermod.LaserMod;
import lasermod.api.ILaser;
import lasermod.api.LaserInGame;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.util.BlockActionPos;
import lasermod.util.LaserUtil;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
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
public class BlockBasicLaser extends BlockContainer {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	
	public BlockBasicLaser() {
		super(Material.rock);
		this.setHardness(1.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setCreativeTab(LaserMod.tabLaser);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) { 
		return new TileEntityBasicLaser(); 
	}
	
	/**
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {

		TileEntityBasicLaser basiclaser = (TileEntityBasicLaser)world.getTileEntity(pos);
    	
        if (world.isBlockIndirectlyGettingPowered(pos) == 0) {
        	BlockActionPos reciver = LaserUtil.getFirstBlock(basiclaser, EnumFacing.getFront(basiclaser.getBlockMetadata()));
    		if(reciver != null && reciver.isLaserReceiver(EnumFacing.getFront(basiclaser.getBlockMetadata()))) {
    			reciver.getLaserReceiver(EnumFacing.getFront(basiclaser.getBlockMetadata())).removeLasersFromSide(world, pos, EnumFacing.getFront(basiclaser.getBlockMetadata()).getOpposite());
    		}
        }
        else if (world.isBlockIndirectlyGettingPowered(pos) > 0) {
    		BlockActionPos reciver = LaserUtil.getFirstBlock(basiclaser, EnumFacing.getFront(basiclaser.getBlockMetadata()));
    		if(reciver != null && reciver.isLaserReceiver(EnumFacing.getFront(basiclaser.getBlockMetadata()))) {
    		  	
    			LaserInGame laserInGame = basiclaser.getOutputLaser(EnumFacing.getFront(basiclaser.getBlockMetadata()));
    		  	
    		  	if(reciver.getLaserReceiver(EnumFacing.getFront(basiclaser.getBlockMetadata())).canPassOnSide(world, pos, EnumFacing.getFront(basiclaser.getBlockMetadata()).getOpposite(), laserInGame))
    		  		reciver.getLaserReceiver(EnumFacing.getFront(basiclaser.getBlockMetadata())).passLaser(world, pos, EnumFacing.getFront(basiclaser.getBlockMetadata()).getOpposite(), laserInGame);
    		}
    		else if(reciver != null) {
    			LaserInGame laserInGame = basiclaser.getOutputLaser(EnumFacing.getFront(basiclaser.getBlockMetadata()));
    			
    			for(ILaser laser : laserInGame.getLaserType()) laser.actionOnBlock(reciver);
    		}
        }
	}**/
	
	@Override
	public int getRenderType() {
		return 3;
	}
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, BlockPistonBase.getFacingFromEntity(worldIn, pos, placer));
    }

	@Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, BlockPistonBase.getFacingFromEntity(worldIn, pos, placer)), 2);
    }
	
	@Override
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
