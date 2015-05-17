package lasermod.api;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import lasermod.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class LaserInGame {

	private double strength = 100D;
	private ArrayList<ILaser> laserType = new ArrayList<ILaser>();
	private int side = -1;
	public int red = 255;
	public int green = 0;
	public int blue = 0;
	
	public LaserInGame() {}
	public LaserInGame(NBTTagCompound tag) { this.readFromNBT(tag); }
	public LaserInGame(ILaser laser) { this.laserType.add(laser);}
	public LaserInGame(ArrayList<ILaser> lasers) { this.laserType.addAll(lasers); }
	
	public LaserInGame setStrength(double strength) {
		if(strength < 0.0D)
			strength = 0.0D;
		this.strength = strength; 
		return this;
	}
	
	public LaserInGame setSide(int side) {
		this.side = side;
		return this;
	}
	
	public LaserInGame setLaserType(ILaser laser) {
		this.laserType.clear();
		this.laserType.add(laser);
		return this;
	}
	
	public LaserInGame addLaserType(ILaser laser) {
		this.laserType.add(laser);
		return this;
	}
	
	public LaserInGame setLaserType(String laser) {
		return setLaserType(LaserRegistry.getLaserFromId(laser));
	}
	
	public LaserInGame addLaserType(String laser) {
		return addLaserType(LaserRegistry.getLaserFromId(laser));
	}
	
	public double getStrength() {
		return this.strength;
	}
	
	public ArrayList<ILaser> getLaserType() {
		return this.laserType;
	}
	
	@SideOnly(Side.CLIENT)
	public float shouldRenderLaser(EntityPlayer player) {
		for(ILaser laser : this.laserType)
			if(!laser.shouldRenderLaser(player, this.side))
				return player.inventory.armorInventory[3] != null && player.inventory.armorInventory[3].getItem() == ModItems.laserSeekingGoogles ? 0.1F : 0.0F;
		return 0.4F;
	}
	
	public LaserInGame readFromNBT(NBTTagCompound tag) {
		this.strength = tag.getDouble("strength");
		this.side = tag.getInteger("side");
		this.red = tag.getInteger("red");
		this.green = tag.getInteger("green");
		this.blue = tag.getInteger("blue");
		
		NBTTagList list = (NBTTagList)tag.getTag("laserTypes");
		for(int i = 0; i < list.tagCount(); ++i)
			this.addLaserType(list.getStringTagAt(i));
		
		return this;
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag.setDouble("strength", this.strength);
		tag.setInteger("side", this.side);
		tag.setInteger("red", this.red);
		tag.setInteger("green", this.green);
		tag.setInteger("blue", this.blue);
		
		NBTTagList list = new NBTTagList();
	
		for(ILaser laser : this.laserType)
			list.appendTag(new NBTTagString(LaserRegistry.getIdFromLaser(laser)));
		
		tag.setTag("laserTypes", list);

		return tag;
	}
	
	public void writeToPacket(DataOutputStream data) throws IOException {
		data.writeDouble(this.strength);
		data.writeInt(this.side);
		data.writeInt(this.red);
		data.writeInt(this.green);
		data.writeInt(this.blue);
		
		data.writeInt(this.laserCount());
		for(ILaser laser : this.laserType)
			data.writeUTF(LaserRegistry.getIdFromLaser(laser));
	}
	
	public LaserInGame readFromPacket(DataInputStream data) throws IOException {
		this.strength = data.readDouble();
		this.side = data.readInt();
		this.red = data.readInt();
		this.green = data.readInt();
		this.blue = data.readInt();
		
		int count = data.readInt();
		for(int i = 0; i < count; ++i)
			this.addLaserType(data.readUTF());
		
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public LaserInGame copy() {
		LaserInGame laser = new LaserInGame((ArrayList<ILaser>)this.laserType.clone());
		laser.setSide(this.side);
		laser.setStrength(this.getStrength());
		laser.red = this.red;
		laser.green = this.green;
		laser.blue = this.blue;
		return laser;
	}
	
	public int getSide() {
		return this.side;
	}

	public int laserCount() {
		return this.laserType.size();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof LaserInGame) {
			LaserInGame laser = (LaserInGame)obj;
			return this.red == laser.red && this.green == laser.green && this.blue == laser.blue; //TODO && this.laserType.equals(laser.laserType);
		}
		return false;
		
	}
}
