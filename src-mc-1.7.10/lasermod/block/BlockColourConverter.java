package lasermod.block;

import lasermod.LaserMod;
import lasermod.network.PacketDispatcher;
import lasermod.network.packet.client.ColourConverterMessage;
import lasermod.tileentity.TileEntityColourConverter;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class BlockColourConverter extends BlockContainer {

	public IIcon inputIcon;
	public IIcon[] front = new IIcon[16];
	
	public BlockColourConverter() {
		super(Material.rock);
		this.setHardness(1.0F);
		this.setCreativeTab(LaserMod.tabLaser);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) { return new TileEntityColourConverter(); }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
	    this.inputIcon = iconRegister.registerIcon("lasermod:colorConverterInput");
	    for(int i = 0; i < 16; ++i) front[i] = iconRegister.registerIcon("lasermod:ColorConverter_" + i);
	}

	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		TileEntityColourConverter colourConverter = (TileEntityColourConverter)world.getTileEntity(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);

		if (meta > 5) return this.front[colourConverter.colour];
		if (side == meta) return this.front[colourConverter.colour];
		else return side == Facing.oppositeSide[meta] ? inputIcon : Blocks.piston.getIcon(0, 1);
    }
	
	@Override
	public IIcon getIcon(int par1, int par2) {
	    int meta = 3;

	    if (meta > 5) return this.front[14];
	    if (par1 == meta) return this.front[14];
	    else return par1 == Facing.oppositeSide[meta] ? inputIcon : Blocks.piston.getIcon(0, 1);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xHit, float yHit, float zHit) {
		ItemStack item = player.getCurrentEquippedItem();
		if(!world.isRemote && item != null) {
			if(item.getItem() == Items.dye) {
				TileEntityColourConverter colourConverter = (TileEntityColourConverter)world.getTileEntity(x, y, z);
				
				int colour = 15 - item.getItemDamage();
				if(colour > 15) colour = 15;
				else if(colour < 0) colour = 0;
				
				if(colour == colourConverter.colour) return true;
				
				colourConverter.colour = colour;
				if(!player.capabilities.isCreativeMode) item.stackSize--;
				if(item.stackSize <= 0) player.setCurrentItemOrArmor(0, (ItemStack)null);
				
				FMLLog.info("interact");
				PacketDispatcher.sendToAllAround(new ColourConverterMessage(colourConverter), colourConverter, 512);
				
				return true;
			}
		}
		return false;
	}

	@Override
	public void onBlockPlacedBy(World par1World, int x, int y, int z, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack) {
		 int rotation = BlockPistonBase.determineOrientation(par1World, x, y, z, par5EntityLiving);
		 par1World.setBlockMetadataWithNotify(x, y, z, rotation, 2);
	}
}
