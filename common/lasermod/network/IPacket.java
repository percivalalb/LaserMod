package lasermod.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lasermod.lib.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import cpw.mods.fml.client.FMLClientHandler;

/**
 * @author ProPercivalalb
 */
public abstract class IPacket {
	
	public abstract void read(DataInputStream data) throws IOException;
	public abstract void write(DataOutputStream data) throws IOException;
	
	public abstract void execute(EntityPlayer player);
	
	public S3FPacketCustomPayload getServerToClientPacket() {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    	DataOutputStream dos = new DataOutputStream(bos);
	    	dos.writeByte(PacketType.getIdFromClass(this.getClass()));
			this.write(dos);
	    	return new S3FPacketCustomPayload(Reference.CHANNEL_NAME, bos.toByteArray());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public C17PacketCustomPayload getClientToServerPacket() {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    	DataOutputStream dos = new DataOutputStream(bos);
	    	dos.writeByte(PacketType.getIdFromClass(this.getClass()));
	    	dos.writeUTF(FMLClientHandler.instance().getClientPlayerEntity().getCommandSenderName());
			this.write(dos);
	    	return new C17PacketCustomPayload(Reference.CHANNEL_NAME, bos.toByteArray());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
