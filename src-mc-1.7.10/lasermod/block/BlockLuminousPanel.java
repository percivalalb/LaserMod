package lasermod.block;

import java.util.Random;

import lasermod.LaserMod;
import lasermod.api.ILaserReceiver;
import lasermod.api.LaserInGame;
import lasermod.tileentity.TileEntityAdvancedLaser;
import lasermod.tileentity.TileEntityLaserDetector;
import lasermod.tileentity.TileEntityLuminousPanel;
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
public class BlockLuminousPanel extends BlockContainer {

	
	public BlockLuminousPanel() {
		super(Material.glass);
		this.setHardness(1.0F);
		this.setCreativeTab(LaserMod.tabLaser);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
	    
	}

	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return Blocks.wool.getIcon(side, 0);
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
		return new TileEntityLuminousPanel();
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		
		if(meta > 5)
			return 15;
		
		return 0;
	}
}
