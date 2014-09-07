package lasermod.client.render.block;

import org.lwjgl.opengl.GL11;

import lasermod.client.model.block.ModelReflector;
import lasermod.helper.ClientHelper;
import lasermod.lib.ResourceReference;
import lasermod.proxy.CommonProxy;
import lasermod.tileentity.TileEntityReflector;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockReflectorRenderer implements ISimpleBlockRenderingHandler {

	private ModelReflector modelReflector = new ModelReflector();
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		renderer.setOverrideBlockTexture(Blocks.anvil.getIcon(1, 1));
		renderer.setRenderBounds(0.9D, 0.2D, 0.0D, 1.0D, 0.6D, 1.0D);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.clearOverrideBlockTexture();
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return CommonProxy.REFLECTOR_RENDER_ID;
	}

}
