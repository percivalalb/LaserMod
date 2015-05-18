package lasermod.client.render;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.AxisAlignedBB;

import org.lwjgl.opengl.GL11;

/**
 * @author ProPercivalalb
 */
public class LaserRenderer {
	
	public static void preLaserRender() {
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		GL11.glDisable(GL11.GL_LIGHTING);

	    RenderHelper.disableStandardItemLighting();
	    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 160F, 160F);
	    GL11.glDisable(GL11.GL_ALPHA_TEST);
	    GL11.glDepthMask(false);
	    GL11.glEnable(GL11.GL_BLEND);
	    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	    GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	public static void postLaserRender() {
	     RenderHelper.enableStandardItemLighting();
	     GL11.glEnable(GL11.GL_ALPHA_TEST);
		 GL11.glDepthMask(true);
	     GL11.glDisable(GL11.GL_BLEND);
	     GL11.glEnable(GL11.GL_TEXTURE_2D);
	     GL11.glEnable(GL11.GL_LIGHTING);
	     GL11.glPopAttrib();
	}
	
    public static void drawBoundingBox(AxisAlignedBB boundingBox) {
	    Tessellator tessellator = Tessellator.instance;
	    tessellator.startDrawingQuads();
	    tessellator.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
	    tessellator.draw();
	}
}
