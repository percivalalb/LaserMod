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
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class BlockBasicLaser extends BlockContainer {

	@SideOnly(Side.CLIENT)
	public IIcon frontIcon;
	@SideOnly(Side.CLIENT)
	public IIcon backIcon;
	@SideOnly(Side.CLIENT)
	public IIcon sideIcon;
	
	public BlockBasicLaser() {
		super(Material.rock);
		this.setHardness(1.0F);
		this.setCreativeTab(LaserMod.tabLaser);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityBasicLaser();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
	    this.frontIcon = iconRegister.registerIcon("lasermod:basicLaserFront");
	    this.backIcon = iconRegister.registerIcon("lasermod:basicLaserBack");
	    this.sideIcon = iconRegister.registerIcon("lasermod:basicLaserSide");
	}
	    
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		int meta = world.getBlockMetadata(x, y, z);

		if (meta > 5)
	        return this.frontIcon;
	    if (side == meta)
	        return this.frontIcon; 
	    else
	    	return side == Facing.oppositeSide[meta] ? this.backIcon : this.sideIcon;
    }
	    
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
	    int rotation = 3;

	    if (rotation > 5)
	        return this.frontIcon;
	    if (side == rotation)
	        return this.frontIcon;
	    else
	    	return side == Facing.oppositeSide[rotation] ? this.backIcon : this.sideIcon;
	}
	
	@Override
    public void updateTick(World world, int x, int y, int z, Random random) {
		TileEntityBasicLaser basiclaser = (TileEntityBasicLaser)world.getTileEntity(x, y, z);
    	
        if (!world.isBlockIndirectlyGettingPowered(x, y, z)) {
        	BlockActionPos reciver = LaserUtil.getFirstBlock(basiclaser, ForgeDirection.getOrientation(basiclaser.getBlockMetadata()));
    		if(reciver != null && reciver.isLaserReceiver(ForgeDirection.getOrientation(basiclaser.getBlockMetadata()))) {
    			reciver.getLaserReceiver(ForgeDirection.getOrientation(basiclaser.getBlockMetadata())).removeLasersFromSide(world, x, y, z, ForgeDirection.getOrientation(basiclaser.getBlockMetadata()).getOpposite());
    		}
        }
        else if (world.isBlockIndirectlyGettingPowered(x, y, z)) {
    		BlockActionPos reciver = LaserUtil.getFirstBlock(basiclaser, ForgeDirection.getOrientation(basiclaser.getBlockMetadata()));
    		if(reciver != null && reciver.isLaserReceiver(ForgeDirection.getOrientation(basiclaser.getBlockMetadata()))) {
    		  	
    		  	LaserInGame laserInGame = basiclaser.getOutputLaser(ForgeDirection.getOrientation(basiclaser.getBlockMetadata()));

    		  	if(reciver.getLaserReceiver(ForgeDirection.getOrientation(basiclaser.getBlockMetadata())).canPassOnSide(world, x, y, z, ForgeDirection.getOrientation(basiclaser.getBlockMetadata()).getOpposite(), laserInGame))
    		  		reciver.getLaserReceiver(ForgeDirection.getOrientation(basiclaser.getBlockMetadata())).passLaser(world, x, y, z, ForgeDirection.getOrientation(basiclaser.getBlockMetadata()).getOpposite(), laserInGame);
    		}
    		else if(reciver != null) {
    			LaserInGame laserInGame = basiclaser.getOutputLaser(ForgeDirection.getOrientation(basiclaser.getBlockMetadata()));
    			
    			for(ILaser laser : laserInGame.getLaserType())
    				laser.actionOnBlock(reciver);
    		}
        }
	}
	
	@Override
	public void onBlockPlacedBy(World par1World, int x, int y, int z, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack) {
		 int rotation = BlockPistonBase.determineOrientation(par1World, x, y, z, par5EntityLiving);
		 par1World.setBlockMetadataWithNotify(x, y, z, rotation, 2);
	}
}
