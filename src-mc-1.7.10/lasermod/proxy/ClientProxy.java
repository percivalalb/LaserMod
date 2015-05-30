package lasermod.proxy;

import lasermod.ModBlocks;
import lasermod.client.render.block.TileEntityAdvancedLaserRenderer;
import lasermod.client.render.block.TileEntityBasicLaserRenderer;
import lasermod.client.render.block.TileEntityColourConverterRenderer;
import lasermod.client.render.block.TileEntityLuminousLampRenderer;
import lasermod.client.render.block.TileEntityReflectorRenderer;
import lasermod.client.render.block.TileEntitySmallColourConverterRenderer;
import lasermod.client.render.item.ItemReflectorRenderer;
import lasermod.helper.ScreenRenderHandler;
import lasermod.helper.WorldOverlayHandler;
import lasermod.tileentity.TileEntityAdvancedLaser;
import lasermod.tileentity.TileEntityBasicLaser;
import lasermod.tileentity.TileEntityColourConverter;
import lasermod.tileentity.TileEntityLuminousLamp;
import lasermod.tileentity.TileEntityReflector;
import lasermod.tileentity.TileEntitySmallColourConverter;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 * @author ProPercivalalb
 */
public class ClientProxy extends CommonProxy {
	
	@Override
	public void onPreLoad() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBasicLaser.class, new TileEntityBasicLaserRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAdvancedLaser.class, new TileEntityAdvancedLaserRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityColourConverter.class, new TileEntityColourConverterRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityReflector.class, new TileEntityReflectorRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLuminousLamp.class, new TileEntityLuminousLampRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySmallColourConverter.class, new TileEntitySmallColourConverterRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.reflector), new ItemReflectorRenderer());
	}
	

	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx));
	}
	
	@Override
	public void registerHandlers() {
		MinecraftForge.EVENT_BUS.register(new ScreenRenderHandler());
		MinecraftForge.EVENT_BUS.register(new WorldOverlayHandler());
	}
	
	@Override
	public int armorRender(String str) {
		return RenderingRegistry.addNewArmourRendererPrefix(str);
	}
}
