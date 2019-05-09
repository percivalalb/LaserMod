package lasermod.api;

import java.util.ArrayList;

import io.netty.buffer.ByteBuf;
import lasermod.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class LaserInGame {

	private double strength = 100D;
	private ArrayList<LaserType> laserType = new ArrayList<LaserType>();
	private EnumFacing dir = EnumFacing.DOWN;
	public int red = 255;
	public int green = 0;
	public int blue = 0;
	
	public LaserInGame() {}
	public LaserInGame(NBTTagCompound tag) { this.readFromNBT(tag); }
	public LaserInGame(LaserType laser) { this.laserType.add(laser);}
	public LaserInGame(ArrayList<LaserType> lasers) { this.laserType.addAll(lasers); }
	
	public LaserInGame setStrength(double strength) {
		if(strength < 0.0D) strength = 0.0D;
		this.strength = strength; 
		return this;
	}
	
	public LaserInGame setDirection(EnumFacing dir) {
		this.dir = dir;
		return this;
	}
	
	public LaserInGame setLaserType(LaserType laser) {
		this.laserType.clear();
		this.laserType.add(laser);
		return this;
	}
	
	public LaserInGame addLaserType(LaserType laser) {
		this.laserType.add(laser);
		return this;
	}
	
	public LaserInGame setLaserType(ResourceLocation laser) {
		return setLaserType(LaserModAPI.LASER_TYPES.getValue(laser));
	}
	
	public LaserInGame addLaserType(ResourceLocation laser) {
		return addLaserType(LaserModAPI.LASER_TYPES.getValue(laser));
	}
	
	public double getStrength() {
		return this.strength;
	}
	
	public ArrayList<LaserType> getLaserType() {
		return this.laserType;
	}
	
	@SideOnly(Side.CLIENT)
	public float shouldRenderLaser(EntityPlayer player) {
		for(LaserType laser : this.laserType) {
			if(!laser.shouldRenderLaser(player, this.dir))
				return player.inventory.armorItemInSlot(3).getItem() == ModItems.LASER_SEEKING_GOGGLES ? 0.1F : 0.0F;
		}
		return 0.4F;
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag.setDouble("strength", this.strength);
		tag.setInteger("side", this.dir.ordinal());
		tag.setInteger("red", this.red);
		tag.setInteger("green", this.green);
		tag.setInteger("blue", this.blue);
		
		NBTTagList list = new NBTTagList();
		for(LaserType laser : this.laserType)
			list.appendTag(new NBTTagString(LaserModAPI.LASER_TYPES.getKey(laser).toString()));
		
		tag.setTag("laserTypes", list);
		return tag;
	}
	
	public void writeToPacket(ByteBuf buffer) {
		buffer.writeDouble(this.strength);
		buffer.writeInt(this.dir.ordinal());
		buffer.writeInt(this.red);
		buffer.writeInt(this.green);
		buffer.writeInt(this.blue);
		
		buffer.writeInt(this.laserCount());
		for(LaserType laser : this.laserType)
			ByteBufUtils.writeUTF8String(buffer, LaserModAPI.LASER_TYPES.getKey(laser).toString());
	}
	
	public static LaserInGame readFromNBT(NBTTagCompound tag) {
		LaserInGame laser = new LaserInGame();
		
		laser.strength = tag.getDouble("strength");
		laser.dir = EnumFacing.byIndex(tag.getInteger("side"));
		laser.red = tag.getInteger("red");
		laser.green = tag.getInteger("green");
		laser.blue = tag.getInteger("blue");
		
		NBTTagList list = (NBTTagList)tag.getTag("laserTypes");
		for(int i = 0; i < list.tagCount(); ++i)
			laser.addLaserType(new ResourceLocation(list.getStringTagAt(i)));
		
		return laser;
	}
	
	public static LaserInGame readFromPacket(ByteBuf buffer) {
		LaserInGame laser = new LaserInGame();
		
		laser.strength = buffer.readDouble();
		laser.dir = EnumFacing.byIndex(buffer.readInt());
		laser.red = buffer.readInt();
		laser.green = buffer.readInt();
		laser.blue = buffer.readInt();
		
		int count = buffer.readInt();
		for(int i = 0; i < count; ++i)
			laser.addLaserType(new ResourceLocation(ByteBufUtils.readUTF8String(buffer)));
		
		return laser;
	}
	
	@SuppressWarnings("unchecked")
	public LaserInGame copy() {
		LaserInGame laser = new LaserInGame((ArrayList<LaserType>)this.laserType.clone());
		laser.setDirection(this.dir);
		laser.setStrength(this.getStrength());
		laser.red = this.red;
		laser.green = this.green;
		laser.blue = this.blue;
		return laser;
	}
	
	public EnumFacing getDirection() {
		return this.dir;
	}

	public int laserCount() {
		return this.laserType.size();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof LaserInGame) {
			LaserInGame laser = (LaserInGame)obj;
			boolean laserTypeEqual = true;
			if(laser.laserCount() != this.laserCount()) laserTypeEqual = false;
			else
				for(int i = 0; i < this.laserCount(); i++) {
					if(!laser.laserType.contains(this.laserType.get(i))) laserTypeEqual = false;
				}
			return this.red == laser.red && this.green == laser.green && this.blue == laser.blue && laserTypeEqual;
		}
		return false;
		
	}
}
