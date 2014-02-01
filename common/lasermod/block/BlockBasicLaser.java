package lasermod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.util.LaserUtil;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

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
		super(Material.field_151576_e);
		this.func_149711_c(1.0F);
		this.func_149647_a(CreativeTabs.tabBrewing);
	}

	@Override
	public TileEntity func_149915_a(World world, int meta) {
		return new TileEntityBasicLaser();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void func_149651_a(IIconRegister iconRegister) {
	    this.frontIcon = iconRegister.registerIcon("lasermod:basicLaserFront");
	    this.backIcon = iconRegister.registerIcon("lasermod:basicLaserBack");
	    this.sideIcon = iconRegister.registerIcon("lasermod:basicLaserSide");
	}
	    
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon func_149673_e(IBlockAccess world, int x, int y, int z, int side) {
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
	public IIcon func_149691_a(int side, int meta) {
	    int rotation = 3;

	    if (rotation > 5)
	        return this.frontIcon;
	    if (side == rotation)
	        return this.frontIcon;
	    else
	    	return side == Facing.oppositeSide[rotation] ? this.backIcon : this.sideIcon;
	}
	
	@Override
	public void func_149689_a(World par1World, int x, int y, int z, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack) {
		 int rotation = determineOrientation(par1World, x, y, z, par5EntityLiving);
		 par1World.setBlockMetadataWithNotify(x, y, z, rotation, 2);
	}
	
	public static int determineOrientation(World par0World, int par1, int par2, int par3, EntityLivingBase par4EntityLivingBase) {
        if (MathHelper.abs((float)par4EntityLivingBase.posX - (float)par1) < 2.0F && MathHelper.abs((float)par4EntityLivingBase.posZ - (float)par3) < 2.0F) {
            double d0 = par4EntityLivingBase.posY + 1.82D - (double)par4EntityLivingBase.yOffset;

            if (d0 - (double)par2 > 2.0D) {
                return 1;
            }

            if ((double)par2 - d0 > 0.0D)
            {
                return 0;
            }
        }

        int l = MathHelper.floor_double((double)(par4EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        return l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0)));
    }
}
