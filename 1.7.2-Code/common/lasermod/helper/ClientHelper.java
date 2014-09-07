package lasermod.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author ProPercivalalb
 */
public class ClientHelper {

	public static final Minecraft mc = Minecraft.getMinecraft();
	
	public static EntityPlayer getPlayer() {
		return mc.thePlayer;
	}
}
