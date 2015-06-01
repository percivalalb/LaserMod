package lasermod.client.render.block;

import lasermod.api.LaserCollisionBoxes;
import lasermod.api.LaserToRender;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.util.LaserUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author ProPercivalalb
 */
public class TileEntityBasicLaserRenderer extends TileEntitySpecialRenderer {

    public void renderBasicLaser(TileEntityBasicLaser basicLaser, double x, double y, double z, float tick) {
    	if(!basicLaser.getWorldObj().isBlockIndirectlyGettingPowered(basicLaser.xCoord, basicLaser.yCoord, basicLaser.zCoord))
    		return;

        AxisAlignedBB laserOutline = LaserUtil.getLaserOutline(basicLaser, ForgeDirection.getOrientation(basicLaser.getBlockMetadata()), x, y, z);
        LaserCollisionBoxes.addLaserCollision(new LaserToRender(basicLaser.getOutputLaser(ForgeDirection.getOrientation(basicLaser.getBlockMetadata())), laserOutline, x, y, z, basicLaser.xCoord, basicLaser.yCoord, basicLaser.zCoord, ForgeDirection.getOrientation(basicLaser.getBlockMetadata()), 0.4F, true));

    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
        renderBasicLaser((TileEntityBasicLaser)tileEntity, x, y, z, tick);
    }
}
