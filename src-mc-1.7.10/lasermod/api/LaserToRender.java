package lasermod.api;

import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class LaserToRender {

	public LaserInGame laser;
	public AxisAlignedBB collision;
	public double renderX;
	public double renderY;
	public double renderZ;
	public int blockX;
	public int blockY;
	public int blockZ;
	public ForgeDirection dir;
	public float alpha;
	public boolean tooltip;
	
	public LaserToRender(LaserInGame laser, AxisAlignedBB collision, double renderX, double renderY, double renderZ, int blockX, int blockY, int blockZ, ForgeDirection dir, float alpha, boolean tooltip) {
		this.laser = laser;
		this.collision = collision;
		this.renderX = renderX;
		this.renderY = renderY;
		this.renderZ = renderZ;
		this.blockX = blockX;
		this.blockY = blockY;
		this.blockZ = blockZ;
		this.dir = dir;
		this.alpha = alpha;
		this.tooltip = tooltip;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof LaserToRender) {
			LaserToRender other = (LaserToRender)obj;
			return this.blockX == other.blockX && this.blockY == other.blockY && this.blockZ == other.blockZ && this.dir == other.dir;
		}
		return false;
	}
}
