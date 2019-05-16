package lasermod.client.gui;

import lasermod.lib.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value = Side.CLIENT)
public class GuiAdvancedLaser extends GuiScreen  {

	private int guiWidth = 148;
	private int guiHeight = 50;
	
	public static final int GUI_ID = 460;
	public GuiAdvancedLaser() {}
	
	@Override
	public void drawScreen(int x, int y, float ticks) {
		super.drawScreen(x, y, ticks);

		int guiX = (this.width - guiWidth) / 2;
		int guiY = (this.height - guiHeight) / 2;
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.drawDefaultBackground();
		this.mc.renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/testGui.png"));
		this.drawTexturedModalRect(guiX, guiY, 0, 0, guiWidth, guiHeight);
	}
}
