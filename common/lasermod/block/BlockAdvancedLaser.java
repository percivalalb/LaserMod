package lasermod.block;

import java.util.Random;

import lasermod.LaserMod;
import lasermod.api.ILaser;
import lasermod.api.LaserRegistry;
import lasermod.tileentity.TileEntityAdvancedLaser;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class BlockAdvancedLaser extends BlockContainer {

	private final Random random = new Random();
	
	public Icon frontIcon;
	public Icon backIcon;
	public Icon sideIcon;
	
	public BlockAdvancedLaser(int id) {
		super(id, Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityAdvancedLaser();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
	    this.frontIcon = iconRegister.registerIcon("lasermod:advancedLaserFront");
	    this.backIcon = iconRegister.registerIcon("lasermod:advancedLaserBack");
	    this.sideIcon = iconRegister.registerIcon("lasermod:advancedLaserSide");
	}

	public static int getOrientation(int par1) {
		return par1 & 7;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xHit, float yHit, float zHit) {
		ItemStack item = player.getCurrentEquippedItem();
		if(item != null) {
			TileEntityAdvancedLaser advancedLaser = (TileEntityAdvancedLaser)world.getBlockTileEntity(x, y, z);
			if(item.itemID == LaserMod.screwdriver.itemID) {
				
				if(!world.isRemote) {
					player.addChatMessage(EnumChatFormatting.RED + String.format("Advanced Laser (%d, %d, %d)", x, y, z));
					if(advancedLaser.getCreatedLaser().getLaserType().size() <= 1)
						player.addChatMessage(" Currently no upgrades attached to this laser.");
					else {
						player.addChatMessage(" Upgrades attached to this laser...");
						for(ILaser laser : advancedLaser.getCreatedLaser().getLaserType()) {
							String name = LaserRegistry.getIdFromLaser(laser);
							name = name.replaceFirst(String.valueOf(name.charAt(0)), String.valueOf(name.charAt(0)).toUpperCase());
							player.addChatMessage(EnumChatFormatting.GREEN + "  " + name);
						}
					}
				
				}
				
				return true;
			}
			
			ILaser laser = LaserRegistry.getLaserFromItem(item);
			boolean power = world.isBlockIndirectlyGettingPowered(x, y, z);
			if(laser != null && !power) {
				for(ItemStack stack : advancedLaser.upgrades) {
					ILaser laser2 = LaserRegistry.getLaserFromItem(stack);
					if(laser == laser2){
						if(!world.isRemote)
							player.addChatMessage("This Laser already has this upgrade.");
						return true;
					}
				}
				
				advancedLaser.upgrades.add(item);
				advancedLaser.laser = null;
				
				if(!player.capabilities.isCreativeMode)
					item.stackSize--;
				if(item.stackSize <= 0)
					player.setCurrentItemOrArmor(0, (ItemStack)null);
				
				if(!world.isRemote)
					PacketDispatcher.sendPacketToAllAround(x + 0.5D, y + 0.5D, z + 0.5D, 512, world.provider.dimensionId, advancedLaser.getDescriptionPacket());
				
				return true;
			}
			else if(laser != null && power && !world.isRemote) {
				player.addChatMessage("Please disable redstone signal to input an upgrade.");
				return true;
			}
		}
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side) {
		int meta = getOrientation(world.getBlockMetadata(x, y, z));

		if (meta > 5)
	        return this.frontIcon;
	    if (side == meta) {
	        return this.frontIcon;
	    }
	    else {
	    	return side == Facing.oppositeSide[meta] ? backIcon : sideIcon;
	    }
    }
	    
	@Override
	public Icon getIcon(int side, int par2) {
	    int meta = 3;

	    if (meta > 5)
	        return this.frontIcon;
	    if (side == meta) {
	        return this.frontIcon;
	    }
	    else {
	    	return side == Facing.oppositeSide[meta] ? backIcon : sideIcon;
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
	public void breakBlock(World par1World, int x, int y, int z, int oldBlockId, int oldBlockMeta)
    {
		TileEntityAdvancedLaser advancedLaser = (TileEntityAdvancedLaser)par1World.getBlockTileEntity(x, y, z);

        if (advancedLaser != null)
        {
            for (int j1 = 0; j1 < advancedLaser.upgrades.size(); ++j1)
            {
                ItemStack itemstack = advancedLaser.upgrades.get(j1);
                itemstack.stackSize = 1;

                if (itemstack != null)
                {
                    float f = this.random.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.random.nextFloat() * 0.8F + 0.1F;
                    EntityItem entityitem;

                    for (float f2 = this.random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; par1World.spawnEntityInWorld(entityitem))
                    {
                        int k1 = this.random.nextInt(21) + 10;

                        if (k1 > itemstack.stackSize)
                        {
                            k1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= k1;
                        entityitem = new EntityItem(par1World, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.itemID, k1, itemstack.getItemDamage()));
                        float f3 = 0.05F;
                        entityitem.motionX = (double)((float)this.random.nextGaussian() * f3);
                        entityitem.motionY = (double)((float)this.random.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float)this.random.nextGaussian() * f3);

                        if (itemstack.hasTagCompound())
                        {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }
                    }
                }
            }

            par1World.func_96440_m(x, y, z, oldBlockId);
        }

        super.breakBlock(par1World, x, y, z, oldBlockId, oldBlockMeta);
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
                return 1;
            }

            if ((double)par2 - d0 > 0.0D)
            {
                return 0;
            }
        }

        int l = MathHelper.floor_double((double)(par4EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        return l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0)));
    }
}
