package lasermod.block;

import java.util.List;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lasermod.ModItems;
import lasermod.api.ILaser;
import lasermod.api.ILaserReciver;
import lasermod.api.LaserInGame;
import lasermod.api.LaserRegistry;
import lasermod.core.helper.LogHelper;
import lasermod.tileentity.TileEntityAdvancedLaser;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.tileentity.TileEntityColourConverter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class BlockColourConverter extends BlockContainer implements ILaserReciver {

	public Icon frontIcon;
	public Icon[] front = new Icon[16];
	
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
	    for(int i = 0; i < 15; ++i) {
	    	front[i] = this.frontIcon = iconRegister.registerIcon("lasermod:ColorConverter_" + i);
	    }
	}

	@Override
	@SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side) {
		TileEntityColourConverter colourConverter = (TileEntityColourConverter)world.getBlockTileEntity(x, y, z);
		int meta = getOrientation(world.getBlockMetadata(x, y, z));

		if (meta > 5)
		    return this.front[colourConverter.colour];
		if (side == meta) {
		    return this.front[colourConverter.colour];
		}
		else {
		 	return side == Facing.oppositeSide[meta] ? Block.anvil.getBlockTextureFromSide(0) : Block.pistonBase.getIcon(0, 1);
		}
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(int id, CreativeTabs creativeTab, List tabList) {
        tabList.add(new ItemStack(id, 1, 0));
    }

	public static int getOrientation(int par1) {
		return par1 & 7;
	}
	    
	@Override
	public Icon getIcon(int par1, int par2) {
	    int meta = 2;

	    if (meta > 5)
	        return this.frontIcon;
	    if (par1 == meta) {
	        return this.frontIcon;
	    }
	    else {
	    	return par1 == Facing.oppositeSide[meta] ? Block.anvil.getBlockTextureFromSide(0) : Block.pistonBase.getIcon(0, 1);
	    }
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xHit, float yHit, float zHit) {
		ItemStack item = player.getCurrentEquippedItem();
		if(item != null) {
			TileEntityColourConverter colourConverter = (TileEntityColourConverter)world.getBlockTileEntity(x, y, z);
			if(item.itemID == ModItems.screwdriver.itemID) {
				return true;
			}
			
			if(item.itemID == Item.dyePowder.itemID) {
				int colour = 15 - item.getItemDamage();
				if(colour > 15)
					colour = 15;
				else if(colour < 0)
					colour = 0;
				
				if(colour == colourConverter.colour)
					return true;
				
				colourConverter.colour = colour;
				
				if(colourConverter.laser != null) {
					ILaserReciver reciver = colourConverter.getFirstReciver(colourConverter.getBlockMetadata());
					if(reciver != null) {
						reciver.removeLasersFromSide(world, colourConverter.reciverCords[0], colourConverter.reciverCords[1], colourConverter.reciverCords[2], x, y, z, Facing.oppositeSide[colourConverter.getBlockMetadata()]);
					  	if(reciver.canPassOnSide(world, colourConverter.reciverCords[0], colourConverter.reciverCords[1], colourConverter.reciverCords[2], x, y, z, Facing.oppositeSide[colourConverter.getBlockMetadata()])) {
							reciver.passLaser(world, colourConverter.reciverCords[0], colourConverter.reciverCords[1], colourConverter.reciverCords[2], y, y,z, colourConverter.getCreatedLaser());
						}
					}
				}
				
				if(!player.capabilities.isCreativeMode)
					item.stackSize--;
				if(item.stackSize <= 0)
					player.setCurrentItemOrArmor(0, (ItemStack)null);
				
				if(!world.isRemote)
					PacketDispatcher.sendPacketToAllAround(x + 0.5D, y + 0.5D, z + 0.5D, world.provider.dimensionId, 512, colourConverter.getDescriptionPacket());
				
				return true;
			}
		}
		return false;
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
