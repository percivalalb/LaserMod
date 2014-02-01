package lasermod.client.render;

import lasermod.api.ILaserProvider;
import lasermod.api.LaserWhitelist;
import lasermod.util.LaserUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
@SideOnly(Side.CLIENT)
public class LaserRenderer {

	public static double LASER_SIZE = 0.4D; //The distance across the entire beam
	
	public static AxisAlignedBB getLaserOutline(ILaserProvider laserProvider, int meta, double renderX, double renderY, double renderZ) {
		int orientation = LaserUtil.getOrientation(meta);
		double offsetMin = 0.5D - LASER_SIZE / 2;
		double offsetMax = 0.5D + LASER_SIZE / 2;
		AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(renderX + offsetMin, renderY + offsetMin, renderZ + offsetMin, renderX + offsetMax, renderY + offsetMax, renderZ + offsetMax);
		
		double[] extra = new double[ForgeDirection.VALID_DIRECTIONS.length];
		
		for(int distance = 1; distance <= LaserUtil.LASER_REACH; distance++) {
			int xTemp = laserProvider.getX() + ForgeDirection.VALID_DIRECTIONS[orientation].offsetX * distance;
			int yTemp = laserProvider.getY() + ForgeDirection.VALID_DIRECTIONS[orientation].offsetY * distance;
			int zTemp = laserProvider.getZ() + ForgeDirection.VALID_DIRECTIONS[orientation].offsetZ * distance;
			
			//Check whether the coordinates are in range
			if(xTemp < -30000000 || zTemp < -30000000 || xTemp >= 30000000 || zTemp >= 30000000 || yTemp < 0 || yTemp >= 256)
				break;
			
			Block block = laserProvider.getWorld().func_147439_a(xTemp, yTemp, zTemp);
			int blockMeta = laserProvider.getWorld().getBlockMetadata(xTemp, yTemp, zTemp);
			TileEntity tileEntity = laserProvider.getWorld().func_147438_o(xTemp, yTemp, zTemp);
			
			//Can't pass through the next block
			if(LaserWhitelist.canLaserPassThrought(block, blockMeta))
				extra[orientation] += 1;
			else {
				extra[orientation] += 1 - offsetMax + 0.01D;
				break;
			}
			
		}
		
        boundingBox.setBounds(boundingBox.minX - extra[4], boundingBox.minY - extra[0], boundingBox.minZ - extra[2], boundingBox.maxX + extra[5], boundingBox.maxY + extra[1], boundingBox.maxZ + extra[3]);
        
        return boundingBox;
	}
	
    public static void drawBoundingBox(AxisAlignedBB boundingBox) {
	    Tessellator tessellator = Tessellator.instance;
	    tessellator.startDrawingQuads();
	    tessellator.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
	    tessellator.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
	    tessellator.draw();
	}
}
