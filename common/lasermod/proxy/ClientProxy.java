package lasermod.proxy;

import lasermod.client.render.block.TileEntityBasicLaserRenderer;
import lasermod.tileentity.TileEntityBasicLaser;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 * @author ProPercivalalb
 */
public class ClientProxy extends CommonProxy {
	
	@Override
	public void onPreLoad() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBasicLaser.class, new TileEntityBasicLaserRenderer());
	}
	
	@Override
	public void registerHandlers() {
		
	}
	
	@Override
	public int armorRender(String str) {
		return RenderingRegistry.addNewArmourRendererPrefix(str);
	}
}
