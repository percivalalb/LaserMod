package lasermod.core.proxy;

import lasermod.ModBlocks;
import lasermod.client.render.block.TileEntityBasicLaserRenderer;
import lasermod.client.render.block.TileEntityReflectorRenderer;
import lasermod.client.render.item.ItemReflectorRenderer;
import lasermod.packet.PacketReflectorUpdate;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.tileentity.TileEntityReflector;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

/**
 * @author ProPercivalalb
 */
public class ClientProxy extends CommonProxy {

	public static Minecraft mc = Minecraft.getMinecraft();
	
	@Override
	public void onPreLoad() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBasicLaser.class, new TileEntityBasicLaserRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityReflector.class, new TileEntityReflectorRenderer());
		MinecraftForgeClient.registerItemRenderer(ModBlocks.reflector.blockID, new ItemReflectorRenderer());
	}
	
	@Override
	public void handleReflectorPacket(PacketReflectorUpdate packet) {
		World world = this.mc.theWorld;
		TileEntity tileEntity = world.getBlockTileEntity(packet.x, packet.y, packet.z);
		
		if(!(tileEntity instanceof TileEntityReflector)) 
			return;
		TileEntityReflector reflector = (TileEntityReflector)tileEntity;
		reflector.openSides = packet.openSides;
		reflector.lasers = packet.lasers;
	}
	
	@Override
	public void registerHandlers() {
		
	}
}
