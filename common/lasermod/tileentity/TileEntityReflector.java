package lasermod.tileentity;

import java.util.ArrayList;
import java.util.List;

import lasermod.LaserMod;
import lasermod.api.ILaser;
import lasermod.api.ILaserReciver;
import lasermod.api.LaserInGame;
import lasermod.api.LaserRegistry;
import lasermod.api.LaserWhitelist;
import lasermod.core.helper.LogHelper;
import lasermod.lib.Constants;
import lasermod.packet.PacketReflectorUpdate;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class TileEntityReflector extends TileEntity {
	public boolean[] openSides = new boolean[] { true, true, true, true, true, true };
	public ArrayList<LaserInGame> lasers = new ArrayList<LaserInGame>();
	public int[] reciverCords = new int[3];
	public int lagReduce = 0;

	public boolean isSideOpen(ForgeDirection direction) {
		return this.isSideOpen(direction.ordinal());
	}

	public boolean isSideOpen(int side) {
		return this.openSides[side];
	}

	public boolean addLaser(LaserInGame laserInGame) {
		if (!lasers.contains(laserInGame)) {
			lasers.add(laserInGame);
			return true;
		}
		return false;
	}

	public int openSides() {
		int count = 0;
		for (boolean bool : this.openSides)
			if (!bool)
				count++;
		return count;
	}

	public boolean containsInputSide(int side) {
		for (int i = 0; i < lasers.size(); ++i) {
			LaserInGame old = lasers.get(i);
			if (old.getSide() == side)
				return true;
		}
		return false;
	}

	public boolean removeAllLasersFromSide(int side) {
		boolean flag = false;
		for (int i = 0; i < lasers.size(); ++i) {
			LaserInGame old = lasers.get(i);
			if (old.getSide() == side) {
				lasers.remove(i);
				flag = true;
			}
		}
		if (flag)
			this.checkAllRecivers();

		return flag;
	}

	public void checkAllRecivers() {
		for (int i = 0; i < this.openSides.length; ++i) {
			if ((!this.openSides[i] && !(this.lasers.size() == 0)) || this.containsInputSide(i))
				continue;
			ILaserReciver reciver = getFirstReciver(i);

			if (reciver != null)
				reciver.removeLasersFromSide(worldObj, reciverCords[0], reciverCords[1], reciverCords[2], this.xCoord, this.yCoord, this.zCoord, Facing.oppositeSide[i]);
		}
	}

	public void checkAllReciversOnBroken() {
		for (int i = 0; i < this.openSides.length; ++i) {
			if (this.openSides[i] || this.containsInputSide(i))
				continue;
			ILaserReciver reciver = getFirstReciver(i);

			if (reciver != null) {
				LogHelper.logInfo("After Break: Side - " + Facing.oppositeSide[i]);
				reciver.removeLasersFromSide(worldObj, reciverCords[0], reciverCords[1], reciverCords[2], this.xCoord, this.yCoord, this.zCoord, Facing.oppositeSide[i]);
			}
		}
	}

	public AxisAlignedBB getFromLaserBox(double x, double y, double z, int side) {
		double laserSize = 0.4D;
		AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(x + 0.5D - laserSize / 2, y + 0.5D - laserSize / 2, z + 0.5D - laserSize / 2, x + 0.5D + laserSize / 2, y + 0.5D + laserSize / 2, z + 0.5D + laserSize / 2);

		double extraMinX = 0.0D;
		double extraMinY = 0.0D;
		double extraMinZ = 0.0D;

		double extraMaxX = 0.0D;
		double extraMaxY = 0.0D;
		double extraMaxZ = 0.0D;

		if (side == ForgeDirection.DOWN.ordinal()) {
			for (int i = this.yCoord - 1; this.yCoord - i >= 0; --i) {
				if (LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, i, this.zCoord)) {
					extraMinY++;
				} else {
					extraMinY += 1.0D - laserSize;
					break;
				}
			}
		} else if (side == ForgeDirection.UP.ordinal()) {
			for (int i = this.yCoord + 1; i < this.yCoord + Constants.LASER_REACH; ++i) {
				if (LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, i, this.zCoord)) {
					extraMaxY++;
				} else {
					extraMaxY += 1.0D - laserSize;
					break;
				}
			}
		} else if (side == ForgeDirection.NORTH.ordinal()) {
			for (int i = 1; i < Constants.LASER_REACH; ++i) {
				if (LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, this.yCoord, this.zCoord - i)) {
					extraMinZ++;
				} else {
					extraMinZ += 1.0D - laserSize;
					break;
				}
			}
		} else if (side == ForgeDirection.SOUTH.ordinal()) {
			for (int i = 1; i < Constants.LASER_REACH; ++i) {
				if (LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, this.yCoord, this.zCoord + i)) {
					extraMaxZ++;
				} else {
					extraMaxZ += 1.0D - laserSize;
					break;
				}
			}
		} else if (side == ForgeDirection.WEST.ordinal()) {
			for (int i = 1; i < Constants.LASER_REACH; ++i) {
				if (LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord - i, this.yCoord, this.zCoord)) {
					extraMinX++;
				} else {
					extraMinX += 1.0D - laserSize;
					break;
				}
			}
		} else if (side == ForgeDirection.EAST.ordinal()) {
			for (int i = 1; i < Constants.LASER_REACH; ++i) {
				if (LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord + i, this.yCoord, this.zCoord)) {
					extraMaxX++;
				} else {
					extraMaxX += 1.0D - laserSize;
					break;
				}
			}
		}
		boundingBox.setBounds(boundingBox.minX - extraMinX, boundingBox.minY - extraMinY, boundingBox.minZ - extraMinZ, boundingBox.maxX + extraMaxX, boundingBox.maxY + extraMaxY, boundingBox.maxZ + extraMaxZ);

		return boundingBox;
	}

	public boolean isValidSourceOfPowerOnSide(int side) {
		if (side == ForgeDirection.DOWN.ordinal()) {
			for (int i = this.yCoord - 1; this.yCoord - i >= 0; --i) {
				if (!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, i, this.zCoord)) {
					Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord, i, this.zCoord)];
					if (block != null && (block.blockID == LaserMod.basicLaser.blockID || block.blockID == LaserMod.advancedLaser.blockID)) {
						int meta = this.worldObj.getBlockMetadata(this.xCoord, i, this.zCoord);
						boolean hasPower = worldObj.isBlockIndirectlyGettingPowered(this.xCoord, i, this.zCoord);
						return meta == Facing.oppositeSide[side] && hasPower;
					} else if (block != null && block instanceof ILaserReciver) {
						return ((ILaserReciver) block).isSendingSignalFromSide(this.worldObj, this.xCoord, i, this.zCoord, this.xCoord, this.yCoord, this.zCoord, Facing.oppositeSide[side]);
					}
					break;
				}
			}
		} else if (side == ForgeDirection.UP.ordinal()) {
			for (int i = this.yCoord + 1; i < this.yCoord+ Constants.LASER_REACH; ++i) {
				if (!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, i, this.zCoord)) {
					Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord, i, this.zCoord)];
					if (block != null && (block.blockID == LaserMod.basicLaser.blockID || block.blockID == LaserMod.advancedLaser.blockID)) {
						int meta = this.worldObj.getBlockMetadata(this.xCoord, i, this.zCoord);
						boolean hasPower = worldObj.isBlockIndirectlyGettingPowered(this.xCoord, i, this.zCoord);
						return meta == Facing.oppositeSide[side] && hasPower;
					} else if (block != null && block instanceof ILaserReciver) {
						return ((ILaserReciver) block).isSendingSignalFromSide(this.worldObj, this.xCoord, i, this.zCoord, this.xCoord, this.yCoord, this.zCoord, Facing.oppositeSide[side]);
					}
					break;
				}
			}
		} else if (side == ForgeDirection.NORTH.ordinal()) {
			for (int i = 1; i < Constants.LASER_REACH; ++i) {
				if (!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, this.yCoord, this.zCoord - i)) {
					Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord - i)];
					if (block != null && (block.blockID == LaserMod.basicLaser.blockID || block.blockID == LaserMod.advancedLaser.blockID)) {
						int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord - i);
						boolean hasPower = worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord - i);
						return meta == Facing.oppositeSide[side] && hasPower;
					} else if (block != null && block instanceof ILaserReciver) {
						return ((ILaserReciver) block).isSendingSignalFromSide(this.worldObj, this.xCoord, this.yCoord, this.zCoord - i, this.xCoord, this.yCoord, this.zCoord, Facing.oppositeSide[side]);
					}
					break;
				}
			}
		} else if (side == ForgeDirection.SOUTH.ordinal()) {
			for (int i = 1; i < Constants.LASER_REACH; ++i) {
				if (!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, this.yCoord, this.zCoord + i)) {
					Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord + i)];
					if (block != null && (block.blockID == LaserMod.basicLaser.blockID || block.blockID == LaserMod.advancedLaser.blockID)) {
						int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord + i);
						boolean hasPower = worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord + i);
						return meta == Facing.oppositeSide[side] && hasPower;
					} else if (block != null && block instanceof ILaserReciver) {
						return ((ILaserReciver) block).isSendingSignalFromSide(this.worldObj, this.xCoord, this.yCoord, this.zCoord + i, this.xCoord, this.yCoord, this.zCoord, Facing.oppositeSide[side]);
					}
					break;
				}
			}
		} else if (side == ForgeDirection.WEST.ordinal()) {
			for (int i = 1; i < Constants.LASER_REACH; ++i) {
				if (!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord - i, this.yCoord, this.zCoord)) {
					Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord - i, this.yCoord, this.zCoord)];
					if (block != null && (block.blockID == LaserMod.basicLaser.blockID || block.blockID == LaserMod.advancedLaser.blockID)) {
						int meta = this.worldObj.getBlockMetadata(this.xCoord - i, this.yCoord, this.zCoord);
						boolean hasPower = worldObj.isBlockIndirectlyGettingPowered(this.xCoord - i, this.yCoord, this.zCoord);
						return meta == Facing.oppositeSide[side] && hasPower;
					} else if (block != null && block instanceof ILaserReciver) {
						return ((ILaserReciver) block).isSendingSignalFromSide(this.worldObj, this.xCoord - i, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord, Facing.oppositeSide[side]);
					}
					break;
				}
			}
		} else if (side == ForgeDirection.EAST.ordinal()) {
			for (int i = 1; i < Constants.LASER_REACH; ++i) {
				if (!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord + i, this.yCoord, this.zCoord)) {
					Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord + i, this.yCoord, this.zCoord)];
					if (block != null && (block.blockID == LaserMod.basicLaser.blockID || block.blockID == LaserMod.advancedLaser.blockID)) {
						int meta = this.worldObj.getBlockMetadata(this.xCoord + i, this.yCoord, this.zCoord);
						boolean hasPower = worldObj.isBlockIndirectlyGettingPowered(this.xCoord + i, this.yCoord, this.zCoord);
						return meta == Facing.oppositeSide[side] && hasPower;
					} else if (block != null && block instanceof ILaserReciver) {
						return ((ILaserReciver) block).isSendingSignalFromSide(
								this.worldObj, this.xCoord + i, this.yCoord,
								this.zCoord, this.xCoord, this.yCoord,
								this.zCoord, Facing.oppositeSide[side]);
					}
					break;
				}
			}
		}
		return false;
	}

	public ILaserReciver getFirstReciver(int meta) {
		if (meta == ForgeDirection.DOWN.ordinal()) {
			for (int i = this.yCoord - 1; this.yCoord - i >= 0; --i) {
				if (!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, i, this.zCoord)) {
					Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord, i, this.zCoord)];
					if (block != null && block instanceof ILaserReciver) {
						reciverCords = new int[] { this.xCoord, i, this.zCoord };
						return (ILaserReciver) block;
					}
					break;
				}
			}
		} else if (meta == ForgeDirection.UP.ordinal()) {
			for (int i = this.yCoord + 1; i < this.yCoord + Constants.LASER_REACH; ++i) {
				if (!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, i, this.zCoord)) {
					Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord, i, this.zCoord)];
					if (block != null && block instanceof ILaserReciver) {
						reciverCords = new int[] { this.xCoord, i, this.zCoord };
						return (ILaserReciver) block;
					}
					break;
				}
			}
		} else if (meta == ForgeDirection.NORTH.ordinal()) {
			for (int i = 1; i < Constants.LASER_REACH; ++i) {
				if (!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, this.yCoord, this.zCoord - i)) {
					Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord - i)];
					if (block != null && block instanceof ILaserReciver) {
						reciverCords = new int[] { this.xCoord, this.yCoord, this.zCoord - i };
						return (ILaserReciver) block;
					}
					break;
				}
			}
		} else if (meta == ForgeDirection.SOUTH.ordinal()) {
			for (int i = 1; i < Constants.LASER_REACH; ++i) {
				if (!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord, this.yCoord, this.zCoord + i)) {
					Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord + i)];
					if (block != null && block instanceof ILaserReciver) {
						reciverCords = new int[] { this.xCoord, this.yCoord, this.zCoord + i };
						return (ILaserReciver) block;
					}
					break;
				}
			}
		} else if (meta == ForgeDirection.WEST.ordinal()) {
			for (int i = 1; i < Constants.LASER_REACH; ++i) {
				if (!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord - i, this.yCoord, this.zCoord)) {
					Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord - i, this.yCoord, this.zCoord)];
					if (block != null && block instanceof ILaserReciver) {
						reciverCords = new int[] { this.xCoord - i, this.yCoord, this.zCoord };
						return (ILaserReciver) block;
					}
					break;
				}
			}
		} else if (meta == ForgeDirection.EAST.ordinal()) {
			for (int i = 1; i < Constants.LASER_REACH; ++i) {
				if (!LaserWhitelist.canLaserPassThrought(this.worldObj, this.xCoord + i, this.yCoord, this.zCoord)) {
					Block block = Block.blocksList[this.worldObj.getBlockId(this.xCoord + i, this.yCoord, this.zCoord)];
					if (block != null && block instanceof ILaserReciver) {
						reciverCords = new int[] { this.xCoord + i, this.yCoord, this.zCoord };
						return (ILaserReciver) block;
					}
					break;
				}
			}
		}

		return null;
	}

	@Override
	public void updateEntity() {
		if (this.lagReduce == 1) {
			this.lagReduce = 0;
			return;
		}

		for (int i = 0; i < this.openSides.length; ++i)
			if (this.openSides[i] || !isValidSourceOfPowerOnSide(i))
				this.removeAllLasersFromSide(i);

		for (int i = 0; i < this.openSides.length; ++i) {
			if (this.openSides[i] || this.containsInputSide(i) || this.lasers.size() == 0)
				continue;
			ILaserReciver reciver = getFirstReciver(i);
			if (reciver != null) {
				if (reciver.canPassOnSide(worldObj, reciverCords[0], reciverCords[1], reciverCords[2], this.xCoord, this.yCoord, this.zCoord, Facing.oppositeSide[i]))
					reciver.passLaser(worldObj, reciverCords[0], reciverCords[1], reciverCords[2], this.xCoord, this.yCoord, this.zCoord, this.getCreatedLaser(i));
			}
		}

		for (int i = 0; i < this.openSides.length; ++i) {
			if (!this.containsInputSide(i) && !this.openSides[i] && this.lasers.size() > 0) {
				AxisAlignedBB boundingBox = getFromLaserBox(this.xCoord, this.yCoord, this.zCoord, i);
				List<Entity> entities = this.worldObj.getEntitiesWithinAABB(Entity.class, boundingBox);
				for (ILaser la : getCreatedLaser(i).getLaserType()) {
					la.performActionOnEntitiesBoth(entities, i);
					if (this.worldObj.isRemote)
						la.performActionOnEntitiesClient(entities, i);
					else
						la.performActionOnEntitiesServer(entities, i);
				}
			}
		}

		this.lagReduce += 1;
	}

	public LaserInGame getCreatedLaser(int side) {
		int facing = Facing.oppositeSide[side];
		ArrayList<ILaser> laserList = new ArrayList<ILaser>();
		for (LaserInGame lig : this.lasers) {
			for (ILaser laser : lig.getLaserType())
				if (!laserList.contains(laserList))
					laserList.add(laser);
		}

		LaserInGame laserInGame = new LaserInGame(laserList);
		double totalPower = 0.0D;
		int red = lasers.get(0).red;
		int green = lasers.get(0).green;
		int blue = lasers.get(0).blue;
		for (int i = 1; i < lasers.size(); ++i) {
			red = (red * lasers.get(i).red) / 255;
			green = (green * lasers.get(i).green) / 255;
			blue = (blue * lasers.get(i).blue) / 255;
		}

		laserInGame.red = red;
		laserInGame.green = green;
		laserInGame.blue = blue;

		for (LaserInGame laser : lasers)
			totalPower += laser.getStrength();

		laserInGame.setSide(facing);
		laserInGame.setStrength(totalPower);

		return laserInGame;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

		NBTTagList list = tag.getTagList("openSides");
		for (int i = 0; i < list.tagCount(); ++i)
			openSides[i] = ((NBTTagByte) list.tagAt(i)).data == 1;

		int amount = tag.getInteger("laserInGameCount");
		for (int i = 0; i < amount; ++i) {
			double strength = tag.getDouble("strength" + i);
			int laserCount = tag.getInteger("laserCount" + i);
			ArrayList<ILaser> laserList = new ArrayList<ILaser>();
			for (int j = 0; j < laserCount; ++j)
				laserList.add(LaserRegistry.getLaserFromId(tag.getString(i + "laserId" + j)));
			int side = tag.getInteger("side" + i);
			LaserInGame laserInGame = new LaserInGame(laserList).setStrength(strength).setSide(side);
			lasers.add(laserInGame);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		NBTTagList list = new NBTTagList("openSides");
		for (int i = 0; i < openSides.length; ++i)
			list.appendTag(new NBTTagByte("" + i, (byte) (this.openSides[i] ? 1 : 0)));
		tag.setTag("openSides", list);

		tag.setInteger("laserInGameCount", this.lasers.size());
		for (int i = 0; i < lasers.size(); ++i) {
			tag.setDouble("strength" + i, lasers.get(i).getStrength());
			tag.setInteger("laserCount" + i, lasers.get(i).laserCount());
			int j = 0;
			for (ILaser laser : lasers.get(i).getLaserType()) {
				tag.setString(i + "laserId" + j, LaserRegistry.getIdFromLaser(laser));
				j++;
			}
			tag.setInteger("side" + i, lasers.get(i).getSide());
		}
	}

	@Override
	public Packet getDescriptionPacket() {
		return new PacketReflectorUpdate(this.xCoord, this.yCoord, this.zCoord, this).buildPacket();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 6400.0D;
	}
}