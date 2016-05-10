package lasermod.client.render.block;

import lasermod.ModBlocks;
import lasermod.api.LaserCollisionBoxes;
import lasermod.api.LaserInGame;
import lasermod.api.LaserToRender;
import lasermod.tileentity.TileEntityLuminousLamp;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;

/**
 * @author ProPercivalalb
 */
public class TileEntityLuminousLampRenderer extends TileEntitySpecialRenderer<TileEntityLuminousLamp> {

	@Override
    public void renderTileEntityAt(TileEntityLuminousLamp panel, double x, double y, double z, float partialTicks, int destroyStage) {
    	LaserInGame laser = panel.getCombinedOutputLaser(EnumFacing.DOWN);
    	if(laser == null) return;

    	//ModBlocks.luminousLamp.setBlockBoundsBasedOnState(panel.getWorld(), (int)x, (int)y, (int)z);
    	AxisAlignedBB panelOutline = new AxisAlignedBB(x + ModBlocks.luminousLamp.getBlockBoundsMinX(), y + ModBlocks.luminousLamp.getBlockBoundsMinY(), z + ModBlocks.luminousLamp.getBlockBoundsMinZ(), x + ModBlocks.luminousLamp.getBlockBoundsMaxX(), y + ModBlocks.luminousLamp.getBlockBoundsMaxY(), z + ModBlocks.luminousLamp.getBlockBoundsMaxZ()).expand(partialTicks / 50, partialTicks / 50, partialTicks / 50).expand(0.05, 0.05, 0.05);
    	LaserCollisionBoxes.addLaserCollision(new LaserToRender(laser, panelOutline, x, y, z, panel.getPos(), null, 0.7F, false));

    }
}
