package lasermod.core.proxy;

import net.minecraft.client.Minecraft;

/**
 * @author ProPercivalalb
 */
public class ClientProxy extends CommonProxy {

	public static Minecraft mc = Minecraft.getMinecraft();
	
	@Override
	public void onPreLoad() {
		//RenderingRegistry.
	}
	
	@Override
	public void registerHandlers() {
		
	}
}
