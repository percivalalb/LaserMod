package lasermod.block;

import lasermod.LaserMod;
import lasermod.ModItems;
import lasermod.network.PacketDispatcher;
import lasermod.network.packet.client.ReflectorMessage;
import lasermod.tileentity.TileEntityReflector;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	public TileEntity createNewTileEntity(World world, int meta) { return new TileEntityReflector(); }

	@Override
	public int getRenderType() {
		return -1;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote && player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.screwdriver) {
			TileEntityReflector reflector = (TileEntityReflector)world.getTileEntity(pos);
			reflector.closedSides[side.getIndex()] = !reflector.closedSides[side.getIndex()];
			
			if(reflector.closedSides[side.getIndex()])
				reflector.removeAllLasersFromSide(side);
			
			PacketDispatcher.sendToAllAround(new ReflectorMessage(reflector), reflector, 512);
			return true;
		}
        return false;
    }
	
	
	
	@Override
	public boolean isOpaqueCube() { return false; }
}
