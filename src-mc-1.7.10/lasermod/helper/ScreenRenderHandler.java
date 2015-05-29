package lasermod.helper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import lasermod.ModItems;
import lasermod.api.ILaser;
import lasermod.api.LaserCollisionBoxes;
import lasermod.api.LaserInGame;
import lasermod.api.LaserRegistry;
import lasermod.api.LaserToRender;
import lasermod.util.BlockActionPos;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author ProPercivalalb
 **/
public class ScreenRenderHandler {

	@SubscribeEvent
	public void onPreRenderGameOverlay(RenderGameOverlayEvent.Pre event) {
		int mouseX = event.mouseX;
		int mouseY = event.mouseY;
		ElementType type = event.type;
		EntityPlayer player = ClientHelper.getPlayer();
		World world = player.worldObj;
		ItemStack stack = ClientHelper.mc.thePlayer.getHeldItem();
		if(event.type == RenderGameOverlayEvent.ElementType.HELMET && stack != null && stack.getItem() == ModItems.handheldSensor) {
			double lookDistance = ClientHelper.mc.playerController.getBlockReachDistance();
			
	        MovingObjectPosition mop = ClientHelper.mc.renderViewEntity.rayTrace(lookDistance, event.partialTicks);
		   
			
	        double d1 = lookDistance;
	        Vec3 posVec = ClientHelper.mc.renderViewEntity.getPosition(event.partialTicks);

            if (ClientHelper.mc.playerController.extendedReach()) {
            	lookDistance = 6.0D;
                d1 = 6.0D;
            }
            else {
                if (lookDistance > 3.0D) {
                    d1 = 3.0D;
                }

                lookDistance = d1;
            }

			if(mop != null)
				d1 = mop.hitVec.distanceTo(posVec);
			
	        Vec3 lookVec = ClientHelper.mc.renderViewEntity.getLook(event.partialTicks);
	        Vec3 combinedVec = posVec.addVector(lookVec.xCoord * lookDistance, lookVec.yCoord * lookDistance, lookVec.zCoord * lookDistance);
			
	        Vec3 finalVec = null;
            float f1 = 1.0F;
           
            double d2 = d1;

            int lookingAtIndex = -1;
            for (int i = 0; i < LaserCollisionBoxes.lasers2.size(); ++i) {
            	LaserToRender ltr = LaserCollisionBoxes.lasers2.get(i);
            	AxisAlignedBB axisalignedbb = ltr.collision.getOffsetBoundingBox(ltr.blockX, ltr.blockY, ltr.blockZ).getOffsetBoundingBox(-ltr.renderX, -ltr.renderY, -ltr.renderZ);
            	
                MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(posVec, combinedVec);
                if (axisalignedbb.isVecInside(posVec)) {
                    if (0.0D < d2 || d2 == 0.0D) {
                    	
                    	lookingAtIndex = i;
                        finalVec = movingobjectposition == null ? posVec : movingobjectposition.hitVec;
                        d2 = 0.0D;
                    }
                }
                else if (movingobjectposition != null) {
                    double d3 = posVec.distanceTo(movingobjectposition.hitVec);

                    if (d3 < d2 || d2 == 0.0D) {
                    	lookingAtIndex = i;
                        finalVec = movingobjectposition.hitVec;
                        d2 = d3;
                    }
                }
           
            }
	        
            if(lookingAtIndex != -1) {
            	LaserToRender ltr = LaserCollisionBoxes.lasers2.get(lookingAtIndex);
            	LaserInGame laser = ltr.laser;
            	Color colour = new Color(laser.red, laser.green, laser.blue);
            	List<String> list = new ArrayList<String>();
            	list.add(EnumChatFormatting.AQUA + "Laser");
            	
            	list.add("   Colour: " + laser.red + ", " + laser.green + ", " + laser.blue + " - rgb");
            	list.add("Orgin: " + ltr.blockX + ", " + ltr.blockY + ", " + ltr.blockZ);
            	list.add("Direction traveling: " + ForgeDirection.getOrientation(laser.getSide()).getOpposite().name());
            	for(ILaser laserType : laser.getLaserType()) {
            		list.add(LaserRegistry.getIdFromLaser(laserType));
            	}
            	drawHoveringText(list, 0, 25, 1000, 200, ClientHelper.mc.fontRenderer);
                this.drawRect(12, 24, 12 + 9, 24 + 9, colour.getRGB());
            	
            }
            else if(mop != null && mop.typeOfHit == MovingObjectType.BLOCK) {
            	List<String> list = new ArrayList<String>();
            	
            	BlockActionPos action = new BlockActionPos(world, mop.blockX, mop.blockY, mop.blockZ);
            	if(action.isLaserProvider(-1)) {
            		list.add("PROVIDER");
            	}
            	if(action.isLaserReciver(-1)) {
            		list.add("RECIVER");
            	}
            	drawHoveringText(list, 0, 25, 1000, 200, ClientHelper.mc.fontRenderer);
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
	
	 protected void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6)
	    {
	        float f = (float)(par5 >> 24 & 255) / 255.0F;
	        float f1 = (float)(par5 >> 16 & 255) / 255.0F;
	        float f2 = (float)(par5 >> 8 & 255) / 255.0F;
	        float f3 = (float)(par5 & 255) / 255.0F;
	        float f4 = (float)(par6 >> 24 & 255) / 255.0F;
	        float f5 = (float)(par6 >> 16 & 255) / 255.0F;
	        float f6 = (float)(par6 >> 8 & 255) / 255.0F;
	        float f7 = (float)(par6 & 255) / 255.0F;
	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glDisable(GL11.GL_ALPHA_TEST);
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        GL11.glShadeModel(GL11.GL_SMOOTH);
	        Tessellator tessellator = Tessellator.instance;
	        tessellator.startDrawingQuads();
	        tessellator.setColorRGBA_F(f1, f2, f3, f);
	        tessellator.addVertex((double)par3, (double)par2, (double)300.0F);
	        tessellator.addVertex((double)par1, (double)par2, (double)300.0F);
	        tessellator.setColorRGBA_F(f5, f6, f7, f4);
	        tessellator.addVertex((double)par1, (double)par4, (double)300.0F);
	        tessellator.addVertex((double)par3, (double)par4, (double)300.0F);
	        tessellator.draw();
	        GL11.glShadeModel(GL11.GL_FLAT);
	        GL11.glDisable(GL11.GL_BLEND);
	        GL11.glEnable(GL11.GL_ALPHA_TEST);
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
	    }
	
	 
	 public void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6)
	    {
	        float f = 0.00390625F;
	        float f1 = 0.00390625F;
	        Tessellator tessellator = Tessellator.instance;
	        tessellator.startDrawingQuads();
	        tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + par6), (double)100, (double)((float)(par3 + 0) * f), (double)((float)(par4 + par6) * f1));
	        tessellator.addVertexWithUV((double)(par1 + par5), (double)(par2 + par6), (double)100, (double)((float)(par3 + par5) * f), (double)((float)(par4 + par6) * f1));
	        tessellator.addVertexWithUV((double)(par1 + par5), (double)(par2 + 0), (double)100, (double)((float)(par3 + par5) * f), (double)((float)(par4 + 0) * f1));
	        tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), (double)100, (double)((float)(par3 + 0) * f), (double)((float)(par4 + 0) * f1));
	        tessellator.draw();
	    }
	 
    
	    public static void drawRect(int par0, int par1, int par2, int par3, int par4)
	    {
	        int j1;

	        if (par0 < par2)
	        {
	            j1 = par0;
	            par0 = par2;
	            par2 = j1;
	        }

	        if (par1 < par3)
	        {
	            j1 = par1;
	            par1 = par3;
	            par3 = j1;
	        }

	        float f3 = (float)(par4 >> 24 & 255) / 255.0F;
	        float f = (float)(par4 >> 16 & 255) / 255.0F;
	        float f1 = (float)(par4 >> 8 & 255) / 255.0F;
	        float f2 = (float)(par4 & 255) / 255.0F;
	        Tessellator tessellator = Tessellator.instance;
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
	        GL11.glColor4f(f, f1, f2, f3);
	        tessellator.startDrawingQuads();
	        tessellator.addVertex((double)par0, (double)par3, 0.0D);
	        tessellator.addVertex((double)par2, (double)par3, 0.0D);
	        tessellator.addVertex((double)par2, (double)par1, 0.0D);
	        tessellator.addVertex((double)par0, (double)par1, 0.0D);
	        tessellator.draw();
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
	        GL11.glDisable(GL11.GL_BLEND);
	    }
}
