package lasermod.proxy;

import lasermod.client.gui.GuiAdvancedLaser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author ProPercivalalb
 */
public class CommonProxy implements IGuiHandler {
	
	public CommonProxy() {
		//FMLJavaModLoadingContext.get().getModEventBus().addListener(this::preInit);
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::postInit);
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) { 
		if (ID == GuiAdvancedLaser.GUI_ID) { return new GuiAdvancedLaser(); } 
		return null;
	}
	
	public void registerHandlers() {}

	public void preInit() {}
	public void init() {}
	public void postInit() {}
	
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return ctx.getServerHandler().player;
	}
	
	public EntityPlayer getPlayerEntity() {
		return null;
	}
	
	public IThreadListener getThreadFromContext(MessageContext ctx) {
		return ctx.getServerHandler().player.getServer();
	}
}
