package lasermod.block;

import java.util.Random;

import lasermod.LaserMod;
import lasermod.ModBlocks;
import lasermod.ModItems;
import lasermod.api.ILaser;
import lasermod.api.ILaserReceiver;
import lasermod.api.LaserInGame;
import lasermod.network.packet.PacketReflector;
import lasermod.proxy.CommonProxy;
import lasermod.tileentity.TileEntityReflector;
import lasermod.util.BlockActionPos;
import lasermod.util.LaserUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class BlockReflector extends BlockContainer {

	public BlockReflector() {
		super(Material.rock);
		this.setHardness(1.0F);
		this.setCreativeTab(LaserMod.tabLaser);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityReflector();
	}

	@Override
	public int getRenderType() {
        return -1;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
	    this.blockIcon = iconRegister.registerIcon("lasermod:reflector");
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
        if (!world.isRemote) {
        	TileEntityReflector reflector = (TileEntityReflector)world.getTileEntity(x, y, z);
        	for(int i = 0; i < reflector.closedSides.length; ++i) {
    			if(reflector.closedSides[i] || reflector.lasers.size() == 0) {
    				BlockActionPos reciver = LaserUtil.getFirstBlock(reflector, i);
    				if(reciver != null && reciver.isLaserReciver(reflector.getBlockMetadata()))
    					reciver.getLaserReceiver(reflector.getBlockMetadata()).removeLasersFromSide(world, x, y, z, Facing.oppositeSide[i]);
    				continue;
    			}
    			if(reflector.containsInputSide(i)) 
    				continue;
    			
    			BlockActionPos reciver = LaserUtil.getFirstBlock(reflector, i);
    			if(reciver != null && reciver.isLaserReciver(i)) {
    				LaserInGame laserInGame = reflector.getOutputLaser(i);
    			  	if(reciver.getLaserReceiver(reflector.getBlockMetadata()).canPassOnSide(world, x, y, z, Facing.oppositeSide[i], laserInGame)) {
    			  		reciver.getLaserReceiver(reflector.getBlockMetadata()).passLaser(world, x, y, z, Facing.oppositeSide[i], laserInGame);
    				}
    			}
    		}
        }
    }
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
		if (!world.isRemote) {
			TileEntityReflector reflector = (TileEntityReflector)world.getTileEntity(x, y, z);
	    	for(int i = 0; i < reflector.closedSides.length; ++i) {
				if(reflector.closedSides[i] || reflector.lasers.size() == 0) {
					BlockActionPos reciver = LaserUtil.getFirstBlock(reflector, i);
					if(reciver != null && reciver.isLaserReciver(reflector.getBlockMetadata()))
						reciver.getLaserReceiver(reflector.getBlockMetadata()).removeLasersFromSide(world, x, y, z, Facing.oppositeSide[i]);

					continue;
				}
				if(reflector.containsInputSide(i)) 
					continue;
				
				BlockActionPos reciver = LaserUtil.getFirstBlock(reflector, i);
				if(reciver != null && reciver.isLaserReciver(i)) {
					LaserInGame laserInGame = reflector.getOutputLaser(i);
				  	if(reciver.getLaserReceiver(reflector.getBlockMetadata()).canPassOnSide(world, x, y, z, Facing.oppositeSide[i], laserInGame)) {
				  		reciver.getLaserReceiver(reflector.getBlockMetadata()).passLaser(world, x, y, z, Facing.oppositeSide[i], laserInGame);
					}
				}
			}
        }
    }

	@Override
    public void updateTick(World world, int x, int y, int z, Random random) {
		TileEntityReflector reflector = (TileEntityReflector)world.getTileEntity(x, y, z);
    	for(int i = 0; i < reflector.closedSides.length; ++i) {
			if(reflector.closedSides[i] || reflector.lasers.size() == 0) {
				BlockActionPos reciver = LaserUtil.getFirstBlock(reflector, i);
				if(reciver != null && reciver.isLaserReciver(reflector.getBlockMetadata()))
					reciver.getLaserReceiver(reflector.getBlockMetadata()).removeLasersFromSide(world, x, y, z, Facing.oppositeSide[i]);
				continue;
			}
			if(reflector.containsInputSide(i)) 
				continue;
			
			BlockActionPos reciver = LaserUtil.getFirstBlock(reflector, i);
			if(reciver != null && reciver.isLaserReciver(i)) {
				LaserInGame laserInGame = reflector.getOutputLaser(i);
			  	if(reciver.getLaserReceiver(reflector.getBlockMetadata()).canPassOnSide(world, x, y, z, Facing.oppositeSide[i], laserInGame)) {
			  		reciver.getLaserReceiver(reflector.getBlockMetadata()).passLaser(world, x, y, z, Facing.oppositeSide[i], laserInGame);
				}
			}
			else if(reciver != null) {
				LaserInGame laserInGame = reflector.getOutputLaser(i);
				
				for(ILaser laser : laserInGame.getLaserType()) {
					laser.actionOnBlock(reciver);
				}
			}
		}
	}
	
	@Override
	public boolean isOpaqueCube() {
	    return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xHit, float yHit, float zHit) {
		ItemStack item = player.getCurrentEquippedItem();
		if(!world.isRemote && item != null && item.getItem() == ModItems.screwdriver) {
			TileEntityReflector reflector = (TileEntityReflector)world.getTileEntity(x, y, z);
			reflector.closedSides[side] = !reflector.closedSides[side];
			
			if(reflector.closedSides[side])
				reflector.removeAllLasersFromSide(side);
			
			LaserMod.NETWORK_MANAGER.sendPacketToAllAround(new PacketReflector(reflector), world.provider.dimensionId, x + 0.5D, y + 0.5D, z + 0.5D, 512);
			
			
			return true;
		}
        return false;
    }
}
