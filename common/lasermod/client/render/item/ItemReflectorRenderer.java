package lasermod.client.render.item;

import lasermod.lib.ResourceReference;
import net.minecraft.client.model.ModelChest;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class ItemReflectorRenderer implements IItemRenderer {

    private ModelChest modelChest;

    public ItemReflectorRenderer() {
        modelChest = new ModelChest();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

        switch (type) {
            case ENTITY: {
                renderReflector(0.5F, 0.5F, 0.5F);
                break;
            }
            case EQUIPPED: {
                renderReflector(1.0F, 1.0F, 1.0F);
                break;
            }
            case EQUIPPED_FIRST_PERSON: {
                renderReflector(1.0F, 1.0F, 1.0F);
                break;
            }
            case INVENTORY: {
                renderReflector(0.0F, 0.075F, 0.0F);
                break;
            }
            default:
                break;
        }
    }

    private void renderReflector(float x, float y, float z) {
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(ResourceReference.REFLECTOR_MODEL);
        GL11.glPushMatrix(); //start
        GL11.glTranslatef(x, y, z); //size
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glRotatef(-90, 0, 1, 0);
        modelChest.renderAll();
        GL11.glPopMatrix(); //end
    }
}
