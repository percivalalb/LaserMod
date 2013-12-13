package lasermod.block;

import lasermod.ModItems;
import lasermod.api.ILaserReciver;
import lasermod.tileentity.TileEntityReflector;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class BlockReflector extends BlockContainer implements ILaserReciver {

	public BlockReflector(int id) {
		super(id, Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityReflector();
	}

	@Override
	public int getRenderType() {
        return -1;
    }
	
	@Override
	public Icon getIcon(int par1, int par2) {
	    int meta = 1;

	    if (meta > 5)
	        return this.blockIcon;

	    if (par1 == meta) {
	        return this.blockIcon;
	    }
	    else {
	    	return par1 == Facing.oppositeSide[meta] ? Block.planks.getIcon(2, 2) : Block.planks.getIcon(0, 1);
	    }
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xHit, float yHit, float zHit) {
		ItemStack item = player.getCurrentEquippedItem();
		if(item != null && item.itemID == ModItems.screwdriver.itemID) {
			TileEntityReflector reflector = (TileEntityReflector)world.getBlockTileEntity(x, y, z);
			reflector.openSides[side] = !reflector.openSides[side];
			if(player instanceof EntityPlayerMP) {
				EntityPlayerMP playerMP = (EntityPlayerMP)player;
				
				Packet packet = reflector.getDescriptionPacket();

	            if (packet != null) {
	                playerMP.playerNetServerHandler.sendPacketToPlayer(packet);
	            }
			}
			
			return true;
		}
        return false;
    }

	@Override
	public boolean canPassOnSide(World world, int blockX, int blockY, int blockZ, int orginX, int orginY, int orginZ, int side) {
		TileEntityReflector reflector = (TileEntityReflector)world.getBlockTileEntity(blockX, blockY, blockZ);
		return reflector.openSides[side];
	}
	
	@Override
	public void passLaser(World world, int blockX, int blockY, int blockZ, int orginX, int orginY, int orginZ) {
		TileEntityReflector reflector = (TileEntityReflector)world.getBlockTileEntity(blockX, blockY, blockZ);
	}
}
