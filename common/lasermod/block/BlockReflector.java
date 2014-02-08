package lasermod.block;

import java.util.Arrays;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import lasermod.LaserMod;
import lasermod.ModItems;
import lasermod.api.ILaserReciver;
import lasermod.api.LaserInGame;
import lasermod.network.packet.PacketReflector;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.tileentity.TileEntityReflector;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
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
		super(Material.rock);
		this.setHardness(1.0F);
		this.setCreativeTab(LaserMod.tabLaser);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityReflector();
	}

	@Override
	public int getRenderType() {
        return -1;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
	    this.blockIcon = iconRegister.registerIcon("lasermod:reflector");
	}
	
	@Override
	public boolean isOpaqueCube() {
	    return false;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xHit, float yHit, float zHit) {
		ItemStack item = player.getCurrentEquippedItem();
		if(!world.isRemote && item != null && item.getItem() == ModItems.screwdriver) {
			TileEntityReflector reflector = (TileEntityReflector)world.getTileEntity(x, y, z);
			reflector.closedSides[side] = !reflector.closedSides[side];
			
			if(reflector.closedSides[side])
				reflector.removeAllLasersFromSide(side);
			
			LaserMod.NETWORK_MANAGER.sendPacketToAllAround(new PacketReflector(reflector), world.provider.dimensionId, x + 0.5D, y + 0.5D, z + 0.5D, 512);
			
			
			return true;
		}
        return false;
    }
}
