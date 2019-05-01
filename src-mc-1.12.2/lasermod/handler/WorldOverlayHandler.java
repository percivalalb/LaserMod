package lasermod.handler;

import java.util.ArrayList;

import lasermod.api.LaserCollisionBoxes;
import lasermod.api.LaserInGame;
import lasermod.api.LaserToRender;
import lasermod.client.render.LaserRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
@SideOnly(value = Side.CLIENT)
public class WorldOverlayHandler {
	
	@SubscribeEvent
	public void onWorldRenderLast(RenderWorldLastEvent event) {
		
	    for (int i = 0; i < LaserCollisionBoxes.RENDER_LASERS.size(); ++i) {
        	LaserToRender ltr = LaserCollisionBoxes.RENDER_LASERS.get(i);
        	AxisAlignedBB axisalignedbb = ltr.collision;
        	LaserInGame laserInGame = ltr.laser;
        	
        	GlStateManager.pushMatrix();
	    	LaserRenderer.preLaserRender();
	    	
	    	GlStateManager.color(laserInGame.red / 255F, laserInGame.green / 255F, laserInGame.blue / 255F, ltr.alpha);
	        
	    	LaserRenderer.drawBoundingBox(axisalignedbb);
	    	LaserRenderer.drawBoundingBox(axisalignedbb.shrink(0.1D));
	
	    	LaserRenderer.postLaserRender();
	    	GlStateManager.popMatrix();

	    }
	    
	    LaserCollisionBoxes.INFO_LASERS = new ArrayList<LaserToRender>(LaserCollisionBoxes.RENDER_LASERS);
	    LaserCollisionBoxes.RENDER_LASERS.clear();
	}
}
