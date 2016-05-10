package lasermod.client.render.block;

import lasermod.api.LaserCollisionBoxes;
import lasermod.api.LaserInGame;
import lasermod.api.LaserToRender;
import lasermod.helper.ClientHelper;
import lasermod.tileentity.TileEntityAdvancedLaser;
import lasermod.util.LaserUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;

/**
 * @author ProPercivalalb
 */
public class TileEntityAdvancedLaserRenderer extends TileEntitySpecialRenderer<TileEntityAdvancedLaser> {

	@Override
    public void renderTileEntityAt(TileEntityAdvancedLaser advancedLaser, double x, double y, double z, float partialTicks, int destroyStage) {
    	if(advancedLaser.getWorld().isBlockIndirectlyGettingPowered(advancedLaser.getPos()) == 0)
    		return;
    	
    	LaserInGame laserInGame = advancedLaser.getOutputLaser(EnumFacing.getFront(advancedLaser.getBlockMetadata()));
    	float alpha = laserInGame.shouldRenderLaser(ClientHelper.getPlayer());

    	if(alpha == 0.0F)
    		return;


        AxisAlignedBB laserOutline = LaserUtil.getLaserOutline(advancedLaser, EnumFacing.getFront(advancedLaser.getBlockMetadata()), x, y, z);
        LaserCollisionBoxes.addLaserCollision(new LaserToRender(laserInGame, laserOutline, x, y, z, advancedLaser.getPos(), EnumFacing.getFront(advancedLaser.getBlockMetadata()), alpha, true));

    }
}
