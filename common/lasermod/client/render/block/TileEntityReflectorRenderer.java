package lasermod.client.render.block;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import lasermod.client.model.block.ModelReflector;
import lasermod.core.helper.LogHelper;
import lasermod.lib.ResourceReference;
import lasermod.tileentity.TileEntityReflector;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

/**
 * @author ProPercivalalb
 */
public class TileEntityReflectorRenderer extends TileEntitySpecialRenderer {

	private ModelReflector modelReflector = new ModelReflector();
	
    public void renderReflector(TileEntityReflector reflector, double x, double y, double z, float tick) {
    	this.bindTexture(ResourceReference.REFLECTOR_MODEL);
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5F, y + 1.5F, z + 0.5F);
        GL11.glScalef(1.0F, -1F, -1F);
        modelReflector.renderModel();
        GL11.glPopMatrix();

    }
    
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
        renderReflector((TileEntityReflector) tileEntity, x, y, z, tick);
    }
}
