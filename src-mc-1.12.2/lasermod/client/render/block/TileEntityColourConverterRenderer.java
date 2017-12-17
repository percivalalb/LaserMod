package lasermod.client.render.block;

import lasermod.api.LaserCollisionBoxes;
import lasermod.api.LaserInGame;
import lasermod.api.LaserToRender;
import lasermod.helper.ClientHelper;
import lasermod.tileentity.TileEntityColourConverter;
import lasermod.util.LaserUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.EnumFacing;

/**
 * @author ProPercivalalb
 */
public class TileEntityColourConverterRenderer extends TileEntitySpecialRenderer<TileEntityColourConverter> {

	@Override
    public void renderTileEntityAt(TileEntityColourConverter colourConverter, double x, double y, double z, float partialTicks, int destroyStage) {
    	if(colourConverter.getOutputLaser(EnumFacing.getFront(colourConverter.getBlockMetadata())) == null)
    		return;
    	LaserInGame laserInGame = colourConverter.getOutputLaser(EnumFacing.getFront(colourConverter.getBlockMetadata()));
    	float alpha = laserInGame.shouldRenderLaser(ClientHelper.getPlayer());

    	if(alpha == 0.0F)
    		return;

		AxisAlignedBB boundingBox = LaserUtil.getLaserOutline(colourConverter, EnumFacing.getFront(colourConverter.getBlockMetadata()), x, y, z);
		LaserCollisionBoxes.addLaserCollision(new LaserToRender(laserInGame, boundingBox, x, y, z, colourConverter.getPos(), EnumFacing.getFront(colourConverter.getBlockMetadata()), alpha, true));

    }
}
