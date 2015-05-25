package lasermod.forgemultipart;

import lasermod.tileentity.TileEntitySmallColourConverter;
import net.minecraft.tileentity.TileEntity;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import codechicken.multipart.minecraft.IPartMeta;
import codechicken.multipart.minecraft.PartMetaAccess;
import cpw.mods.fml.common.FMLLog;

public class PartLaserAccess extends PartMetaAccess {

	public PartLaserAccess(IPartMeta p) {
		super(p);
	}

	@Override
	public TileEntity getTileEntity(int i, int j, int k) {
		if (i == this.part.getPos().x && j == this.part.getPos().y && k == this.part.getPos().z) {
			TileEntity tileEntity = this.part.getWorld().getTileEntity(i, j, k);
			int colour = 14;
			if(tileEntity instanceof TileMultipart) {
	            TileMultipart tem = (TileMultipart)tileEntity;

	            for (TMultiPart t : tem.jPartList()) {
	                if(t instanceof SmallColourConverterPart)
	                	colour = ((SmallColourConverterPart)t).colour;
	            }
	        }
	        return new TileEntitySmallColourConverter().setColour(colour);
		}
	    return this.part.getWorld().getTileEntity(i, j, k);
	}
}
