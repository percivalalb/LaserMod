package codechicken.multipart.minecraft;

import codechicken.lib.data.MCDataInput;
import codechicken.lib.data.MCDataOutput;
import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.TMultiPart;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class McSidedMetaPart extends TMultiPart {

	public int meta;

	public void save(NBTTagCompound tag) {}
	public void load(NBTTagCompound tag) {}
	public void writeDesc(MCDataOutput packet) {}
	public void readDesc(MCDataInput packet) {}
	public Cuboid6 getBounds() { return null; }
	public Cuboid6 getRenderBounds() { return null; }
	public boolean renderStatic(Vector3 pos, int pass) { return false; }
	public void onWorldJoin() {}
	public void renderDynamic(Vector3 pos, float frame, int pass) {}
	public int x() { return 0; }
	public int y() { return 0; }
	public int z() { return 0; }
	public World world() { return null; }
 	public Iterable<Cuboid6> getCollisionBoxes() { return null; }
	public Iterable<IndexedCuboid6> getSubParts() { return null; }
	public void update() {}
	public void sendDescUpdate() {}
	public boolean doesTick() { return false; }
	public boolean activate(EntityPlayer player, MovingObjectPosition part, ItemStack item) { return false; }
	public Block getBlock() { return null; }
	public String getType() { return null; }
	public int sideForMeta(int meta) { return 0; }
}
