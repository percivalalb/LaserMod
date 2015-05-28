package lasermod.client.render.block;

import lasermod.api.ILaser;
import lasermod.api.LaserCollisionBoxes;
import lasermod.api.LaserInGame;
import lasermod.api.LaserToRender;
import lasermod.client.model.block.ModelReflector;
import lasermod.client.render.LaserRenderer;
import lasermod.helper.ClientHelper;
import lasermod.lib.ResourceReference;
import lasermod.tileentity.TileEntityReflector;
import lasermod.util.LaserUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLLog;

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
        
		for(int i = 0; i < reflector.closedSides.length; ++i) {
			if(reflector.closedSides[i] || reflector.containsInputSide(i) || reflector.lasers.size() == 0)
				continue;
		  	LaserInGame laserInGame = reflector.getOutputLaser(i);
	    	float alpha = laserInGame.shouldRenderLaser(ClientHelper.getPlayer());

	    	if(alpha == 0.0F)
	    		continue;
	    	
			AxisAlignedBB boundingBox = LaserUtil.getLaserOutline(reflector, i, x, y, z);
			LaserCollisionBoxes.addLaserCollision(new LaserToRender(laserInGame, boundingBox, x, y, z, reflector.xCoord, reflector.yCoord, reflector.zCoord, i));
		}
        
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
        renderReflector((TileEntityReflector) tileEntity, x, y, z, tick);
    }
}
