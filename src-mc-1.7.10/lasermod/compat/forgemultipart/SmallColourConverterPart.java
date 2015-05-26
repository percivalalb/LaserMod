package lasermod.compat.forgemultipart; 

import java.util.ArrayList;
import java.util.Arrays;

import lasermod.LaserMod;
import lasermod.ModBlocks;
import lasermod.api.ILaser;
import lasermod.api.ILaserProvider;
import lasermod.api.ILaserReceiver;
import lasermod.api.LaserInGame;
import lasermod.client.render.block.TileEntitySmallColourConverterRenderer;
import lasermod.network.packet.PacketSmallColourConverter;
import lasermod.tileentity.TileEntitySmallColourConverter;
import lasermod.util.BlockActionPos;
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

/**
 * @author ProPercivalalb
 */
public class SmallColourConverterPart extends McSidedMetaPart implements ILaserProvider, ILaserReceiver {

	public int colour;
	private LaserInGame laser;
	
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
      	packet.writeBoolean(this.laser != null);
      	if(this.laser != null)
      		packet.writeNBTTagCompound(this.laser.writeToNBT(new NBTTagCompound()));
    }
    
    @Override
    public void readDesc(MCDataInput packet) {
        super.readDesc(packet);
        this.colour = packet.readInt();
        if(packet.readBoolean())
        	this.laser = new LaserInGame(packet.readNBTTagCompound());
        else
        	this.laser = null;
    }
	
	@Override
	public Cuboid6 getBounds() {
        if(meta == ForgeDirection.DOWN.ordinal())
        	 return new Cuboid6(0.2F, 0.8F, 0.2F, 0.8F, 1.0F, 0.8F);
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
	
	public Cuboid6 infi = new Cuboid6(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
	
	@Override
	public Cuboid6 getRenderBounds() {
		return infi;
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
			tileEntity.multipart = true;
			tileEntity.laser = this.laser;
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
    	if(!this.world().isRemote) {
	    	if(this.laser != null && !LaserUtil.isValidSourceOfPowerOnSide(this, Facing.oppositeSide[meta])) {
				this.setLaser(null);
				this.sendDescUpdate();
			}
	    	
	    	BlockActionPos reciver = LaserUtil.getFirstBlock(this, this.meta);
			if(reciver != null && reciver.isLaserReciver(this.meta)) {
				LaserInGame laserInGame = this.getOutputLaser(this.meta);
	        	if(laserInGame == null) {
	        		reciver.getLaserReceiver(this.meta).removeLasersFromSide(this.world(), this.x(), this.y(), this.z(), Facing.oppositeSide[this.meta]);
	        	}
	        	else if(reciver.getLaserReceiver(this.meta).canPassOnSide(this.world(), this.x(), this.y(), this.z(), Facing.oppositeSide[this.meta], laserInGame)) {
	        		reciver.getLaserReceiver(this.meta).passLaser(this.world(), this.x(), this.y(), this.z(), Facing.oppositeSide[this.meta], laserInGame);
				}
			}
			else if(reciver != null) {
				LaserInGame laserInGame = this.getOutputLaser(this.meta);
				
				if(laserInGame != null) {
					for(ILaser laser : laserInGame.getLaserType()) {
						laser.actionOnBlock(reciver);
					}
				}
			}
    	}
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
		return side == Facing.oppositeSide[meta];
	}

	@Override
	public void passLaser(World world, int orginX, int orginY, int orginZ, int side, LaserInGame laserInGame) {
		if(this.getOutputLaser(side) == null) {
			this.setLaser(laserInGame);
			this.sendDescUpdate();
		}
	}

	@Override
	public void removeLasersFromSide(World world, int orginX, int orginY, int orginZ, int side) {
		if(side == Facing.oppositeSide[this.meta]) {
			boolean change = this.laser != null;
			this.setLaser(null);
			if(change) {
				this.sendDescUpdate();
			}
		}
	}

	@Override
	public LaserInGame getOutputLaser(int side) {
		if(this.laser != null) {
			this.laser.setSide(Facing.oppositeSide[side]);
			this.laser.red = (int)(LaserUtil.LASER_COLOUR_TABLE[this.colour][0] * 255);
			this.laser.green = (int)(LaserUtil.LASER_COLOUR_TABLE[this.colour][1] * 255);
			this.laser.blue = (int)(LaserUtil.LASER_COLOUR_TABLE[this.colour][2] * 255);
			return this.laser.copy();
		}
		return null;
	}

	@Override
	public int getDistance() {
		return 64;
	}

	@Override
	public boolean isSendingSignalFromSide(World world, int askerX, int askerY, int askerZ, int side) {
		return this.getOutputLaser(side) != null && side == this.meta;
	}
	
	public void setLaser(LaserInGame laser) {
		this.laser = laser;
	}

	@Override
	public boolean isForgeMultipart() {
		return true;
	}

}
