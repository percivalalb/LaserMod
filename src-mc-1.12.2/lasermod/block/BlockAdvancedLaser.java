package lasermod.block;

import java.util.Random;

import lasermod.LaserMod;
import lasermod.ModItems;
import lasermod.api.ILaser;
import lasermod.api.LaserRegistry;
import lasermod.network.PacketDispatcher;
import lasermod.network.packet.client.AdvancedLaserMessage;
import lasermod.tileentity.TileEntityAdvancedLaser;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class BlockAdvancedLaser extends BlockPoweredRedstone {
	
	private Random rand = new Random();
	
	public BlockAdvancedLaser() {
		super(Material.ROCK);
		this.setHardness(1.0F);
		this.setCreativeTab(LaserMod.TAB_LASER);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) { return new TileEntityAdvancedLaser(); }
    
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack item = player.getHeldItem(hand);
		if(!item.isEmpty()) {
			TileEntityAdvancedLaser advancedLaser = (TileEntityAdvancedLaser)world.getTileEntity(pos);
			if(item.getItem() == ModItems.SCREWDRIVER) {
				
				if(!world.isRemote) {
					player.sendMessage(new TextComponentString(TextFormatting.RED + String.format("Advanced Laser (%d, %d, %d)", pos.getX(), pos.getY(), pos.getZ())));
					if(advancedLaser.getOutputLaser(state.getValue(FACING)).getLaserType().size() <= 1) {
						player.sendMessage(new TextComponentString(" Currently no upgrades attached to this laser."));
					}
					else {
						//player.openGui(LaserMod.instance, GuiAdvancedLaser.GUI_ID, world, x, y, z);
						//player.addChatMessage(" Upgrades attached to this laser...");
						for(ItemStack stack : advancedLaser.upgrades) {
							ILaser type = LaserRegistry.getLaserFromItem(stack);
							String name = LaserRegistry.getIdFromLaser(type);
							name = name.replaceFirst(String.valueOf(name.charAt(0)), String.valueOf(name.charAt(0)).toUpperCase());
							player.sendMessage(new TextComponentString(TextFormatting.GREEN + "  " + name));
						}
					}
				}
				return true;
			}
			
			ILaser laser = LaserRegistry.getLaserFromItem(item);
			boolean power = state.getValue(POWERED);
			if(laser != null && !power) {
				for(ItemStack stack : advancedLaser.upgrades) {
					ILaser laser2 = LaserRegistry.getLaserFromItem(stack);
					if(laser == laser2){
						if(!world.isRemote) player.sendMessage(new TextComponentString("This Laser already has this upgrade."));
						return true;
					}
				}
				
				advancedLaser.upgrades.add(item);
				
				if(!player.capabilities.isCreativeMode) item.shrink(1);
				
				if(!world.isRemote) PacketDispatcher.sendToAllAround(new AdvancedLaserMessage(advancedLaser), advancedLaser, 512);
				
				return true;
			}
			else if(laser != null && power && !world.isRemote) {
				player.sendMessage(new TextComponentString("Please disable redstone signal to input an upgrade."));
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

            ItemStack stack = advancedLaser.upgrades.get(i);
            InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
        }
        
        super.breakBlock(world, pos, state);
    }
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		if(state.getValue(FACING) == face) return BlockFaceShape.UNDEFINED;
        return BlockFaceShape.SOLID;
    }
}
