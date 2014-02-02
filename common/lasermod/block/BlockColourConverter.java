package lasermod.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lasermod.ModItems;
import lasermod.api.ILaser;
import lasermod.api.ILaserReciver;
import lasermod.api.LaserInGame;
import lasermod.api.LaserRegistry;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.tileentity.TileEntityColourConverter;
import lasermod.util.LaserUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class BlockColourConverter extends BlockContainer {

	public IIcon inputIcon;
	public IIcon[] front = new IIcon[16];
	
	public BlockColourConverter() {
		super(Material.field_151576_e);
		this.func_149711_c(1.0F);
		this.func_149647_a(CreativeTabs.tabBrewing);
	}

	@Override
	public TileEntity func_149915_a(World world, int meta) {
		return new TileEntityColourConverter();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void func_149651_a(IIconRegister iconRegister) {
	    this.inputIcon = iconRegister.registerIcon("lasermod:colorConverterInput");
	    for(int i = 0; i < 16; ++i) {
	    	front[i] = iconRegister.registerIcon("lasermod:ColorConverter_" + i);
	    }
	}

	@Override
	@SideOnly(Side.CLIENT)
    public IIcon func_149673_e(IBlockAccess world, int x, int y, int z, int side) {
		TileEntityColourConverter colourConverter = (TileEntityColourConverter)world.func_147438_o(x, y, z);
		int meta = LaserUtil.getOrientation(world.getBlockMetadata(x, y, z));

		if (meta > 5)
		    return this.front[colourConverter.colour];
		if (side == meta)
		    return this.front[colourConverter.colour];
		else
		 	return side == Facing.oppositeSide[meta] ? inputIcon : Blocks.piston.func_149691_a(0, 1);
    }
	    
	@Override
	public IIcon func_149691_a(int par1, int par2) {
	    int meta = 3;

	    if (meta > 5)
	        return this.front[14];
	    if (par1 == meta)
	        return this.front[14];
	    else
	    	return par1 == Facing.oppositeSide[meta] ? inputIcon : Blocks.piston.func_149691_a(0, 1);
	}
	
	@Override
	public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer player, int side, float xHit, float yHit, float zHit) {
		ItemStack item = player.getCurrentEquippedItem();
		if(item != null) {
			TileEntityColourConverter colourConverter = (TileEntityColourConverter)world.func_147438_o(x, y, z);
			//TODO if(item.itemID == ModItems.screwdriver.itemID) {
			//	return true;
			///}
			
			if(item.getItem() == Items.dye) {
				int colour = 15 - item.getItemDamage();
				if(colour > 15)
					colour = 15;
				else if(colour < 0)
					colour = 0;
				
				if(colour == colourConverter.colour)
					return true;
				
				colourConverter.colour = colour;
				//TODO world.markBlockForRenderUpdate(x, y, z);
				
				if(colourConverter.getOutputLaser() != null) {
					ILaserReciver reciver = LaserUtil.getFirstReciver(colourConverter, colourConverter.func_145832_p());
					if(reciver != null) {
						reciver.removeLasersFromSide(world, x, y, z, Facing.oppositeSide[colourConverter.func_145832_p()]);
					  	if(reciver.canPassOnSide(world, x, y, z, Facing.oppositeSide[colourConverter.func_145832_p()])) {
							reciver.passLaser(world, y, y,z, colourConverter.getOutputLaser());
						}
					}
				}
				
				if(!player.capabilities.isCreativeMode)
					item.stackSize--;
				if(item.stackSize <= 0)
					player.setCurrentItemOrArmor(0, (ItemStack)null);
				
				//TODO if(!world.isRemote)
				//	PacketDispatcher.sendPacketToAllAround(x + 0.5D, y + 0.5D, z + 0.5D, 512, world.provider.dimensionId, colourConverter.getDescriptionPacket());
				
				return true;
			}
		}
		return false;
	}

	
	@Override
	public void func_149689_a(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack item) {
		 int rotation = determineOrientation(world, x, y, z, entityLiving);
		 world.setBlockMetadataWithNotify(x, y, z, rotation, 2);
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
}
