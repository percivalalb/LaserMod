package lasermod.core.proxy;

import lasermod.client.render.world.TileEntityBasicLaserRenderer;
import lasermod.tileentity.TileEntityBasicLaser;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.Minecraft;

/**
 * @author ProPercivalalb
 */
public class ClientProxy extends CommonProxy {

	public static Minecraft mc = Minecraft.getMinecraft();
	
	@Override
	public void onPreLoad() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBasicLaser.class, new TileEntityBasicLaserRenderer());
	}
	
	@Override
	public void registerHandlers() {
		
	}
}
