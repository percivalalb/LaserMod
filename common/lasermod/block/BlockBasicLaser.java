package lasermod.block;

import lasermod.LaserMod;
import lasermod.tileentity.TileEntityBasicLaser;
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
		int meta = LaserUtil.getOrientation(world.getBlockMetadata(x, y, z));

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
	public void onBlockPlacedBy(World par1World, int x, int y, int z, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack) {
		 int rotation = BlockPistonBase.determineOrientation(par1World, x, y, z, par5EntityLiving);
		 par1World.setBlockMetadataWithNotify(x, y, z, rotation, 2);
	}
}
