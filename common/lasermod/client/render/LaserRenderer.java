package lasermod.client.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.AxisAlignedBB;

/**
 * @author ProPercivalalb
 */
public class LaserRenderer {
	
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
