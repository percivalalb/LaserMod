package lasermod.block;

import java.util.Random;

import lasermod.LaserMod;
import lasermod.ModItems;
import lasermod.api.ILaser;
import lasermod.api.LaserInGame;
import lasermod.api.LaserRegistry;
import lasermod.network.PacketDispatcher;
import lasermod.network.packet.client.AdvancedLaserMessage;
import lasermod.tileentity.TileEntityAdvancedLaser;
import lasermod.util.BlockActionPos;
import lasermod.util.LaserUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class BlockAdvancedLaser extends BlockContainer {

	private Random rand = new Random();
	
	@SideOnly(Side.CLIENT)
	public IIcon frontIcon;
	@SideOnly(Side.CLIENT)
	public IIcon backIcon;
	@SideOnly(Side.CLIENT)
	public IIcon sideIcon;
	
	public BlockAdvancedLaser() {
		super(Material.rock);
		this.setHardness(1.0F);
		this.setCreativeTab(LaserMod.tabLaser);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityAdvancedLaser();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
	    this.frontIcon = iconRegister.registerIcon("lasermod:advancedLaserFront");
	    this.backIcon = iconRegister.registerIcon("lasermod:advancedLaserBack");
	    this.sideIcon = iconRegister.registerIcon("lasermod:advancedLaserSide");
	}
	    
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		int meta = world.getBlockMetadata(x, y, z);

		if (meta > 5)
	        return this.frontIcon;
	    if (side == meta)
	        return this.frontIcon; 
	    else
	    	return side == Facing.oppositeSide[meta] ? this.backIcon : this.sideIcon;
    }
	    
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
	    int rotation = 3;

	    if (rotation > 5)
	        return this.frontIcon;
	    if (side == rotation)
	        return this.frontIcon;
	    else
	    	return side == Facing.oppositeSide[rotation] ? this.backIcon : this.sideIcon;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack heldStack) {
		 int rotation = BlockPistonBase.determineOrientation(world, x, y, z, entityLiving);
		 world.setBlockMetadataWithNotify(x, y, z, rotation, 2);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xHit, float yHit, float zHit) {
		ItemStack item = player.getCurrentEquippedItem();
		if(item != null) {
			TileEntityAdvancedLaser advancedLaser = (TileEntityAdvancedLaser)world.getTileEntity(x, y, z);
			if(item.getItem() == ModItems.screwdriver) {
				
				if(!world.isRemote) {
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + String.format("Advanced Laser (%d, %d, %d)", x, y, z)));
					if(advancedLaser.getOutputLaser(ForgeDirection.getOrientation(advancedLaser.getBlockMetadata())).getLaserType().size() <= 1) {
						player.addChatMessage(new ChatComponentText(" Currently no upgrades attached to this laser."));
					}
					else {
						//player.openGui(LaserMod.instance, GuiAdvancedLaser.GUI_ID, world, x, y, z);
						//player.addChatMessage(" Upgrades attached to this laser...");
						for(ILaser laser : advancedLaser.getOutputLaser(ForgeDirection.getOrientation(advancedLaser.getBlockMetadata())).getLaserType()) {
							String name = LaserRegistry.getIdFromLaser(laser);
							name = name.replaceFirst(String.valueOf(name.charAt(0)), String.valueOf(name.charAt(0)).toUpperCase());
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "  " + name));
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
							player.addChatMessage(new ChatComponentText("This Laser already has this upgrade."));
						return true;
					}
				}
				
				advancedLaser.upgrades.add(item);
				
				if(!player.capabilities.isCreativeMode)
					item.stackSize--;
				if(item.stackSize <= 0)
					player.setCurrentItemOrArmor(0, (ItemStack)null);
				
				if(!world.isRemote)
					PacketDispatcher.sendToAllAround(new AdvancedLaserMessage(advancedLaser), advancedLaser, 512);
				
				return true;
			}
			else if(laser != null && power && !world.isRemote) {
				player.addChatMessage(new ChatComponentText("Please disable redstone signal to input an upgrade."));
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block oldBlock, int oldBlockMeta) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (!(tileEntity instanceof TileEntityAdvancedLaser))
            return;

        TileEntityAdvancedLaser advancedLaser = (TileEntityAdvancedLaser)tileEntity;

        for (int i = 0; i < advancedLaser.upgrades.size(); i++) {

            ItemStack itemStack = advancedLaser.upgrades.get(i);

            if (itemStack != null && itemStack.stackSize > 0) {
                float dX = rand.nextFloat() * 0.8F + 0.1F;
                float dY = rand.nextFloat() * 0.8F + 0.1F;
                float dZ = rand.nextFloat() * 0.8F + 0.1F;

                EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, new ItemStack(itemStack.getItem(), itemStack.stackSize, itemStack.getItemDamage()));

                if (itemStack.hasTagCompound())
                    entityItem.getEntityItem().setTagCompound((NBTTagCompound)itemStack.getTagCompound().copy());

                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                itemStack.stackSize = 0;
            }
        }

        super.breakBlock(world, x, y, z, oldBlock, oldBlockMeta);
    }
}
