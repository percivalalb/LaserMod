package lasermod.packet;

import java.util.Hashtable;
import java.util.Map;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

/**
 * @author ProPercivalalb
 **/
public class PacketHandler implements IPacketHandler {

	private Map<String, Class> customPackages = new Hashtable<String, Class>();
	
	public PacketHandler() {
		customPackages.put("reflectorUpdate", PacketReflectorUpdate.class);
	}
	
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		try {
			((PacketBase)customPackages.get(packet.channel).newInstance()).readPacket(packet, player);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
