package lasermod.client.render.block;

import lasermod.api.LaserCollisionBoxes;
import lasermod.api.LaserInGame;
import lasermod.api.LaserToRender;
import lasermod.block.BlockReflector;
import lasermod.client.model.block.ModelReflector;
import lasermod.helper.ClientHelper;
import lasermod.lib.ResourceReference;
import lasermod.tileentity.TileEntityReflector;
import lasermod.util.LaserUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
@SideOnly(value = Side.CLIENT)
public class TileEntityReflectorRenderer extends TileEntitySpecialRenderer<TileEntityReflector> {

	private ModelReflector modelReflector = new ModelReflector();
	
	@Override
	public void render(TileEntityReflector te, double x, double y, double z, float partialTicks, int destroyStage, float a) {
		
		//The block
		GlStateManager.pushMatrix();
    	this.bindTexture(ResourceReference.REFLECTOR_MODEL);
    	GlStateManager.translate(x + 0.5F, y + 1.5F, z + 0.5F);
    	GlStateManager.scale(1.0F, -1F, -1F);
        this.modelReflector.renderModel(te);
		GlStateManager.popMatrix();
        
        //Lasers
        if(te.hasWorld() && te.getWorld().getBlockState(te.getPos()).getValue(BlockReflector.POWERED)) {
			for(EnumFacing dir : EnumFacing.VALUES) {
				if(te.sideClosed[dir.ordinal()] || te.containsInputSide(dir) || te.noLaserInputs())
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
