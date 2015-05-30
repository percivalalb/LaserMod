package lasermod.block;

import java.util.Random;

import lasermod.LaserMod;
import lasermod.tileentity.TileEntityLaserDetector;
import lasermod.util.LaserUtil;
import net.minecraft.block.Block;
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
public class BlockLaserDetector extends BlockContainer {

	@SideOnly(Side.CLIENT)
	public IIcon stateOff;
	
	public BlockLaserDetector() {
		super(Material.rock);
		this.setHardness(1.0F);
		this.setCreativeTab(LaserMod.tabLaser);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
	    this.blockIcon = iconRegister.registerIcon("lasermod:detector");
	    this.stateOff = iconRegister.registerIcon("lasermod:detector_off");
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
        if (!world.isRemote) {
        	TileEntityLaserDetector laserdetector = (TileEntityLaserDetector)world.getTileEntity(x, y, z);
			
			if(laserdetector.getBlockMetadata() != 1)
				return;
			
			int count = 0;
			for(int i = 0; i < 6; ++i)
				if(!LaserUtil.isValidSourceOfPowerOnSide(laserdetector, i))
					count++;
			
			if(count == 6)
				world.setBlockMetadataWithNotify(x, y, z, 0, 3);
        }
    }
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
		if (!world.isRemote) {
			TileEntityLaserDetector laserdetector = (TileEntityLaserDetector)world.getTileEntity(x, y, z);
			
			if(laserdetector.getBlockMetadata() != 1)
				return;
			
			int count = 0;
			for(int i = 0; i < 6; ++i)
				if(!LaserUtil.isValidSourceOfPowerOnSide(laserdetector, i))
					count++;
			
			if(count == 6)
				world.setBlockMetadataWithNotify(x, y, z, 0, 3);
        }
    }

	@Override
    public void updateTick(World world, int x, int y, int z, Random random) {
		if (!world.isRemote) {
			TileEntityLaserDetector laserdetector = (TileEntityLaserDetector)world.getTileEntity(x, y, z);
			
			if(laserdetector.getBlockMetadata() != 1)
				return;
			
			int count = 0;
			for(int i = 0; i < 6; ++i)
				if(!LaserUtil.isValidSourceOfPowerOnSide(laserdetector, i))
					count++;
			
			if(count == 6)
				world.setBlockMetadataWithNotify(x, y, z, 0, 3);
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityLaserDetector();
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
	    return world.getBlockMetadata(x, y, z) != 0 ? 15 : 0;
	}
}
