package lasermod.client.render.world;

import lasermod.tileentity.TileEntityBasicLaser;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

/**
 * @author ProPercivalalb
 */
public class TileEntityBasicLaserRenderer extends TileEntitySpecialRenderer {

    public void renderBasicLaser(TileEntityBasicLaser basicLaser, double x, double y, double z, float tick) {
    	
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
        renderBasicLaser((TileEntityBasicLaser) tileEntity, x, y, z, tick);
    }
}
