package lasermod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lasermod.api.ILaser;
import lasermod.api.ILaserReciver;
import lasermod.api.LaserInGame;
import lasermod.api.LaserRegistry;
import lasermod.core.helper.LogHelper;
import lasermod.tileentity.TileEntityLaserDetector;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class BlockLaserDetector extends BlockContainer implements ILaserReciver {

	public Icon stateOff;
	
	public BlockLaserDetector(int id) {
		super(id, Material.rock);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
	    this.blockIcon = iconRegister.registerIcon("lasermod:detector");
	    this.stateOff = iconRegister.registerIcon("lasermod:detector_off");
	}

	@Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta) {
		if(meta == 0) 
			return this.stateOff;
        return this.blockIcon;
    }
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityLaserDetector();
	}
	@Override
	public boolean isOpaqueCube() {
        return false;
    }

	@Override
    public boolean renderAsNormalBlock() {
        return false;
    }

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
	    return world.getBlockMetadata(x, y, z) != 0 ? 15 : 0;
	}
	
	@Override
	public boolean canPassOnSide(World world, int blockX, int blockY, int blockZ, int orginX, int orginY, int orginZ, int side) {
		return world.getBlockMetadata(blockX, blockY, blockZ) != 1;
	}

	@Override
	public void passLaser(World world, int blockX, int blockY, int blockZ, int orginX, int orginY, int orginZ, LaserInGame laserInGame) {
		world.setBlockMetadataWithNotify(blockX, blockY, blockZ, 1, 3);
	}

	@Override
	public void removeLasersFromSide(World world, int blockX, int blockY, int blockZ, int orginX, int orginY, int orginZ, int side) {
		
	}

	@Override
	public boolean isSendingSignalFromSide(World world, int blockX, int blockY, int blockZ, int orginX, int orginY, int orginZ, int side) {
		return false;
	}
}
