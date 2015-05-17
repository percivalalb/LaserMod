package lasermod.client.render.block;

import lasermod.api.LaserInGame;
import lasermod.client.render.LaserRenderer;
import lasermod.helper.ClientHelper;
import lasermod.proxy.ClientProxy;
import lasermod.tileentity.TileEntityAdvancedLaser;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.util.LaserUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import org.lwjgl.opengl.GL11;

/**
 * @author ProPercivalalb
 */
public class TileEntityAdvancedLaserRenderer extends TileEntitySpecialRenderer {

    public void renderAdvancedLaser(TileEntityAdvancedLaser advancedLaser, double x, double y, double z, float tick) {
    	if(!advancedLaser.getWorldObj().isBlockIndirectlyGettingPowered(advancedLaser.xCoord, advancedLaser.yCoord, advancedLaser.zCoord))
    		return;
    	
    	LaserInGame laserInGame = advancedLaser.getOutputLaser(advancedLaser.getBlockMetadata());
    	float alpha = laserInGame.shouldRenderLaser(ClientHelper.getPlayer());

    	if(alpha == 0.0F)
    		return;
    	
    	GL11.glPushMatrix();
    	LaserRenderer.preLaserRender();

        AxisAlignedBB laserOutline = LaserUtil.getLaserOutline(advancedLaser, advancedLaser.getBlockMetadata(), x, y, z);
        GL11.glColor4f(laserInGame.red / 255F, laserInGame.green / 255F, laserInGame.blue / 255F, alpha);
    	LaserRenderer.drawBoundingBox(laserOutline);
    	LaserRenderer.drawBoundingBox(laserOutline.contract(0.12D, 0.12D, 0.12D));

    	LaserRenderer.postLaserRender();
        GL11.glPopMatrix();

    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
    	renderAdvancedLaser((TileEntityAdvancedLaser)tileEntity, x, y, z, tick);
    }
}
