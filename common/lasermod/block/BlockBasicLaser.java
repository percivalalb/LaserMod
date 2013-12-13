package lasermod.block;

import lasermod.tileentity.TileEntityBasicLaser;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class BlockBasicLaser extends BlockContainer {

	public BlockBasicLaser(int id) {
		super(id, Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBasicLaser();
	}

	public static int getOrientation(int par1) {
		return par1 & 7;
	}
	    
	@Override
	public Icon getIcon(int par1, int par2) {
	    int meta = getOrientation(par2);

	    if (meta > 5)
	        return this.blockIcon;

	    if (par1 == meta)
	    {
	        return this.blockIcon;
	    }
	    else
	    {
	    	return par1 == Facing.oppositeSide[meta] ? Block.planks.getIcon(2, 2) : Block.planks.getIcon(0, 1);
	    }
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
	    int rotation = 0;
	        
	    if (Math.abs(player.rotationPitch) > 90D / 2D)
	        if (player.rotationPitch > 0)
	            rotation = 1;
	        else if (player.rotationPitch < 0)
	            rotation = 0;
	        else
	            rotation = Direction.directionToFacing[Direction.rotateOpposite[Math.round(player.rotationYaw / 90) & 3]];
	        
	        
	        world.setBlockMetadataWithNotify(x, y, z, rotation, 2);
	    }
}
