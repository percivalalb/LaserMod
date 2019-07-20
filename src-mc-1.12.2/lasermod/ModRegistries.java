package lasermod;

import lasermod.api.LaserModAPI;
import lasermod.api.LaserType;
import lasermod.lib.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModRegistries {

	@SubscribeEvent
    public static void newRegistry(RegistryEvent.NewRegistry event) {
    	LaserModAPI.LASER_TYPES = makeRegistry(new ResourceLocation(Reference.MOD_ID, "laser_types"), LaserType.class, 64).disableSaving().create();
	}
	
	private static <T extends IForgeRegistryEntry<T>> RegistryBuilder<T> makeRegistry(ResourceLocation name, Class<T> type, int max) {
        return new RegistryBuilder<T>().setName(name).setType(type).setMaxID(max);
    }

}
