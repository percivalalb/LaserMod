package lasermod.api;

import java.util.Objects;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class LaserToRender {

	public LaserInGame laser;
	public AxisAlignedBB collision;
	public double renderX;
	public double renderY;
	public double renderZ;
	public BlockPos pos;
	public EnumFacing dir;
	public float alpha;
	public boolean tooltip;
	
	public LaserToRender(LaserInGame laser, AxisAlignedBB collision, double renderX, double renderY, double renderZ, BlockPos pos, EnumFacing dir, float alpha, boolean tooltip) {
		this.laser = laser;
		this.collision = collision;
		this.renderX = renderX;
		this.renderY = renderY;
		this.renderZ = renderZ;
		this.pos = pos;

		this.dir = dir;
		this.alpha = alpha;
		this.tooltip = tooltip;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
        if(obj == null) return false;
        if(this.getClass() != obj.getClass()) return false;
        LaserToRender laser = (LaserToRender)obj;
        return this.pos.equals(laser.pos) 
        		&& this.dir == laser.dir;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.pos, this.dir);
	}
}
