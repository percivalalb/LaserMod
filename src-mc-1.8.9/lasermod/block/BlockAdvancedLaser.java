package lasermod.block;

import java.util.Random;

import lasermod.LaserMod;
import lasermod.ModItems;
import lasermod.api.ILaser;
import lasermod.api.LaserRegistry;
import lasermod.network.PacketDispatcher;
import lasermod.network.packet.client.AdvancedLaserMessage;
import lasermod.tileentity.TileEntityAdvancedLaser;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class BlockAdvancedLaser extends BlockContainer {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	
	private Random rand = new Random();
	
	public BlockAdvancedLaser() {
		super(Material.rock);
		this.setHardness(1.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setCreativeTab(LaserMod.tabLaser);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) { return new TileEntityAdvancedLaser(); }
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, BlockPistonBase.getFacingFromEntity(worldIn, pos, placer));
    }

	@Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, BlockPistonBase.getFacingFromEntity(worldIn, pos, placer)), 2);
    }
	
	@SideOnly(Side.CLIENT)
    public IBlockState getStateForEntityRender(IBlockState state) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
    }

	@Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {

        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] {FACING});
    }
	
    @Override
	public int getRenderType() {
		return 3;
	}
    
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack item = player.getCurrentEquippedItem();
		if(item != null) {
			TileEntityAdvancedLaser advancedLaser = (TileEntityAdvancedLaser)world.getTileEntity(pos);
			if(item.getItem() == ModItems.screwdriver) {
				
				if(!world.isRemote) {
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + String.format("Advanced Laser (%d, %d, %d)", pos.getX(), pos.getY(), pos.getZ())));
					if(advancedLaser.getOutputLaser(EnumFacing.getFront(advancedLaser.getBlockMetadata())).getLaserType().size() <= 1) {
						player.addChatMessage(new ChatComponentText(" Currently no upgrades attached to this laser."));
					}
					else {
						//player.openGui(LaserMod.instance, GuiAdvancedLaser.GUI_ID, world, x, y, z);
						//player.addChatMessage(" Upgrades attached to this laser...");
						for(ILaser laser : advancedLaser.getOutputLaser(EnumFacing.getFront(advancedLaser.getBlockMetadata())).getLaserType()) {
							String name = LaserRegistry.getIdFromLaser(laser);
							name = name.replaceFirst(String.valueOf(name.charAt(0)), String.valueOf(name.charAt(0)).toUpperCase());
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "  " + name));
						}
					}
				}
				return true;
			}
			
			ILaser laser = LaserRegistry.getLaserFromItem(item);
			boolean power = world.isBlockIndirectlyGettingPowered(pos) > 0;
			if(laser != null && !power) {
				for(ItemStack stack : advancedLaser.upgrades) {
					ILaser laser2 = LaserRegistry.getLaserFromItem(stack);
					if(laser == laser2){
						if(!world.isRemote) player.addChatMessage(new ChatComponentText("This Laser already has this upgrade."));
						return true;
					}
				}
				
				advancedLaser.upgrades.add(item);
				
				if(!player.capabilities.isCreativeMode) item.stackSize--;
				if(item.stackSize <= 0) player.setCurrentItemOrArmor(0, (ItemStack)null);
				
				if(!world.isRemote) PacketDispatcher.sendToAllAround(new AdvancedLaserMessage(advancedLaser), advancedLaser, 512);
				
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
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntity tileEntity = world.getTileEntity(pos);

        if (!(tileEntity instanceof TileEntityAdvancedLaser)) return;

        TileEntityAdvancedLaser advancedLaser = (TileEntityAdvancedLaser)tileEntity;
        for (int i = 0; i < advancedLaser.upgrades.size(); i++) {

            ItemStack itemStack = advancedLaser.upgrades.get(i);

            if (itemStack != null && itemStack.stackSize > 0) {
                float dX = rand.nextFloat() * 0.8F + 0.1F;
                float dY = rand.nextFloat() * 0.8F + 0.1F;
                float dZ = rand.nextFloat() * 0.8F + 0.1F;

                EntityItem entityItem = new EntityItem(world, pos.getX() + dX, pos.getY() + dY, pos.getZ() + dZ, new ItemStack(itemStack.getItem(), itemStack.stackSize, itemStack.getItemDamage()));

                if (itemStack.hasTagCompound()) entityItem.getItem().setTagCompound((NBTTagCompound)itemStack.getTagCompound().copy());

                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntity(entityItem);
                itemStack.stackSize = 0;
            }
        }
        super.breakBlock(world, pos, state);
    }
}
