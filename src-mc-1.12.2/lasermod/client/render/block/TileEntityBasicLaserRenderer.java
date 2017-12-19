package lasermod.client.render.block;

import lasermod.api.LaserCollisionBoxes;
import lasermod.api.LaserInGame;
import lasermod.api.LaserToRender;
import lasermod.block.BlockBasicLaser;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.util.LaserUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;

/**
 * @author ProPercivalalb
 */
public class TileEntityBasicLaserRenderer extends TileEntitySpecialRenderer<TileEntityBasicLaser> {

	@Override
    public void render(TileEntityBasicLaser te, double x, double y, double z, float partialTicks, int destroyStage, float a) {
		if(te.hasWorld() && te.getWorld().getBlockState(te.getPos()).getValue(BlockBasicLaser.POWERED)) {
	    	EnumFacing facing = te.getWorld().getBlockState(te.getPos()).getValue(BlockBasicLaser.FACING);
			
			
	    	LaserInGame laserInGame = te.getOutputLaser(facing);
	
	
	        AxisAlignedBB laserOutline = LaserUtil.getLaserOutline(te, facing, x, y, z);
	        LaserCollisionBoxes.addLaserCollision(new LaserToRender(laserInGame, laserOutline, x, y, z, te.getPos(), facing, 0.4F, true));
		}
    }
	
	@Override
	public boolean isGlobalRenderer(TileEntityBasicLaser te) {
        return true;
    }
}
