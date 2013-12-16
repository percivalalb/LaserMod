package lasermod.client.render.block;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import lasermod.api.LaserInGame;
import lasermod.client.model.block.ModelReflector;
import lasermod.core.helper.LogHelper;
import lasermod.core.proxy.ClientProxy;
import lasermod.lib.ResourceReference;
import lasermod.tileentity.TileEntityReflector;
import net.minecraft.client.gui.Gui;
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
    	GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDepthMask(false);
        //GL11.glDisable(GL11.GL_DEPTH_TEST); //Make the line see thought blocks
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        Tessellator tessellator = Tessellator.instance;
		for(int i = 0; i < reflector.openSides.length; ++i) {
			if(reflector.openSides[i] || reflector.containsInputSide(i) || reflector.lasers.size() == 0)
				continue;
		  	LaserInGame laserInGame = reflector.getCreatedLaser(i);
	    	float alpha = laserInGame.shouldRenderLaser(ClientProxy.mc.thePlayer);
	    	
	    	if(alpha == 0.0F)
	    		continue;
	    	
			tessellator.setColorRGBA(laserInGame.red, laserInGame.green, laserInGame.blue, 155);
	    	GL11.glColor4f(laserInGame.red / 255F, laserInGame.green / 255F, laserInGame.blue / 255F, alpha);
	    	
			AxisAlignedBB boundingBox = reflector.getFromLaserBox(x, y, z, i);
			
	    	drawBoundingBox(boundingBox);
	    	drawBoundingBox(boundingBox.contract(0.12D, 0.12D, 0.12D));
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
    
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
        renderReflector((TileEntityReflector) tileEntity, x, y, z, tick);
    }
}
