package lasermod.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 * @author ProPercivalalb
 */
public class ClientProxy extends CommonProxy {
	
	@Override
	public void onPreLoad() {
		
	}
	
	@Override
	public void registerHandlers() {
		
	}
	
	@Override
	public int armorRender(String str) {
		return RenderingRegistry.addNewArmourRendererPrefix(str);
	}
}
