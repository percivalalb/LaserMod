package lasermod.client.render.block;

import lasermod.ModBlocks;
import lasermod.client.render.LaserRenderer;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.tileentity.TileEntityLuminousPanel;
import lasermod.util.LaserUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import org.lwjgl.opengl.GL11;

/**
 * @author ProPercivalalb
 */
public class TileEntityLuminousPanelRenderer extends TileEntitySpecialRenderer {

    public void renderLuminousPanel(TileEntityLuminousPanel panel, double x, double y, double z, float tick) {
    	GL11.glPushMatrix();
    	LaserRenderer.preLaserRender();
    	ModBlocks.luminousPanel.setBlockBoundsBasedOnState(panel.getWorldObj(), (int)x, (int)y, (int)z);
    	AxisAlignedBB panelOutline = AxisAlignedBB.getBoundingBox(x + ModBlocks.luminousPanel.getBlockBoundsMinX(), y + ModBlocks.luminousPanel.getBlockBoundsMinY(), z + ModBlocks.luminousPanel.getBlockBoundsMinZ(), x + ModBlocks.luminousPanel.getBlockBoundsMaxX(), y + ModBlocks.luminousPanel.getBlockBoundsMaxY(), z + ModBlocks.luminousPanel.getBlockBoundsMaxZ());
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.4F);
    	LaserRenderer.drawBoundingBox(panelOutline.expand(tick / 15, tick  / 15, tick / 15));

    	LaserRenderer.postLaserRender();
        GL11.glPopMatrix();

    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
        renderLuminousPanel((TileEntityLuminousPanel)tileEntity, x, y, z, tick);
    }
}
