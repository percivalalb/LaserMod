package lasermod.helper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import lasermod.ModItems;
import lasermod.api.ILaser;
import lasermod.api.ILaserProvider;
import lasermod.api.ILaserReceiver;
import lasermod.api.LaserCollisionBoxes;
import lasermod.api.LaserInGame;
import lasermod.api.LaserRegistry;
import lasermod.api.LaserToRender;
import lasermod.util.BlockActionPos;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author ProPercivalalb
 **/
public class ScreenRenderHandler {

	@SubscribeEvent
	public void onPreRenderGameOverlay(RenderGameOverlayEvent.Pre event) {
		
		ElementType type = event.type;
		EntityPlayer player = ClientHelper.getPlayer();
		World world = player.world;
		ItemStack stack = ClientHelper.mc.player.getHeldItem();
		
		if(event.type == RenderGameOverlayEvent.ElementType.HELMET && stack != null && stack.getItem() == ModItems.handheldSensor) {
			
			double reach = ClientHelper.mc.playerController.getBlockReachDistance();
			
	        MovingObjectPosition mop = ClientHelper.mc.getRenderViewEntity().rayTrace(reach, event.partialTicks);

	        Vec3 posVec = ClientHelper.mc.getRenderViewEntity().getPositionEyes(event.partialTicks);

			if(mop != null)
				reach = mop.hitVec.distanceTo(posVec);
			
	        Vec3 lookVec = ClientHelper.mc.getRenderViewEntity().getLook(event.partialTicks);
	        Vec3 combinedVec = posVec.addVector(lookVec.xCoord * reach, lookVec.yCoord * reach, lookVec.zCoord * reach);
			
	        Vec3 finalVec = null;


            int lookingAtIndex = -1;
            for (int i = 0; i < LaserCollisionBoxes.lasers2.size(); ++i) {
            	LaserToRender ltr = LaserCollisionBoxes.lasers2.get(i);
            	if(!ltr.tooltip) continue;
            	AxisAlignedBB axisalignedbb = ltr.collision.offset(ltr.pos.getX(), ltr.pos.getY(), ltr.pos.getZ()).offset(-ltr.renderX, -ltr.renderY, -ltr.renderZ);
            	
                MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(posVec, combinedVec);
                if (axisalignedbb.isVecInside(posVec)) {
                    if (0.0D < reach || reach == 0.0D) {
                    	lookingAtIndex = i;
                        finalVec = movingobjectposition == null ? posVec : movingobjectposition.hitVec;
                        reach = 0.0D;
                    }
                }
                else if (movingobjectposition != null) {
                    double d3 = posVec.distanceTo(movingobjectposition.hitVec);

                    if (d3 < reach || reach == 0.0D) {
                    	lookingAtIndex = i;
                        finalVec = movingobjectposition.hitVec;
                        reach = d3;
                    }
                }
           
            }
	        
            if(lookingAtIndex != -1) {
            	LaserToRender ltr = LaserCollisionBoxes.lasers2.get(lookingAtIndex);
            	LaserInGame laser = ltr.laser;
            	Color colour = new Color(laser.red, laser.green, laser.blue);
            	List<String> list = new ArrayList<String>();
            	list.add("Laser");
            	
            	list.add("   Colour: " + laser.red + ", " + laser.green + ", " + laser.blue + " - rgb");
            	list.add("Orgin: " + ltr.pos.getX() + ", " + ltr.pos.getY() + ", " + ltr.pos.getZ());
            	list.add("Direction traveling: " + laser.getDirection().getOpposite().name());
    
            	list.add("Types:");
            	if(laser.getLaserType().size() > 0) {
	            	String types = " ";
	    			for(ILaser laserType : laser.getLaserType())
	    				types += "[" + LaserRegistry.getIdFromLaser(laserType) + "] ";
	    			list.add(types + "");
            	}
            	else
            		list.add(" None");
            	
            	drawHoveringText(list, 0, 25, 1000, 200, ClientHelper.mc.fontRenderer);
                this.drawRect(12, 24, 12 + 9, 24 + 9, colour.getRGB());
            	
            }
            else if(mop != null && mop.typeOfHit == MovingObjectType.BLOCK) {
            	List<String> list = new ArrayList<String>();
            	
            	BlockActionPos action = new BlockActionPos(world, mop.getBlockPos());
             	if(action.isLaserReceiver(null)) {
            		list.add("Receiving");
             		
            		ILaserReceiver reciver = action.getLaserReceiver(null);
            		List<LaserInGame> output = reciver.getInputLasers();
            		for(LaserInGame laser : output) {
            			if(laser == null) continue;
            			Color colour = new Color(laser.red, laser.green, laser.blue);
            			this.drawRect(22, 24, 22 + 9, 24 + 9, colour.getRGB());
            			
            			list.add("   " + laser.getDirection().name() + ", Power: " + laser.getStrength());
            		}
            		
            		if(output.size() == 0)
            			list.add("   Nothing");
            	}
            	if(action.isLaserProvider(null)) {
            		ILaserProvider provider = action.getLaserProvider(null);
            		list.add("Emitting");
            		List<LaserInGame> input = provider.getOutputLasers();
            		for(LaserInGame laser : provider.getOutputLasers()) {
            			if(laser == null) continue;
            			Color colour = new Color(laser.red, laser.green, laser.blue);
            			this.drawRect(22, 24, 22 + 9, 24 + 9, colour.getRGB());
            			
            			list.add("   " + laser.getDirection().getOpposite().name() + ", Power: " + laser.getStrength() + ", Range: " + provider.getDistance(laser.getDirection()));
            		}
            		
            		if(input.size() == 0)
            			list.add("   Nothing");
            	}
  
            	drawHoveringText(list, 0, 25, 1000, 200, ClientHelper.mc.fontRenderer);
            	
        		int i = 0;
            	if(action.isLaserReceiver(null)) {
            		i += 24;
            		ILaserReceiver reciver = action.getLaserReceiver(null);
            		for(LaserInGame laser : reciver.getInputLasers()) {
            			if(laser == null) continue;
            			Color colour = new Color(laser.red, laser.green, laser.blue);
            			this.drawRect(12, i, 12 + 9, i + 9, colour.getRGB());
            			i += 10;
            		}
            	}
            	else {
            		i += 14;
            	}
            	
            	if(action.isLaserProvider(null)) {
            		i += 10;
            		ILaserProvider provider = action.getLaserProvider(null);
            		for(LaserInGame laser : provider.getOutputLasers()) {
            			if(laser == null) continue;
            			Color colour = new Color(laser.red, laser.green, laser.blue);
            			this.drawRect(12, i, 12 + 9, i + 9, colour.getRGB());
            			i += 10;
            		}
            	}
            }
		}
	}
	
	public void drawHoveringText(List par1List, int mouseX, int mouseY, int width, int height, FontRenderer font) {
        if (!par1List.isEmpty()) {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;
            Iterator iterator = par1List.iterator();

            while (iterator.hasNext())
            {
                String s = (String)iterator.next();
                int l = font.getStringWidth(s);

                if (l > k)
                {
                    k = l;
                }
            }

            int i1 = mouseX + 12;
            int j1 = mouseY - 12;
            int k1 = 8;

            if (par1List.size() > 1)
            {
                k1 += 2 + (par1List.size() - 1) * 10;
            }

            if (i1 + k > width)
            {
                i1 -= 28 + k;
            }

            if (j1 + k1 + 6 > height)
            {
                j1 = height - k1 - 6;
            }

            int l1 = Color.darkGray.getRGB();
            this.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
            this.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
            this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = Color.gray.getRGB();
            int j2 = Color.gray.getRGB();
            this.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
            this.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);

            for (int k2 = 0; k2 < par1List.size(); ++k2) {
                String s1 = (String)par1List.get(k2);
                font.drawStringWithShadow(s1, i1, j1, -1);
                if (k2 == 0)
                {
                    j1 += 2;
                }

                j1 += 10;
            }
            
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        }
    }
	
	  protected void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor)
	    {
	        float f = (float)(startColor >> 24 & 255) / 255.0F;
	        float f1 = (float)(startColor >> 16 & 255) / 255.0F;
	        float f2 = (float)(startColor >> 8 & 255) / 255.0F;
	        float f3 = (float)(startColor & 255) / 255.0F;
	        float f4 = (float)(endColor >> 24 & 255) / 255.0F;
	        float f5 = (float)(endColor >> 16 & 255) / 255.0F;
	        float f6 = (float)(endColor >> 8 & 255) / 255.0F;
	        float f7 = (float)(endColor & 255) / 255.0F;
	        GlStateManager.disableTexture2D();
	        GlStateManager.enableBlend();
	        GlStateManager.disableAlpha();
	        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
	        GlStateManager.shadeModel(7425);
	        Tessellator tessellator = Tessellator.getInstance();
	        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
	        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
	        worldrenderer.pos((double)right, (double)top, (double)300).color(f1, f2, f3, f).endVertex();
	        worldrenderer.pos((double)left, (double)top, (double)300).color(f1, f2, f3, f).endVertex();
	        worldrenderer.pos((double)left, (double)bottom, (double)300).color(f5, f6, f7, f4).endVertex();
	        worldrenderer.pos((double)right, (double)bottom, (double)300).color(f5, f6, f7, f4).endVertex();
	        tessellator.draw();
	        GlStateManager.shadeModel(7424);
	        GlStateManager.disableBlend();
	        GlStateManager.enableAlpha();
	        GlStateManager.enableTexture2D();
	    }
	
	 
	  public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
	    {
	        float f = 0.00390625F;
	        float f1 = 0.00390625F;
	        Tessellator tessellator = Tessellator.getInstance();
	        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
	        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
	        worldrenderer.pos((double)(x + 0), (double)(y + height), (double)100).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + height) * f1)).endVertex();
	        worldrenderer.pos((double)(x + width), (double)(y + height), (double)100).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + height) * f1)).endVertex();
	        worldrenderer.pos((double)(x + width), (double)(y + 0), (double)100).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + 0) * f1)).endVertex();
	        worldrenderer.pos((double)(x + 0), (double)(y + 0), (double)100).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + 0) * f1)).endVertex();
	        tessellator.draw();
	    }
	 
    
	  public static void drawRect(int left, int top, int right, int bottom, int color)
	    {
	        if (left < right)
	        {
	            int i = left;
	            left = right;
	            right = i;
	        }

	        if (top < bottom)
	        {
	            int j = top;
	            top = bottom;
	            bottom = j;
	        }

	        float f3 = (float)(color >> 24 & 255) / 255.0F;
	        float f = (float)(color >> 16 & 255) / 255.0F;
	        float f1 = (float)(color >> 8 & 255) / 255.0F;
	        float f2 = (float)(color & 255) / 255.0F;
	        Tessellator tessellator = Tessellator.getInstance();
	        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
	        GlStateManager.enableBlend();
	        GlStateManager.disableTexture2D();
	        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
	        GlStateManager.color(f, f1, f2, f3);
	        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
	        worldrenderer.pos((double)left, (double)bottom, 0.0D).endVertex();
	        worldrenderer.pos((double)right, (double)bottom, 0.0D).endVertex();
	        worldrenderer.pos((double)right, (double)top, 0.0D).endVertex();
	        worldrenderer.pos((double)left, (double)top, 0.0D).endVertex();
	        tessellator.draw();
	        GlStateManager.enableTexture2D();
	        GlStateManager.disableBlend();
	    }
}
