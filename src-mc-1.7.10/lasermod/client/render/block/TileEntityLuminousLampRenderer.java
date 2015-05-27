package lasermod.client.render.block;

import lasermod.ModBlocks;
import lasermod.api.LaserInGame;
import lasermod.client.render.LaserRenderer;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.tileentity.TileEntityLuminousLamp;
import lasermod.util.LaserUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import org.lwjgl.opengl.GL11;

/**
 * @author ProPercivalalb
 */
public class TileEntityLuminousLampRenderer extends TileEntitySpecialRenderer {

    public void renderLuminousPanel(TileEntityLuminousLamp panel, double x, double y, double z, float tick) {
    	LaserInGame laser = panel.getCombinedOutputLaser();
    	if(laser == null) return;
    	
    	GL11.glPushMatrix();
    	LaserRenderer.preLaserRender();
    	ModBlocks.luminousLamp.setBlockBoundsBasedOnState(panel.getWorldObj(), (int)x, (int)y, (int)z);
    	AxisAlignedBB panelOutline = AxisAlignedBB.getBoundingBox(x + ModBlocks.luminousLamp.getBlockBoundsMinX(), y + ModBlocks.luminousLamp.getBlockBoundsMinY(), z + ModBlocks.luminousLamp.getBlockBoundsMinZ(), x + ModBlocks.luminousLamp.getBlockBoundsMaxX(), y + ModBlocks.luminousLamp.getBlockBoundsMaxY(), z + ModBlocks.luminousLamp.getBlockBoundsMaxZ());
    	GL11.glColor4f(laser.red / 255F, laser.green / 255F, laser.blue / 255F, 0.7F);
    	LaserRenderer.drawBoundingBox(panelOutline.expand(tick / 50, tick / 50, tick / 50).expand(0.05, 0.05, 0.05));

    	LaserRenderer.postLaserRender();
        GL11.glPopMatrix();

    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
        renderLuminousPanel((TileEntityLuminousLamp)tileEntity, x, y, z, tick);
    }
}
