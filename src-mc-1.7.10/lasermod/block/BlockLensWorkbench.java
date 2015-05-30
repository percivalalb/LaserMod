package lasermod.block;

import lasermod.LaserMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
public class BlockLensWorkbench extends Block {
	
    @SideOnly(Side.CLIENT)
    private IIcon sideIcon;

    public BlockLensWorkbench() {
        super(Material.wood);
        this.setCreativeTab(LaserMod.tabLaser);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return p_149691_1_ == 1 ? this.sideIcon : (p_149691_1_ == 0 ? Blocks.planks.getBlockTextureFromSide(p_149691_1_) : this.blockIcon);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon("lasermod:lensBenchSide");
        this.sideIcon = p_149651_1_.registerIcon(this.getTextureName() + "_top");
    }
}