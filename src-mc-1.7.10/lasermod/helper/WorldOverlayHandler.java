package lasermod.helper;

import java.util.ArrayList;

import lasermod.api.LaserCollisionBoxes;
import lasermod.api.LaserInGame;
import lasermod.api.LaserToRender;
import lasermod.client.render.LaserRenderer;
import lasermod.util.LaserUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * @author ProPercivalalb
 */
public class WorldOverlayHandler {
	
	private static Minecraft mc = Minecraft.getMinecraft();
	private static boolean hasCheckedVersion = false;
	
	@SubscribeEvent
	public void onWorldRenderLast(RenderWorldLastEvent event) {

	    for (int i = 0; i < LaserCollisionBoxes.lasers.size(); ++i) {
        	LaserToRender ltr = LaserCollisionBoxes.lasers.get(i);
        	AxisAlignedBB axisalignedbb = ltr.collision;
        	LaserInGame laserInGame = ltr.laser;
        	
			GL11.glPushMatrix();
	    	LaserRenderer.preLaserRender();
	
	    	float alpha = laserInGame.shouldRenderLaser(ClientHelper.getPlayer());

	    	if(alpha == 0.0F)
	    		continue;
	    	
	        GL11.glColor4f(laserInGame.red / 255F, laserInGame.green / 255F, laserInGame.blue / 255F, alpha);
	    	LaserRenderer.drawBoundingBox(axisalignedbb);
	    	LaserRenderer.drawBoundingBox(axisalignedbb.contract(0.1D, 0.1D, 0.1D));
	
	    	LaserRenderer.postLaserRender();
	        GL11.glPopMatrix();

	    }
	    FMLLog.info("test1");
	    LaserCollisionBoxes.lasers2 = (ArrayList<LaserToRender>)LaserCollisionBoxes.lasers.clone();
	    LaserCollisionBoxes.lasers.clear();
	}
}
