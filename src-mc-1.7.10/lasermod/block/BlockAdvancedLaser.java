package lasermod.block;

import java.util.Random;

import lasermod.LaserMod;
import lasermod.ModItems;
import lasermod.api.ILaser;
import lasermod.api.ILaserReceiver;
import lasermod.api.LaserInGame;
import lasermod.api.LaserRegistry;
import lasermod.client.gui.GuiAdvancedLaser;
import lasermod.network.packet.PacketAdvancedLaser;
import lasermod.network.packet.PacketColourConverter;
import lasermod.tileentity.TileEntityAdvancedLaser;
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
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
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
		int meta = LaserUtil.getOrientation(world.getBlockMetadata(x, y, z));

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
	public void onBlockAdded(World world, int x, int y, int z) {
		//world.markBlockForUpdate(x, y, z);
    }
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
		//world.markBlockForUpdate(x, y, z);
    }

	@Override
    public void updateTick(World world, int x, int y, int z, Random random) {
		TileEntityAdvancedLaser basiclaser = (TileEntityAdvancedLaser)world.getTileEntity(x, y, z);
    	
        if (!world.isBlockIndirectlyGettingPowered(x, y, z)) {
        	
        	BlockActionPos reciver = LaserUtil.getFirstBlock(basiclaser, basiclaser.getBlockMetadata());
    		if(reciver != null && reciver.isLaserReceiver(basiclaser.getBlockMetadata())) {
    			reciver.getLaserReceiver(basiclaser.getBlockMetadata()).removeLasersFromSide(world, x, y, z, Facing.oppositeSide[basiclaser.getBlockMetadata()]);
    		}
        }
        else if (world.isBlockIndirectlyGettingPowered(x, y, z)) {
    		BlockActionPos reciver = LaserUtil.getFirstBlock(basiclaser, basiclaser.getBlockMetadata());
    		if(reciver != null && reciver.isLaserReceiver(basiclaser.getBlockMetadata())) {
    		  	
    		  	LaserInGame laserInGame = basiclaser.getOutputLaser(basiclaser.getBlockMetadata());

    		  	if(reciver.getLaserReceiver(basiclaser.getBlockMetadata()).canPassOnSide(world, x, y, z, Facing.oppositeSide[basiclaser.getBlockMetadata()], laserInGame)) {
    		  		reciver.getLaserReceiver(basiclaser.getBlockMetadata()).passLaser(world, x, y, z, Facing.oppositeSide[basiclaser.getBlockMetadata()], laserInGame);
    			}
    		}
    		else if(reciver != null) {
    			LaserInGame laserInGame = basiclaser.getOutputLaser(basiclaser.getBlockMetadata());
    			
    			for(ILaser laser : laserInGame.getLaserType()) {
    				laser.actionOnBlock(reciver);
    			}
    		}
        }
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
					if(advancedLaser.getOutputLaser(advancedLaser.getBlockMetadata()).getLaserType().size() <= 1) {
						player.addChatMessage(new ChatComponentText(" Currently no upgrades attached to this laser."));
					}
					else {
						//player.openGui(LaserMod.instance, GuiAdvancedLaser.GUI_ID, world, x, y, z);
						//player.addChatMessage(" Upgrades attached to this laser...");
						for(ILaser laser : advancedLaser.getOutputLaser(advancedLaser.getBlockMetadata()).getLaserType()) {
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
					LaserMod.NETWORK_MANAGER.sendPacketToAllAround(new PacketAdvancedLaser(advancedLaser), world.provider.dimensionId, x + 0.5D, y + 0.5D, z + 0.5D, 512);
				
				return true;
			}
			else if(laser != null && power && !world.isRemote) {
				//player.addChatMessage("Please disable redstone signal to input an upgrade.");
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
