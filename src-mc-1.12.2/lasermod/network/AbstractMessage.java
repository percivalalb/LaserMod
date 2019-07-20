package lasermod.network;

import java.io.IOException;

import com.google.common.base.Throwables;

import io.netty.buffer.ByteBuf;
import lasermod.LaserMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/**
 * 
 * Creating an abstract message class similar to {@link Packet} allows us to take
 * advantage of Minecraft's PacketBuffer class, which has many useful methods.
 * 
 * Should the IMessageHandler be implemented as a GenericMessageHandler class,
 * every child message class will still have to have an empty implementation of
 * the handler, just so it can be registered:
 * 
 * public static class Handler extends GenericMessageHandler<SomeMessage> {}
 * 
 * This is kind of ridiculous, so instead the message will also implement the handler,
 * despite the fact that a handler instance shouldn't have any of the class members or
 * methods that a message instance does (since a handler is not a message).
 * 
 * To combat that incongruity, the #onMessage method will be made final.
 * 
 * As a bonus, registration of this class can be made less verbose than normal, since the
 * same class is used for both the IMessage and the IMessageHandler.
 * 
 */
public abstract class AbstractMessage<T extends AbstractMessage<T> & IPacket<T>> implements IMessage, IMessageHandler <T, IMessage>
{
	/**
	 * If message is sent to the wrong side, an exception will be thrown during handling
	 * @return True if the message is allowed to be handled on the given side
	 */
	protected boolean isValidOnSide(Side side) {
		return true;
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		((T)this).decode(new PacketBuffer(buffer));
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		((T)this).encode(((T)this), new PacketBuffer(buffer));
	}
	
	@Override
	public final IMessage onMessage(final T msg, final MessageContext ctx) {
		if (!msg.isValidOnSide(ctx.side))
			throw new RuntimeException("Invalid side " + ctx.side.name() + " for " + msg.getClass().getSimpleName());
		
		IThreadListener thread = LaserMod.PROXY.getThreadFromContext(ctx);
		// pretty much copied straight from vanilla code, see {@link PacketThreadUtil#checkThreadAndEnqueue}
		thread.addScheduledTask(new Runnable() {
			public void run() {
				msg.handle(msg, LaserMod.PROXY.getPlayerEntity(ctx));
			}
		});
		
		return null;
	}
	
	/**
	 * Messages that can only be sent from the server to the client should use this class
	 */
	public static abstract class AbstractClientMessage<T extends AbstractMessage<T> & IPacket<T>> extends AbstractMessage<T> {
		@Override
		protected final boolean isValidOnSide(Side side) {
			return side.isClient();
		}
	}

	/**
	 * Messages that can only be sent from the client to the server should use this class
	 */
	public static abstract class AbstractServerMessage<T extends AbstractMessage<T> & IPacket<T>> extends AbstractMessage<T> {
		@Override
		protected final boolean isValidOnSide(Side side) {
			return side.isServer();
		}
	}
}