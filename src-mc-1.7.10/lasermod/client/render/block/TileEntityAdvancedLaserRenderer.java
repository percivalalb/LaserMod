package lasermod.client.render.block;

import lasermod.api.LaserCollisionBoxes;
import lasermod.api.LaserInGame;
import lasermod.api.LaserToRender;
import lasermod.helper.ClientHelper;
import lasermod.tileentity.TileEntityAdvancedLaser;
import lasermod.util.LaserUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author ProPercivalalb
 */
public class TileEntityAdvancedLaserRenderer extends TileEntitySpecialRenderer {

    public void renderAdvancedLaser(TileEntityAdvancedLaser advancedLaser, double x, double y, double z, float tick) {
    	if(!advancedLaser.getWorldObj().isBlockIndirectlyGettingPowered(advancedLaser.xCoord, advancedLaser.yCoord, advancedLaser.zCoord))
    		return;
    	
    	LaserInGame laserInGame = advancedLaser.getOutputLaser(ForgeDirection.getOrientation(advancedLaser.getBlockMetadata()));
    	float alpha = laserInGame.shouldRenderLaser(ClientHelper.getPlayer());

    	if(alpha == 0.0F)
    		return;


        AxisAlignedBB laserOutline = LaserUtil.getLaserOutline(advancedLaser, ForgeDirection.getOrientation(advancedLaser.getBlockMetadata()), x, y, z);
        LaserCollisionBoxes.addLaserCollision(new LaserToRender(laserInGame, laserOutline, x, y, z, advancedLaser.xCoord, advancedLaser.yCoord, advancedLaser.zCoord, ForgeDirection.getOrientation(advancedLaser.getBlockMetadata()), alpha, true));

    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
    	renderAdvancedLaser((TileEntityAdvancedLaser)tileEntity, x, y, z, tick);
    }
}
