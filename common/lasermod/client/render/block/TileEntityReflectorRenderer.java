package lasermod.client.render.block;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import lasermod.api.LaserInGame;
import lasermod.client.model.block.ModelReflector;
import lasermod.client.render.LaserRenderer;
import lasermod.lib.ResourceReference;
import lasermod.tileentity.TileEntityReflector;
import lasermod.util.LaserUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

/**
 * @author ProPercivalalb
 */
public class TileEntityReflectorRenderer extends TileEntitySpecialRenderer {

	private ModelReflector modelReflector = new ModelReflector();
	
    public void renderReflector(TileEntityReflector reflector, double x, double y, double z, float tick) {
    	this.bindTexture(ResourceReference.REFLECTOR_MODEL);
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5F, y + 1.5F, z + 0.5F);
        GL11.glScalef(1.0F, -1F, -1F);
        modelReflector.renderModel(reflector);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
    	RenderHelper.disableStandardItemLighting();
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 160F, 160F);
    	GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        GL11.glDisable(GL11.GL_TEXTURE_2D);
		for(int i = 0; i < reflector.openSides.length; ++i) {
			if(reflector.openSides[i] || reflector.containsInputSide(i) || reflector.lasers.size() == 0)
				continue;
		  	LaserInGame laserInGame = reflector.getOutputLaser(i);
	    	float alpha = 0.4F;//laserInGame.shouldRenderLaser(ClientProxy.mc.thePlayer);
	    	
	    	if(alpha == 0.0F)
	    		continue;
	    	
	    	GL11.glColor4f(laserInGame.red / 255F, laserInGame.green / 255F, laserInGame.blue / 255F, alpha);
	    	
			AxisAlignedBB boundingBox = LaserUtil.getLaserOutline(reflector, i, x, y, z);
			
	    	LaserRenderer.drawBoundingBox(boundingBox);
	    	LaserRenderer.drawBoundingBox(boundingBox.contract(0.12D, 0.12D, 0.12D));
		}
		
         
        //GL11.glEnable(GL11.GL_DEPTH_TEST); //Make the line see thought blocks
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LIGHTING); //Make the line see thought blocks
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
        
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
        renderReflector((TileEntityReflector) tileEntity, x, y, z, tick);
    }
}
