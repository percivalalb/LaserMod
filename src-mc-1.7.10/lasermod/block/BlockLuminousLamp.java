package lasermod.block;

import lasermod.LaserMod;
import lasermod.tileentity.TileEntityLuminousLamp;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class BlockLuminousLamp extends BlockContainer {

	@SideOnly(Side.CLIENT)
	public IIcon stateOff;
	
	public BlockLuminousLamp() {
		super(Material.glass);
		this.setHardness(1.0F);
		this.setCreativeTab(LaserMod.tabLaser);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
	    this.blockIcon = iconRegister.registerIcon("lasermod:luminouslampon");
	    this.stateOff = iconRegister.registerIcon("lasermod:luminouslampoff");
	}

	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		TileEntityLuminousLamp luminousLamp = (TileEntityLuminousLamp)world.getTileEntity(x, y, z);
		return luminousLamp.noLaserInputs() ? this.stateOff : this.blockIcon;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) { return this.stateOff; }
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) { return new TileEntityLuminousLamp(); }
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		TileEntityLuminousLamp panel = (TileEntityLuminousLamp)world.getTileEntity(x, y, z);
		
		if(!panel.noLaserInputs()) return 15;
		return 0;
	}
	
	@Override
	public boolean isOpaqueCube() { return false; }
	
	@Override
	public boolean renderAsNormalBlock() { return false; }
}
