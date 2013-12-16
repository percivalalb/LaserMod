package lasermod.block;

import java.util.List;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lasermod.api.ILaserReciver;
import lasermod.api.LaserInGame;
import lasermod.core.helper.LogHelper;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.tileentity.TileEntityColourConverter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class BlockColourConverter extends BlockContainer implements ILaserReciver {

	public Icon frontIcon;
	public Icon backIcon;
	public Icon sideIcon;
	
	public BlockColourConverter(int id) {
		super(id, Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityColourConverter();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
	    this.frontIcon = iconRegister.registerIcon("lasermod:eaw");
	    this.backIcon = iconRegister.registerIcon("lasermod:dawe");
	    this.sideIcon = iconRegister.registerIcon("lasermod:daw");
	}

	@Override
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(int id, CreativeTabs creativeTab, List tabList) {
        tabList.add(new ItemStack(id, 1, 0));
        tabList.add(new ItemStack(id, 1, 1));
        tabList.add(new ItemStack(id, 1, 2));
        tabList.add(new ItemStack(id, 1, 3));
        tabList.add(new ItemStack(id, 1, 4));
        tabList.add(new ItemStack(id, 1, 5));
        tabList.add(new ItemStack(id, 1, 6));
        tabList.add(new ItemStack(id, 1, 7));
        tabList.add(new ItemStack(id, 1, 8));
        tabList.add(new ItemStack(id, 1, 9));
        tabList.add(new ItemStack(id, 1, 10));
        tabList.add(new ItemStack(id, 1, 11));
        tabList.add(new ItemStack(id, 1, 12));
        tabList.add(new ItemStack(id, 1, 13));
        tabList.add(new ItemStack(id, 1, 14));
        tabList.add(new ItemStack(id, 1, 15));
    }

	public static int getOrientation(int par1) {
		return par1 & 7;
	}
	    
	@Override
	public Icon getIcon(int par1, int par2) {
	    int meta = getOrientation(par2);

	    if (meta > 5)
	        return this.frontIcon;
	    if (par1 == meta) {
	        return this.frontIcon;
	    }
	    else {
	    	return par1 == Facing.oppositeSide[meta] ? Block.anvil.getBlockTextureFromSide(0) : sideIcon;
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
	public void onBlockPlacedBy(World par1World, int x, int y, int z, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack) {
		 int rotation = determineOrientation(par1World, x, y, z, par5EntityLiving);
		 par1World.setBlockMetadataWithNotify(x, y, z, rotation, 2);
	}
	
	public static int determineOrientation(World par0World, int par1, int par2, int par3, EntityLivingBase par4EntityLivingBase) {
        if (MathHelper.abs((float)par4EntityLivingBase.posX - (float)par1) < 2.0F && MathHelper.abs((float)par4EntityLivingBase.posZ - (float)par3) < 2.0F) {
            double d0 = par4EntityLivingBase.posY + 1.82D - (double)par4EntityLivingBase.yOffset;

            if (d0 - (double)par2 > 2.0D) {
                return 0;
            }

            if ((double)par2 - d0 > 0.0D)
            {
                return 1;
            }
        }

        int l = MathHelper.floor_double((double)(par4EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        return l == 0 ? 3 : (l == 1 ? 4 : (l == 2 ? 2 : (l == 3 ? 5 : 1)));
    }
	
	@Override
	public boolean canPassOnSide(World world, int blockX, int blockY, int blockZ, int orginX, int orginY, int orginZ, int side) {
		TileEntityColourConverter colourConverter = (TileEntityColourConverter)world.getBlockTileEntity(blockX, blockY, blockZ);
		return side == Facing.oppositeSide[colourConverter.getBlockMetadata()];
	}
	
	@Override
	public void passLaser(World world, int blockX, int blockY, int blockZ, int orginX, int orginY, int orginZ, LaserInGame laserInGame) {
		TileEntityColourConverter colourConverter = (TileEntityColourConverter)world.getBlockTileEntity(blockX, blockY, blockZ);
		if(colourConverter.laser == null) {
			colourConverter.laser = laserInGame;
			if(!world.isRemote)
				PacketDispatcher.sendPacketToAllAround(blockX + 0.5D, blockY + 0.5D, blockZ + 0.5D, world.provider.dimensionId, 512, colourConverter.getDescriptionPacket());
		}
		//if(!world.isRemote)
		//	PacketDispatcher.sendPacketToAllAround(blockX + 0.5D, blockY + 0.5D, blockZ + 0.5D, world.provider.dimensionId, 512, colourConverter.getDescriptionPacket());
		
	}

	@Override
	public void removeLasersFromSide(World world, int blockX, int blockY, int blockZ, int orginX, int orginY, int orginZ, int side) {
		TileEntityColourConverter colourConverter = (TileEntityColourConverter)world.getBlockTileEntity(blockX, blockY, blockZ);
		
		if(side == Facing.oppositeSide[colourConverter.getBlockMetadata()]) {
			colourConverter.laser = null;
			if(!world.isRemote)
				PacketDispatcher.sendPacketToAllAround(blockX + 0.5D, blockY + 0.5D, blockZ + 0.5D, world.provider.dimensionId, 512, colourConverter.getDescriptionPacket());
		}
		
		//if(flag && !world.isRemote)
		//	PacketDispatcher.sendPacketToAllAround(blockX + 0.5D, blockY + 0.5D, blockZ + 0.5D, world.provider.dimensionId, 512, colourConverter.getDescriptionPacket());
	}
	
	@Override
	public boolean isSendingSignalFromSide(World world, int blockX, int blockY, int blockZ, int orginX, int orginY, int orginZ, int side) {
		TileEntityColourConverter colourConverter = (TileEntityColourConverter)world.getBlockTileEntity(blockX, blockY, blockZ);
		return colourConverter.laser != null && side == colourConverter.getBlockMetadata();
	}
}
