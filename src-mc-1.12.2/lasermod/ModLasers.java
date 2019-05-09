package lasermod;

import lasermod.api.LaserType;
import lasermod.laser.DamageLaser;
import lasermod.laser.FireLaser;
import lasermod.laser.HealingLaser;
import lasermod.laser.IceLaser;
import lasermod.laser.InvisibleLaser;
import lasermod.laser.MiningLaser;
import lasermod.laser.PullLaser;
import lasermod.laser.PushLaser;
import lasermod.laser.WaterLaser;
import lasermod.lib.LaserNames;
import lasermod.lib.Reference;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * @author ProPercivalalb
 */
public class ModLasers {
	
	@ObjectHolder(LaserNames.FIRE)
	public static LaserType FIRE;
	@ObjectHolder(LaserNames.WATER)
	public static LaserType WATER;
	@ObjectHolder(LaserNames.ICE)
	public static LaserType ICE;
	@ObjectHolder(LaserNames.INVISIBLE)
	public static LaserType INVISIBLE;
	@ObjectHolder(LaserNames.MINING)
	public static LaserType MINING;
	@ObjectHolder(LaserNames.PUSH)
	public static LaserType PUSH;
	@ObjectHolder(LaserNames.PULL)
	public static LaserType PULL;
	@ObjectHolder(LaserNames.DAMAGE)
	public static LaserType DAMAGE;
	@ObjectHolder(LaserNames.HEAL)
	public static LaserType HEAL;
	
	
	@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Registration {
		
	    @SubscribeEvent
	    public static void onLaserTypeRegister(final RegistryEvent.Register<LaserType> event) {
	    	IForgeRegistry<LaserType> laserRegistry = event.getRegistry();
	    	
	    	laserRegistry.register(new FireLaser().setRegistryName(LaserNames.FIRE));
	    	laserRegistry.register(new WaterLaser().setRegistryName(LaserNames.WATER));
	    	laserRegistry.register(new IceLaser().setRegistryName(LaserNames.ICE));
	    	laserRegistry.register(new InvisibleLaser().setRegistryName(LaserNames.INVISIBLE));
	    	laserRegistry.register(new MiningLaser().setRegistryName(LaserNames.MINING));
	    	laserRegistry.register(new PushLaser().setRegistryName(LaserNames.PUSH));
	    	laserRegistry.register(new PullLaser().setRegistryName(LaserNames.PULL));
	    	laserRegistry.register(new DamageLaser().setRegistryName(LaserNames.DAMAGE));
	    	laserRegistry.register(new HealingLaser().setRegistryName(LaserNames.HEAL));
	    }
    }
}
