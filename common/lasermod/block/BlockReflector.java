package lasermod.block;

import java.util.Arrays;

import lasermod.ModItems;
import lasermod.api.ILaserReciver;
import lasermod.api.LaserInGame;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.tileentity.TileEntityReflector;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

/**
 * @author ProPercivalalb
 */
public class BlockReflector extends BlockContainer {

	public BlockReflector() {
		super(Material.field_151576_e);
		this.func_149711_c(1.0F);
		this.func_149647_a(CreativeTabs.tabBrewing);
	}

	@Override
	public TileEntity func_149915_a(World world, int meta) {
		return new TileEntityReflector();
	}

	@Override
	public int func_149645_b() {
        return -1;
    }
	
	@Override
	public IIcon func_149691_a(int par1, int par2) {
	    int meta = 1;

	    if (meta > 5)
	        return this.field_149761_L;
	    if (par1 == meta)
	        return this.field_149761_L;
	    else
	    	return par1 == Facing.oppositeSide[meta] ? Blocks.planks.func_149691_a(2, 2) : Blocks.planks.func_149691_a(0, 1);
	}
	
	@Override
	public boolean func_149662_c() {
	    return false;
	}
	
	@Override
	public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer player, int side, float xHit, float yHit, float zHit) {
		ItemStack item = player.getCurrentEquippedItem();
		if(item != null && item.itemID == ModItems.screwdriver.itemID) {
			TileEntityReflector reflector = (TileEntityReflector)world.getBlockTileEntity(x, y, z);
			reflector.openSides[side] = !reflector.openSides[side];
			world.getChunkFromBlockCoords(x, z).setChunkModified();
			if(reflector.openSides[side]) {
				boolean flag = reflector.removeAllLasersFromSide(side);
				reflector.checkAllRecivers();
			}
			
			if(!world.isRemote)
				PacketDispatcher.sendPacketToAllAround(x + 0.5D, y + 0.5D, z + 0.5D, 512, world.provider.dimensionId, reflector.getDescriptionPacket());
			
			
			return true;
		}
        return false;
    }
}
