package lasermod.client.render.block;

import org.lwjgl.opengl.GL11;

import lasermod.client.render.LaserRenderer;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.util.LaserUtil;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

/**
 * @author ProPercivalalb
 */
public class TileEntityBasicLaserRenderer extends TileEntitySpecialRenderer {

    public void renderBasicLaser(TileEntityBasicLaser basicLaser, double x, double y, double z, float tick) {
    	if(!basicLaser.func_145831_w().isBlockIndirectlyGettingPowered(basicLaser.field_145851_c, basicLaser.field_145848_d, basicLaser.field_145849_e))
    		return;
    	GL11.glPushMatrix();
    	RenderHelper.disableStandardItemLighting();
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 160F, 160F);
    	GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
		
        AxisAlignedBB laserOutline = LaserUtil.getLaserOutline(basicLaser, basicLaser.func_145832_p(), x, y, z);
    	GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.4F);
    	LaserRenderer.drawBoundingBox(laserOutline);
    	LaserRenderer.drawBoundingBox(laserOutline.contract(0.12D, 0.12D, 0.12D));

        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();

    }

    @Override
    public void func_147500_a(TileEntity tileEntity, double x, double y, double z, float tick) {
        renderBasicLaser((TileEntityBasicLaser)tileEntity, x, y, z, tick);
    }
}
