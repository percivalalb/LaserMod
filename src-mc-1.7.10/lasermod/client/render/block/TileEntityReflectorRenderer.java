package lasermod.client.render.block;

import lasermod.api.LaserCollisionBoxes;
import lasermod.api.LaserInGame;
import lasermod.api.LaserToRender;
import lasermod.client.model.block.ModelReflector;
import lasermod.helper.ClientHelper;
import lasermod.lib.ResourceReference;
import lasermod.tileentity.TileEntityReflector;
import lasermod.util.LaserUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

/**
 * @author ProPercivalalb
 */
public class TileEntityReflectorRenderer extends TileEntitySpecialRenderer {

	private ModelReflector modelReflector = new ModelReflector();
	
    public void renderReflector(TileEntityReflector reflector, double x, double y, double z, float tick) {
        GL11.glPushMatrix();
  
    	this.bindTexture(ResourceReference.REFLECTOR_MODEL);
        GL11.glTranslated(x + 0.5F, y + 1.5F, z + 0.5F);
        GL11.glScalef(1.0F, -1F, -1F);
        this.modelReflector.renderModel(reflector);
        GL11.glPopMatrix();
        
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			if(reflector.closedSides[dir.ordinal()] || reflector.containsInputSide(dir) || reflector.noLaserInputs())
				continue;
		  	LaserInGame laserInGame = reflector.getOutputLaser(dir);
	    	float alpha = laserInGame.shouldRenderLaser(ClientHelper.getPlayer());

	    	if(alpha == 0.0F)
	    		continue;
	    	
			AxisAlignedBB boundingBox = LaserUtil.getLaserOutline(reflector, dir, x, y, z);
			LaserCollisionBoxes.addLaserCollision(new LaserToRender(laserInGame, boundingBox, x, y, z, reflector.xCoord, reflector.yCoord, reflector.zCoord, dir, alpha, true));
		}
        
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
        renderReflector((TileEntityReflector) tileEntity, x, y, z, tick);
    }
}
