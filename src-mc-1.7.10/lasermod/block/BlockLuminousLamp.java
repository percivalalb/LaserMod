package lasermod.block;

import java.util.Random;

import lasermod.LaserMod;
import lasermod.api.ILaserReceiver;
import lasermod.api.LaserInGame;
import lasermod.tileentity.TileEntityAdvancedLaser;
import lasermod.tileentity.TileEntityLaserDetector;
import lasermod.tileentity.TileEntityLuminousLamp;
import lasermod.util.LaserUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;
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
    public IIcon getIcon(int side, int meta) {
		if(meta == 0)
			return this.stateOff;
        return this.blockIcon;
    }
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		//TODO
    }
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
		//TODO
    }

	@Override
    public void updateTick(World world, int x, int y, int z, Random random) {
		//TODO
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityLuminousLamp();
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		TileEntityLuminousLamp panel = (TileEntityLuminousLamp)world.getTileEntity(x, y, z);
		if(meta == 1) {
			return 15;
		}
		return 0;
	}
	
	@Override
	public boolean isOpaqueCube() {
	    return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
	    return false;
	}
}
