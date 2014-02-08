package lasermod.client.render.block;

import lasermod.api.LaserInGame;
import lasermod.client.render.LaserRenderer;
import lasermod.helper.ClientHelper;
import lasermod.tileentity.TileEntityColourConverter;
import lasermod.util.LaserUtil;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import org.lwjgl.opengl.GL11;

/**
 * @author ProPercivalalb
 */
public class TileEntityColourConverterRenderer extends TileEntitySpecialRenderer {

    public void renderColourConverter(TileEntityColourConverter colourConverter, double x, double y, double z, float tick) {
    	if(colourConverter.getOutputLaser(colourConverter.getBlockMetadata()) == null)
    		return;
    	LaserInGame laserInGame = colourConverter.getOutputLaser(colourConverter.getBlockMetadata());
    	float alpha = laserInGame.shouldRenderLaser(ClientHelper.getPlayer());

    	if(alpha == 0.0F)
    		return;
    	
    	GL11.glPushMatrix();
    	RenderHelper.disableStandardItemLighting();
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 160F, 160F);
    	GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDepthMask(false);
        //GL11.glDisable(GL11.GL_DEPTH_TEST); //Make the line see thought blocks
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		AxisAlignedBB boundingBox = LaserUtil.getLaserOutline(colourConverter, colourConverter.getBlockMetadata(), x, y, z);
    	GL11.glColor4f(laserInGame.red / 255F, laserInGame.green / 255F, laserInGame.blue / 255F, alpha);
    	LaserRenderer.drawBoundingBox(boundingBox);
    	LaserRenderer.drawBoundingBox(boundingBox.contract(0.12D, 0.12D, 0.12D));
         
        //GL11.glEnable(GL11.GL_DEPTH_TEST); //Make the line see thought blocks
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();

    }
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
        renderColourConverter((TileEntityColourConverter)tileEntity, x, y, z, tick);
    }
}
