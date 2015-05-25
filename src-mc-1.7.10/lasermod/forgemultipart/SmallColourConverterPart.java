package lasermod.forgemultipart; 

import java.util.ArrayList;
import java.util.Arrays;

import lasermod.LaserMod;
import lasermod.ModBlocks;
import lasermod.api.ILaserReceiver;
import lasermod.api.LaserInGame;
import lasermod.client.render.block.TileEntitySmallColourConverterRenderer;
import lasermod.network.packet.PacketSmallColourConverter;
import lasermod.tileentity.TileEntitySmallColourConverter;
import lasermod.util.LaserUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import codechicken.lib.data.MCDataInput;
import codechicken.lib.data.MCDataOutput;
import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import codechicken.multipart.minecraft.McMetaPart;
import codechicken.multipart.minecraft.McSidedMetaPart;
import codechicken.multipart.minecraft.PartMetaAccess;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SmallColourConverterPart extends McSidedMetaPart implements ILaserReceiver {

	public int colour;
	
	public SmallColourConverterPart() {
		
	}
	
	public SmallColourConverterPart(byte meta, int colour) {
		this.meta = meta;
		this.colour = colour;
	}
	
	@Override
    public void save(NBTTagCompound tag) {
        super.save(tag);
        tag.setInteger("colour", this.colour);
    }
    
    @Override
    public void load(NBTTagCompound tag) {
        super.load(tag);
        this.colour = tag.getInteger("colour");
    }
    
    @Override
    public void writeDesc(MCDataOutput packet) {
      	super.writeDesc(packet);
      	packet.writeInt(this.colour);
    }
    
    @Override
    public void readDesc(MCDataInput packet) {
        super.readDesc(packet);
        this.colour = packet.readInt();
    }
	
	@Override
	public Cuboid6 getBounds() {
		

        if(meta == ForgeDirection.DOWN.ordinal())
        	 return new Cuboid6(0.2F, 0.8F, 0.2F, 0.8F, 9.0F, 0.8F);
        else if(meta == ForgeDirection.UP.ordinal())
        	return new Cuboid6(0.2F, 0.0F, 0.2F, 0.8F, 0.2F, 0.8F);
    	else if(meta == ForgeDirection.SOUTH.ordinal())
    		return new Cuboid6(0.2F, 0.2F, 0.0F, 0.8F, 0.8F, 0.2F);
       	else if(meta == ForgeDirection.NORTH.ordinal())
       		return new Cuboid6(0.2F, 0.2F, 0.8F, 0.8F, 0.8F, 1.0F);
    	else if(meta == ForgeDirection.EAST.ordinal())
    		return new Cuboid6(0.0F, 0.2F, 0.2F, 0.2F, 0.8F, 0.8F);
       	else if(meta == ForgeDirection.WEST.ordinal())
       		return new Cuboid6(0.8F, 0.2F, 0.2F, 1.0F, 0.8F, 0.8F);
       	else
       		return new Cuboid6(0.4F, 0.4F, 0.4F, 0.6F, 0.6F, 0.6F);
	}
	
	@Override
    public boolean renderStatic(Vector3 pos, int pass) {
        if(pass == 0) {
            new RenderBlocks(new PartLaserAccess(this)).renderBlockByRenderType(getBlock(), x(), y(), z());
            return true;
        }
        return false;
    }
	
	@SideOnly(Side.CLIENT)
	public TileEntitySmallColourConverterRenderer render;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void onWorldJoin() {
		this.render = new TileEntitySmallColourConverterRenderer();

		this.render.func_147497_a(TileEntityRendererDispatcher.instance);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderDynamic(Vector3 pos, float frame, int pass) {
        if(pass == 0) {
			TileEntitySmallColourConverter tileEntity = new TileEntitySmallColourConverter().setColour(colour);
			tileEntity.xCoord = x();
			tileEntity.yCoord = y();
			tileEntity.zCoord = z();
			tileEntity.blockMetadata = meta;
			tileEntity.laser = new LaserInGame();
			tileEntity.laser.setSide(Facing.oppositeSide[meta]);
			tileEntity.laser.red = (int)(LaserUtil.LASER_COLOUR_TABLE[this.colour][0] * 255);
			tileEntity.laser.green = (int)(LaserUtil.LASER_COLOUR_TABLE[this.colour][1] * 255);
			tileEntity.laser.blue = (int)(LaserUtil.LASER_COLOUR_TABLE[this.colour][2] * 255);
			tileEntity.setWorldObj(this.getWorld());
			this.render.renderTileEntityAt(tileEntity, pos.x, pos.y, pos.z, 0);
        }
	}

	@Override
    public Iterable<Cuboid6> getCollisionBoxes() {
        return Arrays.asList(getBounds());
    }
    
    public Iterable<Cuboid6> getOcclusionBoxes() {
    	 return Arrays.asList(getBounds());
    }
    
    @Override
    public Iterable<IndexedCuboid6> getSubParts() {
        Cuboid6 cuboid = getBounds();
        ArrayList<IndexedCuboid6> indexed = new ArrayList<IndexedCuboid6>();

        indexed.add(new IndexedCuboid6(0, cuboid));
        return indexed;
    }
    
    @Override
    public void update() {
    	
    }
    
    public boolean doesTick() {
    	return true;
    }
	
    
    
	@Override
    public boolean activate(EntityPlayer player, MovingObjectPosition part, ItemStack item) {
		if(!player.worldObj.isRemote && item != null) {
			if(item.getItem() == Items.dye) {
				
				int colour = 15 - item.getItemDamage();
				if(colour > 15)
					colour = 15;
				else if(colour < 0)
					colour = 0;
				
				if(colour == this.colour)
					return true;
				
				this.colour = colour;
				
				if(!player.capabilities.isCreativeMode)
					item.stackSize--;
				if(item.stackSize <= 0)
					player.setCurrentItemOrArmor(0, (ItemStack)null);
				
				this.sendDescUpdate();
				
				//LaserMod.NETWORK_MANAGER.sendPacketToAllAround(new PacketSmallColourConverter(colourConverter), world.provider.dimensionId, x + 0.5D, y + 0.5D, z + 0.5D, 512);
				
				return true;
			}
		}
        
        return true;
    }

	@Override
	public Block getBlock() {
		return ModBlocks.smallColourConverter;
	}

	@Override
	public String getType() {
		return "lasermod:smallcolorconverter";
	}

	@Override
	public int sideForMeta(int meta) {
		return Facing.oppositeSide[meta];
	}

	@Override
	public int getX() {
		return this.x();
	}

	@Override
	public int getY() {
		return this.y();
	}

	@Override
	public int getZ() {
		return this.z();
	}

	@Override
	public boolean canPassOnSide(World world, int orginX, int orginY, int orginZ, int side, LaserInGame laserInGame) {
		return false;
	}

	@Override
	public void passLaser(World world, int orginX, int orginY, int orginZ, int side, LaserInGame laserInGame) {
		
	}

	@Override
	public void removeLasersFromSide(World world, int orginX, int orginY, int orginZ, int side) {
		
	}

}
