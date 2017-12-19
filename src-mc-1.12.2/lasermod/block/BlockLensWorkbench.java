package lasermod.block;

import lasermod.LaserMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class BlockLensWorkbench extends Block {
	
    public BlockLensWorkbench() {
        super(Material.WOOD);
        this.setCreativeTab(LaserMod.TAB_LASER);
    }

}