package lasermod.api;

import java.util.List;

import javax.annotation.Nullable;

import lasermod.util.BlockActionPos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * @author ProPercivalalb
 */
public class LaserType extends IForgeRegistryEntry.Impl<LaserType> {

	@Nullable
	private String translationKey;
	
	public void performActionOnEntitiesBoth(List<Entity> entities, EnumFacing dir) {
	}
	
	public void performActionOnEntitiesClient(List<Entity> entities, EnumFacing dir) {
	}
	
	public void performActionOnEntitiesServer(List<Entity> entities, EnumFacing dir) {
	}
	
	public boolean shouldRenderLaser(EntityPlayer player, EnumFacing dir) {
		return true;
	}
	
	public void actionOnBlock(BlockActionPos reciver) {
	}
	
	public String getTranslationKey() {
		if (this.translationKey == null) {
			// 1.13 Util.makeTranslationKey("laser", LaserModAPI.LASER_TYPES.getKey(this));
			this.translationKey = LaserType.makeTranslationKey("laser", LaserModAPI.LASER_TYPES.getKey(this));
		}

		return this.translationKey;
	}
	
	public static String makeTranslationKey(String type, @Nullable ResourceLocation id) {
		return id == null ? type + ".unregistered_sadface" : type + '.' + id.getResourceDomain() + '.' + id.getResourcePath().replace('/', '.');
	}
}
