package lasermod.block;

import java.util.Random;

import lasermod.LaserMod;
import lasermod.ModBlocks;
import lasermod.ModItems;
import lasermod.api.ILaserReceiver;
import lasermod.api.LaserInGame;
import lasermod.network.packet.PacketReflector;
import lasermod.proxy.CommonProxy;
import lasermod.tileentity.TileEntityMirror;
import lasermod.tileentity.TileEntityReflector;
import lasermod.util.LaserUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class BlockMirror extends BlockContainer {

	public BlockMirror() {
		super(Material.glass);
		this.setHardness(1.0F);
		this.setCreativeTab(LaserMod.tabLaser);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityMirror();
	}

	@Override
	public int getRenderType() {
        return -1;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
	    this.blockIcon = iconRegister.registerIcon("lasermod:mirror");
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
        if(!world.isRemote) {
        	TileEntityMirror mirror = (TileEntityMirror)world.getTileEntity(x, y, z);
        }
    }
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
		if(!world.isRemote) {
			TileEntityMirror mirror = (TileEntityMirror)world.getTileEntity(x, y, z);
	    	
		}
    }

	@Override
    public void updateTick(World world, int x, int y, int z, Random random) {
		TileEntityMirror mirror = (TileEntityMirror)world.getTileEntity(x, y, z);
    	
	}
	
	@Override
	public boolean isOpaqueCube() {
	    return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xHit, float yHit, float zHit) {
		ItemStack item = player.getCurrentEquippedItem();
		if(!world.isRemote && item != null && item.getItem() == ModItems.screwdriver) {
			TileEntityMirror mirror = (TileEntityMirror)world.getTileEntity(x, y, z);
			
			//LaserMod.NETWORK_MANAGER.sendPacketToAllAround(new PacketMirror(mirror), world.provider.dimensionId, x + 0.5D, y + 0.5D, z + 0.5D, 512);
			
			
			return true;
		}
        return false;
    }
}
