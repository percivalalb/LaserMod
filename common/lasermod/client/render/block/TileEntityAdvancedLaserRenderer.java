package lasermod.client.render.block;

import lasermod.api.LaserInGame;
import lasermod.core.proxy.ClientProxy;
import lasermod.tileentity.TileEntityAdvancedLaser;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import org.lwjgl.opengl.GL11;

/**
 * @author ProPercivalalb
 */
public class TileEntityAdvancedLaserRenderer extends TileEntitySpecialRenderer {
	public void renderAdvancedLaser(TileEntityAdvancedLaser advancedLaser, double x, double y, double z, float tick) {
		if (!advancedLaser.worldObj.isBlockIndirectlyGettingPowered(advancedLaser.xCoord, advancedLaser.yCoord, advancedLaser.zCoord))
			return;
		LaserInGame laserInGame = advancedLaser.getCreatedLaser();
		float alpha = laserInGame.shouldRenderLaser(ClientProxy.mc.thePlayer);

		if (alpha == 0.0F)
			return;

		GL11.glPushMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(false);
		// GL11.glDisable(GL11.GL_DEPTH_TEST); // Make the line see thought blocks
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Tessellator tessellator = Tessellator.instance;
		tessellator.setColorRGBA(laserInGame.red, laserInGame.green, laserInGame.blue, 155);
		advancedLaser.last = advancedLaser.getLaserBox(x, y, z);
		GL11.glColor4f(laserInGame.red / 255F, laserInGame.green / 255F, laserInGame.blue / 255F, alpha);
		drawBoundingBox(advancedLaser.last);
		drawBoundingBox(advancedLaser.last.contract(0.12D, 0.12D, 0.12D));

		// GL11.glEnable(GL11.GL_DEPTH_TEST); //Make the line see thought blocks
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
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
		renderAdvancedLaser((TileEntityAdvancedLaser) tileEntity, x, y, z, tick);
	}
}