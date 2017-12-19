package lasermod.client.render.block;

import lasermod.api.LaserCollisionBoxes;
import lasermod.api.LaserInGame;
import lasermod.api.LaserToRender;
import lasermod.helper.ClientHelper;
import lasermod.tileentity.TileEntityReflector;
import lasermod.tileentity.TileEntitySmallColourConverter;
import lasermod.util.LaserUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.EnumFacing;

/**
 * @author ProPercivalalb
 */
public class TileEntitySmallColourConverterRenderer extends TileEntitySpecialRenderer<TileEntitySmallColourConverter> {

	@Override
	public void render(TileEntitySmallColourConverter colourConverter, double x, double y, double z, float partialTicks, int destroyStage, float a) {
    	if(colourConverter.getOutputLaser(EnumFacing.getFront(colourConverter.getBlockMetadata())) == null)
    		return;
    	LaserInGame laserInGame = colourConverter.getOutputLaser(EnumFacing.getFront(colourConverter.getBlockMetadata()));
    	float alpha = laserInGame.shouldRenderLaser(ClientHelper.getPlayer());

    	if(alpha == 0.0F)
    		return;
    	
		AxisAlignedBB boundingBox = LaserUtil.getLaserOutline(colourConverter, EnumFacing.getFront(colourConverter.getBlockMetadata()), x, y, z);
		LaserCollisionBoxes.addLaserCollision(new LaserToRender(laserInGame, boundingBox, x, y, z, colourConverter.getPos(), EnumFacing.getFront(colourConverter.getBlockMetadata()), alpha, true));

    }
	
	@Override
	public boolean isGlobalRenderer(TileEntitySmallColourConverter te) {
        return true;
    }
}
