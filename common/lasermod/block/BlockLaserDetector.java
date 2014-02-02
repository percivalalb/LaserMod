package lasermod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lasermod.LaserMod;
import lasermod.api.ILaserReciver;
import lasermod.api.LaserInGame;
import lasermod.tileentity.TileEntityLaserDetector;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class BlockLaserDetector extends BlockContainer {

	@SideOnly(Side.CLIENT)
	public IIcon stateOff;
	
	public BlockLaserDetector() {
		super(Material.field_151576_e);
		this.func_149711_c(1.0F);
		this.func_149647_a(CreativeTabs.tabBrewing);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_149651_a(IIconRegister iconRegister) {
	    this.field_149761_L = iconRegister.registerIcon("lasermod:detector");
	    this.stateOff = iconRegister.registerIcon("lasermod:detector_off");
	}

	@Override
    @SideOnly(Side.CLIENT)
    public IIcon func_149691_a(int side, int meta) {
		if(meta == 0) 
			return this.stateOff;
        return this.field_149761_L;
    }
	
	@Override
	public TileEntity func_149915_a(World world, int meta) {
		return new TileEntityLaserDetector();
	}

	@Override
	public boolean func_149744_f() {
		return true;
	}

	@Override
	public int func_149709_b(IBlockAccess world, int x, int y, int z, int side) {
	    return world.getBlockMetadata(x, y, z) != 0 ? 15 : 0;
	}
}
