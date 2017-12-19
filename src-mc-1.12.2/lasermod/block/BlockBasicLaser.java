package lasermod.block;

import java.util.Random;

import lasermod.LaserMod;
import lasermod.api.ILaser;
import lasermod.api.LaserInGame;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.util.BlockActionPos;
import lasermod.util.LaserUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class BlockBasicLaser extends BlockPoweredRedstone {
	
	public BlockBasicLaser() {
		super(Material.ROCK);
		this.setHardness(1.0F);
		this.setCreativeTab(LaserMod.TAB_LASER);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) { 
		return new TileEntityBasicLaser(); 
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		if(state.getValue(FACING) == face) return BlockFaceShape.UNDEFINED;
        return BlockFaceShape.SOLID;
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
}
