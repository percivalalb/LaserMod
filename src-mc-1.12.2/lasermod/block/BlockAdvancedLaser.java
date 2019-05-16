package lasermod.block;

import lasermod.LaserMod;
import lasermod.ModItems;
import lasermod.api.LaserRegistry;
import lasermod.api.LaserType;
import lasermod.network.PacketDispatcher;
import lasermod.network.packet.client.AdvancedLaserMessage;
import lasermod.tileentity.TileEntityAdvancedLaser;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public class BlockAdvancedLaser extends BlockPoweredRedstone {
	
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
					player.sendMessage(new TextComponentTranslation("lasermod.screwdriver.advanced_laser", pos.getX(), pos.getY(), pos.getZ()).setStyle((new Style()).setColor(TextFormatting.RED)));
					if(advancedLaser.getOutputLaser(state.getValue(FACING)).getLaserType().size() <= 1) {
						player.sendMessage(new TextComponentTranslation("lasermod.screwdriver.no_upgrade"));
					}
					else {
						//player.openGui(LaserMod.instance, GuiAdvancedLaser.GUI_ID, world, x, y, z);
						//player.addChatMessage(" Upgrades attached to this laser...");
						for(LaserType type : advancedLaser.upgrades) {
							player.sendMessage(new TextComponentTranslation(type.getTranslationKey()).setStyle((new Style()).setColor(TextFormatting.GREEN)));
						}
					}
				}
				return true;
			}
			
			LaserType type = LaserRegistry.getLaserFromItem(item);
			boolean power = state.getValue(POWERED);
			if(type != null && !power) {
				if(advancedLaser.upgrades.contains(type)) {
					if(!world.isRemote) player.sendMessage(new TextComponentTranslation("lasermod.screwdriver.already_has_upgrade"));
					return true;
				}
				
				advancedLaser.upgrades.add(type);
				
				if(!player.capabilities.isCreativeMode) item.shrink(1);
				
				if(!world.isRemote) PacketDispatcher.sendToAllTracking(new AdvancedLaserMessage(advancedLaser), advancedLaser);
				
				return true;
			}
			else if(type != null && power && !world.isRemote) {
				player.sendMessage(new TextComponentTranslation("lasermod.screwdriver.redstone_signal"));
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

            // TODO Spawn upgrades ItemStack stack = advancedLaser.upgrades.get(i);
            //InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
        }
        
        super.breakBlock(world, pos, state);
    }
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		if(state.getValue(FACING) == face) return BlockFaceShape.UNDEFINED;
        return BlockFaceShape.SOLID;
    }
}
