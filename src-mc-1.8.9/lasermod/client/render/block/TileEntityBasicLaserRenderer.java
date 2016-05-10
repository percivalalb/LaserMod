package lasermod.client.render.block;

import lasermod.api.LaserCollisionBoxes;
import lasermod.api.LaserToRender;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.util.LaserUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;

/**
 * @author ProPercivalalb
 */
public class TileEntityBasicLaserRenderer extends TileEntitySpecialRenderer<TileEntityBasicLaser> {

	@Override
    public void renderTileEntityAt(TileEntityBasicLaser basicLaser, double x, double y, double z, float partialTicks, int destroyStage) {
		if(basicLaser.getWorld().isBlockIndirectlyGettingPowered(basicLaser.getPos()) == 0)
    		return;

        AxisAlignedBB laserOutline = LaserUtil.getLaserOutline(basicLaser, EnumFacing.getFront(basicLaser.getBlockMetadata()), x, y, z);
        LaserCollisionBoxes.addLaserCollision(new LaserToRender(basicLaser.getOutputLaser(EnumFacing.getFront(basicLaser.getBlockMetadata())), laserOutline, x, y, z, basicLaser.getPos(), EnumFacing.getFront(basicLaser.getBlockMetadata()), 0.4F, true));

    }
}
