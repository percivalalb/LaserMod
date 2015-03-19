package lasermod.client.gui;

import lasermod.lib.Reference;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GuiAdvancedLaser extends GuiScreen 
{

	private int guiWidth = 148;
	private int guiHeight = 50;
	
	public static final int GUI_ID = 460;
	public GuiAdvancedLaser() {}
	
	@Override
	public void drawScreen(int x, int y, float ticks)
	{
		int guiX = (this.width - guiWidth) / 2;
		int guiY = (this.height - guiHeight) / 2;
		GL11.glColor4f(1, 1, 1, 1);
		this.drawDefaultBackground();
		this.mc.renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/testGui.png"));
		this.drawTexturedModalRect(guiX, guiY, 0, 0, guiWidth, guiHeight);
		
		super.drawScreen(x, y, ticks);
	}
}
