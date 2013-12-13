package lasermod.core.proxy;

import lasermod.ModBlocks;
import lasermod.client.render.block.TileEntityBasicLaserRenderer;
import lasermod.client.render.item.ItemReflectorRenderer;
import lasermod.tileentity.TileEntityBasicLaser;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.MinecraftForgeClient;

/**
 * @author ProPercivalalb
 */
public class ClientProxy extends CommonProxy {

	public static Minecraft mc = Minecraft.getMinecraft();
	
	@Override
	public void onPreLoad() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBasicLaser.class, new TileEntityBasicLaserRenderer());
		MinecraftForgeClient.registerItemRenderer(ModBlocks.reflector.blockID, new ItemReflectorRenderer());
	}
	
	@Override
	public void registerHandlers() {
		
	}
}
