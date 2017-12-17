package lasermod.client.render.block;

import org.lwjgl.opengl.GL11;

import lasermod.api.LaserCollisionBoxes;
import lasermod.api.LaserInGame;
import lasermod.api.LaserToRender;
import lasermod.client.model.block.ModelReflector;
import lasermod.helper.ClientHelper;
import lasermod.lib.ResourceReference;
import lasermod.tileentity.TileEntityReflector;
import lasermod.util.LaserUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.EnumFacing;

/**
 * @author ProPercivalalb
 */
public class TileEntityReflectorRenderer extends TileEntitySpecialRenderer<TileEntityReflector> {

	private ModelReflector modelReflector = new ModelReflector();
	
	@Override
    public void renderTileEntityAt(TileEntityReflector reflector, double x, double y, double z, float partialTicks, int destroyStage) {
        GL11.glPushMatrix();
  
    	this.bindTexture(ResourceReference.REFLECTOR_MODEL);
        GL11.glTranslated(x + 0.5F, y + 1.5F, z + 0.5F);
        GL11.glScalef(1.0F, -1F, -1F);
        this.modelReflector.renderModel(reflector);
        GL11.glPopMatrix();
        
		for(EnumFacing dir : EnumFacing.VALUES) {
			if(reflector.closedSides[dir.ordinal()] || reflector.containsInputSide(dir) || reflector.noLaserInputs())
				continue;
		  	LaserInGame laserInGame = reflector.getOutputLaser(dir);
	    	float alpha = laserInGame.shouldRenderLaser(ClientHelper.getPlayer());

	    	if(alpha == 0.0F)
	    		continue;
	    	
			AxisAlignedBB boundingBox = LaserUtil.getLaserOutline(reflector, dir, x, y, z);
			LaserCollisionBoxes.addLaserCollision(new LaserToRender(laserInGame, boundingBox, x, y, z, reflector.getPos(), dir, alpha, true));
		}
        
    }
}
