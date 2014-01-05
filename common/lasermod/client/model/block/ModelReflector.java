package lasermod.client.model.block;

import lasermod.tileentity.TileEntityReflector;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

/**
 * @author ProPercivalalb
 */
public class ModelReflector extends ModelBase {
	private ModelRenderer columnFR, columnBL, columnFL, columnBR, topBlocker, rightBlocker, frontBlocker, leftBlocker, backBlocker, bottomBlocker, bitTB, bitTF, bitTL, bitTR, bitBB, bitBR, bitBF, bitBL;

	public ModelReflector() {
		textureWidth = 128;
		textureHeight = 128;

		columnFR = new ModelRenderer(this, 75, 42);
		columnFR.addBox(0F, 0F, 0F, 6, 16, 6);
		columnFR.setRotationPoint(-8F, 8F, -8F);
		columnFR.setTextureSize(128, 128);
		columnFR.mirror = true;
		setRotation(columnFR, 0F, 0F, 0F);
		columnBL = new ModelRenderer(this, 25, 42);
		columnBL.addBox(0F, 0F, 0F, 6, 16, 6);
		columnBL.setRotationPoint(2F, 8F, 2F);
		columnBL.setTextureSize(128, 128);
		columnBL.mirror = true;
		setRotation(columnBL, 0F, 0F, 0F);
		columnFL = new ModelRenderer(this, 0, 42);
		columnFL.addBox(0F, 0F, 0F, 6, 16, 6);
		columnFL.setRotationPoint(2F, 8F, -8F);
		columnFL.setTextureSize(128, 128);
		columnFL.mirror = true;
		setRotation(columnFL, 0F, 0F, 0F);
		columnBR = new ModelRenderer(this, 50, 42);
		columnBR.addBox(0F, 0F, 0F, 6, 16, 6);
		columnBR.setRotationPoint(-8F, 8F, 2F);
		columnBR.setTextureSize(128, 128);
		columnBR.mirror = true;
		setRotation(columnBR, 0F, 0F, 0F);
		topBlocker = new ModelRenderer(this, 0, 6);
		topBlocker.addBox(0F, 0F, 0F, 4, 1, 4);
		topBlocker.setRotationPoint(-2F, 8F, -2F);
		topBlocker.setTextureSize(128, 128);
		topBlocker.mirror = true;
		setRotation(topBlocker, 0F, 0F, 0F);
		rightBlocker = new ModelRenderer(this, 28, 0);
		rightBlocker.addBox(0F, 0F, 0F, 1, 4, 4);
		rightBlocker.setRotationPoint(-8F, 14F, -2F);
		rightBlocker.setTextureSize(128, 128);
		rightBlocker.mirror = true;
		setRotation(rightBlocker, 0F, 0F, 0F);
		frontBlocker = new ModelRenderer(this, 17, 6);
		frontBlocker.addBox(0F, 0F, 0F, 4, 4, 1);
		frontBlocker.setRotationPoint(-2F, 14F, -8F);
		frontBlocker.setTextureSize(128, 128);
		frontBlocker.mirror = true;
		setRotation(frontBlocker, 0F, 0F, 0F);
		leftBlocker = new ModelRenderer(this, 28, 9);
		leftBlocker.addBox(0F, 0F, 0F, 1, 4, 4);
		leftBlocker.setRotationPoint(7F, 14F, -2F);
		leftBlocker.setTextureSize(128, 128);
		leftBlocker.mirror = true;
		setRotation(leftBlocker, 0F, 0F, 0F);
		backBlocker = new ModelRenderer(this, 17, 0);
		backBlocker.addBox(0F, 0F, 0F, 4, 4, 1);
		backBlocker.setRotationPoint(-2F, 14F, 7F);
		backBlocker.setTextureSize(128, 128);
		backBlocker.mirror = true;
		setRotation(backBlocker, 0F, 0F, 0F);
		bottomBlocker = new ModelRenderer(this, 0, 0);
		bottomBlocker.addBox(0F, 0F, 0F, 4, 1, 4);
		bottomBlocker.setRotationPoint(-2F, 23F, -2F);
		bottomBlocker.setTextureSize(128, 128);
		bottomBlocker.mirror = true;
		setRotation(bottomBlocker, 0F, 0F, 0F);
		bitTB = new ModelRenderer(this, 21, 69);
		bitTB.addBox(0F, 0F, 0F, 4, 6, 6);
		bitTB.setRotationPoint(-2F, 8F, 2F);
		bitTB.setTextureSize(128, 128);
		bitTB.mirror = true;
		setRotation(bitTB, 0F, 0F, 0F);
		bitTF = new ModelRenderer(this, 42, 69);
		bitTF.addBox(0F, 0F, 0F, 4, 6, 6);
		bitTF.setRotationPoint(-2F, 8F, -8F);
		bitTF.setTextureSize(128, 128);
		bitTF.mirror = true;
		setRotation(bitTF, 0F, 0F, 0F);
		bitTL = new ModelRenderer(this, 0, 69);
		bitTL.addBox(-1F, 0F, -2F, 6, 6, 4);
		bitTL.setRotationPoint(3F, 8F, 0F);
		bitTL.setTextureSize(128, 128);
		bitTL.mirror = true;
		setRotation(bitTL, 0F, 0F, 0F);
		bitTR = new ModelRenderer(this, 63, 69);
		bitTR.addBox(0F, 0F, 0F, 6, 6, 4);
		bitTR.setRotationPoint(-8F, 8F, -2F);
		bitTR.setTextureSize(128, 128);
		bitTR.mirror = true;
		setRotation(bitTR, 0F, 0F, 0F);
		bitBB = new ModelRenderer(this, 105, 69);
		bitBB.addBox(0F, 0F, 0F, 4, 6, 6);
		bitBB.setRotationPoint(-2F, 18F, 2F);
		bitBB.setTextureSize(128, 128);
		bitBB.mirror = true;
		setRotation(bitBB, 0F, 0F, 0F);
		bitBR = new ModelRenderer(this, 84, 69);
		bitBR.addBox(0F, 0F, 0F, 6, 6, 4);
		bitBR.setRotationPoint(-8F, 18F, -2F);
		bitBR.setTextureSize(128, 128);
		bitBR.mirror = true;
		setRotation(bitBR, 0F, 0F, 0F);
		bitBF = new ModelRenderer(this, 21, 82);
		bitBF.addBox(0F, 0F, 0F, 4, 6, 6);
		bitBF.setRotationPoint(-2F, 18F, -8F);
		bitBF.setTextureSize(128, 128);
		bitBF.mirror = true;
		setRotation(bitBF, 0F, 0F, 0F);
		bitBL = new ModelRenderer(this, 0, 82);
		bitBL.addBox(0F, 0F, 0F, 6, 6, 4);
		bitBL.setRotationPoint(2F, 18F, -2F);
		bitBL.setTextureSize(128, 128);
		bitBL.mirror = true;
		setRotation(bitBL, 0F, 0F, 0F);
	}

	public void renderModel() {
		columnFR.render(0.0625F);
		columnBL.render(0.0625F);
		columnFL.render(0.0625F);
		columnBR.render(0.0625F);
		topBlocker.render(0.0625F);
		rightBlocker.render(0.0625F);
		frontBlocker.render(0.0625F);
		leftBlocker.render(0.0625F);
		backBlocker.render(0.0625F);
		bottomBlocker.render(0.0625F);
		bitTB.render(0.0625F);
		bitTF.render(0.0625F);
		bitTL.render(0.0625F);
		bitTR.render(0.0625F);
		bitBB.render(0.0625F);
		bitBR.render(0.0625F);
		bitBF.render(0.0625F);
		bitBL.render(0.0625F);
	}

	public void renderModel(TileEntityReflector reflector) {
		columnFR.render(0.0625F);
		columnBL.render(0.0625F);
		columnFL.render(0.0625F);
		columnBR.render(0.0625F);
		ModelRenderer[] blockers = new ModelRenderer[] { bottomBlocker, topBlocker, backBlocker, frontBlocker, rightBlocker, leftBlocker };
		for (int i = 0; i < 6; ++i) {
			if (reflector.openSides[i])
				blockers[i].render(0.0625F);
		}
		bitTB.render(0.0625F);
		bitTF.render(0.0625F);
		bitTL.render(0.0625F);
		bitTR.render(0.0625F);
		bitBB.render(0.0625F);
		bitBR.render(0.0625F);
		bitBF.render(0.0625F);
		bitBL.render(0.0625F);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}