package lasermod.client.render.block;

import org.lwjgl.opengl.GL11;

import lasermod.api.LaserCollisionBoxes;
import lasermod.api.LaserInGame;
import lasermod.api.LaserToRender;
import lasermod.block.BlockBasicLaser;
import lasermod.block.BlockReflector;
import lasermod.client.model.block.ModelReflector;
import lasermod.helper.ClientHelper;
import lasermod.lib.ResourceReference;
import lasermod.tileentity.TileEntityColourConverter;
import lasermod.tileentity.TileEntityLuminousLamp;
import lasermod.tileentity.TileEntityReflector;
import lasermod.util.LaserUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraft.util.EnumFacing;

/**
 * @author ProPercivalalb
 */
public class TileEntityReflectorRenderer extends TileEntitySpecialRenderer<TileEntityReflector> {

	private ModelReflector modelReflector = new ModelReflector();
	
	@Override
	public void render(TileEntityReflector te, double x, double y, double z, float partialTicks, int destroyStage, float a) {
		
		//The block
        GL11.glPushMatrix();
    	this.bindTexture(ResourceReference.REFLECTOR_MODEL);
        GL11.glTranslated(x + 0.5F, y + 1.5F, z + 0.5F);
        GL11.glScalef(1.0F, -1F, -1F);
        this.modelReflector.renderModel(te);
        GL11.glPopMatrix();
        
        //Lasers
        if(te.hasWorld() && te.getWorld().getBlockState(te.getPos()).getValue(BlockReflector.POWERED)) {
			for(EnumFacing dir : EnumFacing.VALUES) {
				if(te.closedSides[dir.ordinal()] || te.containsInputSide(dir) || te.noLaserInputs())
					continue;
				
			  	LaserInGame laserInGame = te.getOutputLaser(dir);
		    	float alpha = laserInGame.shouldRenderLaser(ClientHelper.getPlayer());
	
		    	if(alpha == 0.0F)
		    		continue;
		    	
				AxisAlignedBB boundingBox = LaserUtil.getLaserOutline(te, dir, x, y, z);
				LaserCollisionBoxes.addLaserCollision(new LaserToRender(laserInGame, boundingBox, x, y, z, te.getPos(), dir, alpha, true));
			}
        }
        
    }
	
	@Override
	public boolean isGlobalRenderer(TileEntityReflector te) {
        return true;
    }
}
