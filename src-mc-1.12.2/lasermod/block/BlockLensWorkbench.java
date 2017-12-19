package lasermod.block;

import lasermod.LaserMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * @author ProPercivalalb
 */
public class BlockLensWorkbench extends Block {
	
    public BlockLensWorkbench() {
        super(Material.WOOD);
        this.setCreativeTab(LaserMod.TAB_LASER);
    }

}