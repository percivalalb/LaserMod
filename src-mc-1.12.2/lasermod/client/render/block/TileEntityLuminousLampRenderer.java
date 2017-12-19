package lasermod.client.render.block;

import lasermod.api.LaserCollisionBoxes;
import lasermod.api.LaserInGame;
import lasermod.api.LaserToRender;
import lasermod.block.BlockLuminousLamp;
import lasermod.tileentity.TileEntityLuminousLamp;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;

/**
 * @author ProPercivalalb
 */
public class TileEntityLuminousLampRenderer extends TileEntitySpecialRenderer<TileEntityLuminousLamp> {

	@Override
	public void render(TileEntityLuminousLamp te, double x, double y, double z, float partialTicks, int destroyStage, float a) {
		if(te.hasWorld() && te.getWorld().getBlockState(te.getPos()).getValue(BlockLuminousLamp.ACTIVE)) {
			
			
	    	LaserInGame laser = te.getCombinedOutputLaser(EnumFacing.DOWN);
	    	if(laser == null) return;

	    	//ModBlocks.luminousLamp.setBlockBoundsBasedOnState(panel.getWorld(), (int)x, (int)y, (int)z);
	    	//AxisAlignedBB panelOutline = new AxisAlignedBB(x + ModBlocks.LUMINOUS_LAMP.getBlockBoundsMinX(), y + ModBlocks.LUMINOUS_LAMP.getBlockBoundsMinY(), z + ModBlocks.LUMINOUS_LAMP.getBlockBoundsMinZ(), x + ModBlocks.LUMINOUS_LAMP.getBlockBoundsMaxX(), y + ModBlocks.LUMINOUS_LAMP.getBlockBoundsMaxY(), z + ModBlocks.LUMINOUS_LAMP.getBlockBoundsMaxZ()).grow(partialTicks / 50, partialTicks / 50, partialTicks / 50).grow(0.05, 0.05, 0.05);
	    	AxisAlignedBB panelOutline = new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D).grow(partialTicks / 50, partialTicks / 50, partialTicks / 50).grow(0.05, 0.05, 0.05);
	        LaserCollisionBoxes.addLaserCollision(new LaserToRender(laser, panelOutline, x, y, z, te.getPos(), EnumFacing.DOWN, 0.7F, false));
		}
    }
}
