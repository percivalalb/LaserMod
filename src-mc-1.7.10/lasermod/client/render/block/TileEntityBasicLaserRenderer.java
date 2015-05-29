package lasermod.client.render.block;

import lasermod.api.LaserCollisionBoxes;
import lasermod.api.LaserToRender;
import lasermod.client.render.LaserRenderer;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.util.LaserUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import org.lwjgl.opengl.GL11;

/**
 * @author ProPercivalalb
 */
public class TileEntityBasicLaserRenderer extends TileEntitySpecialRenderer {

    public void renderBasicLaser(TileEntityBasicLaser basicLaser, double x, double y, double z, float tick) {
    	if(!basicLaser.getWorldObj().isBlockIndirectlyGettingPowered(basicLaser.xCoord, basicLaser.yCoord, basicLaser.zCoord))
    		return;

        AxisAlignedBB laserOutline = LaserUtil.getLaserOutline(basicLaser, basicLaser.getBlockMetadata(), x, y, z);
        LaserCollisionBoxes.addLaserCollision(new LaserToRender(basicLaser.getOutputLaser(basicLaser.blockMetadata), laserOutline, x, y, z, basicLaser.xCoord, basicLaser.yCoord, basicLaser.zCoord, basicLaser.getBlockMetadata(), 0.4F));

    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
        renderBasicLaser((TileEntityBasicLaser)tileEntity, x, y, z, tick);
    }
}
