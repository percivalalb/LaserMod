package PigbearLaser.Render;

import PigbearLaser.Beam;
import PigbearLaser.BlockBeam;
import PigbearLaser.EntityBeam;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ModLoader;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.Tessellator;
import net.minecraft.src.mod_PigbearLaser;
import org.lwjgl.opengl.GL11;

public class BeamRender extends BlockRenderer {

	static int brightness;
	
	@Override
	public void renderWorld( RenderBlocks blocks, IBlockAccess world, int x, int y, int z, Block block ) {

		Tessellator tessellator = Tessellator.instance;
		RenderEngine engine = ModLoader.getMinecraftInstance().renderEngine;

		tessellator.draw();
		//engine.bindTexture( 0 );
		GL11.glDisable( GL11.GL_TEXTURE_2D );
		EntityBeam beam = ( EntityBeam ) world.getBlockTileEntity( x, y, z );
		
		brightness = mod_PigbearLaser.blockLaserBeam.getMixedBrightnessForBlock( world, x, y, z );

		float f_x = ( float ) x + 0.5f;
		float f_y = ( float ) y + 0.5f;
		float f_z = ( float ) z + 0.5f;
		if( BlockBeam.renderPass == 1 ^ mod_PigbearLaser.beamTransparency == 0 ) {
			if( beam.isOnAxis( 2 ) ) {
				RenderInfo info_x = getBeamInfo( beam, 2, x, y, z, world );

				if( beam.isOnAxis( 0 ) ) {
					RenderInfo info_y = getBeamInfo( beam, 0, x, y, z, world );

					if( beam.isOnAxis( 1 ) ) {
						// x-y-z-intersection
						RenderInfo info_z = getBeamInfo( beam, 1, x, y, z, world );

						if( info_x.thickness == info_y.thickness && info_x.thickness == info_z.thickness ) {
							if( !info_x.end_at_pos )
								renderBeamX( tessellator, info_x, f_x, f_y, f_z, -0.0625f * info_x.thickness, 0.5f );
							if( !info_x.end_at_neg )
								renderBeamX( tessellator, info_x, f_x, f_y, f_z, 0.5f, -0.0625f * info_x.thickness );
							if( !info_y.end_at_pos )
								renderBeamY( tessellator, info_y, f_x, f_y, f_z, -0.0625f * info_x.thickness, 0.5f );
							if( !info_y.end_at_neg )
								renderBeamY( tessellator, info_y, f_x, f_y, f_z, 0.5f, -0.0625f * info_x.thickness );
							if( !info_z.end_at_pos )
								renderBeamZ( tessellator, info_z, f_x, f_y, f_z, -0.0625f * info_x.thickness, 0.5f );
							if( !info_z.end_at_neg )
								renderBeamZ( tessellator, info_z, f_x, f_y, f_z, 0.5f, -0.0625f * info_x.thickness );

							tessellator.startDrawing( 7 );
							tessellator.setColorRGBA_I( info_x.color | info_y.color, info_y.alpha );

							double power = info_x.thickness * 0.0625;

							if( info_y.cap_pos ) {
								tessellator.addVertex( f_x - power, f_y + power, f_z - power );
								tessellator.addVertex( f_x - power, f_y + power, f_z + power );
								tessellator.addVertex( f_x + power, f_y + power, f_z + power );
								tessellator.addVertex( f_x + power, f_y + power, f_z - power );
							}

							if( info_y.cap_neg ) {
								tessellator.addVertex( f_x - power, f_y - power, f_z + power );
								tessellator.addVertex( f_x - power, f_y - power, f_z - power );
								tessellator.addVertex( f_x + power, f_y - power, f_z - power );
								tessellator.addVertex( f_x + power, f_y - power, f_z + power );
							}

							if( info_x.cap_neg ) {
								tessellator.addVertex( f_x - power, f_y - power, f_z - power );
								tessellator.addVertex( f_x - power, f_y - power, f_z + power );
								tessellator.addVertex( f_x - power, f_y + power, f_z + power );
								tessellator.addVertex( f_x - power, f_y + power, f_z - power );
							}

							if( info_x.cap_pos ) {
								tessellator.addVertex( f_x + power, f_y - power, f_z + power );
								tessellator.addVertex( f_x + power, f_y - power, f_z - power );
								tessellator.addVertex( f_x + power, f_y + power, f_z - power );
								tessellator.addVertex( f_x + power, f_y + power, f_z + power );
							}

							if( info_z.cap_neg ) {
								tessellator.addVertex( f_x - power, f_y - power, f_z - power );
								tessellator.addVertex( f_x - power, f_y + power, f_z - power );
								tessellator.addVertex( f_x + power, f_y + power, f_z - power );
								tessellator.addVertex( f_x + power, f_y - power, f_z - power );
							}

							if( info_z.cap_pos ) {
								tessellator.addVertex( f_x - power, f_y + power, f_z + power );
								tessellator.addVertex( f_x - power, f_y - power, f_z + power );
								tessellator.addVertex( f_x + power, f_y - power, f_z + power );
								tessellator.addVertex( f_x + power, f_y + power, f_z + power );
							}

							tessellator.draw();

						} else if( info_x.thickness >= info_y.thickness && info_x.thickness >= info_z.thickness ) {
							renderBeamX( tessellator, info_x, f_x, f_y, f_z, 0.5f, 0.5f );
							if( !info_y.end_at_pos )
								renderBeamY( tessellator, info_y, f_x, f_y, f_z, -0.0625f * info_x.thickness, 0.5f );
							if( !info_y.end_at_neg )
								renderBeamY( tessellator, info_y, f_x, f_y, f_z, 0.5f, -0.0625f * info_x.thickness );
							if( !info_z.end_at_pos )
								renderBeamZ( tessellator, info_z, f_x, f_y, f_z, -0.0625f * info_x.thickness, 0.5f );
							if( !info_z.end_at_neg )
								renderBeamZ( tessellator, info_z, f_x, f_y, f_z, 0.5f, -0.0625f * info_x.thickness );
						} else if( info_y.thickness >= info_x.thickness && info_y.thickness >= info_z.thickness ) {
						} else {
							renderBeamZ( tessellator, info_z, f_x, f_y, f_z, 0.5f, 0.5f );
							if( !info_y.end_at_pos )
								renderBeamY( tessellator, info_y, f_x, f_y, f_z, -0.0625f * info_z.thickness, 0.5f );
							if( !info_y.end_at_neg )
								renderBeamY( tessellator, info_y, f_x, f_y, f_z, 0.5f, -0.0625f * info_z.thickness );
							if( !info_x.end_at_pos )
								renderBeamX( tessellator, info_x, f_x, f_y, f_z, -0.0625f * info_z.thickness, 0.5f );
							if( !info_x.end_at_neg )
								renderBeamX( tessellator, info_x, f_x, f_y, f_z, 0.5f, -0.0625f * info_z.thickness );

						}

					} else {
						// x-y-intersection
						if( info_x.thickness > info_y.thickness ) {
							renderBeamX( tessellator, info_x, f_x, f_y, f_z, 0.5f, 0.5f );
							if( !info_y.end_at_pos )
								renderBeamY( tessellator, info_y, f_x, f_y, f_z, 0.0625f * info_x.thickness, 0.5f );
							if( !info_y.end_at_neg )
								renderBeamY( tessellator, info_y, f_x, f_y, f_z, 0.5f, 0.0625f * info_x.thickness );
						} else if( info_x.thickness < info_y.thickness ) {
							renderBeamY( tessellator, info_y, f_x, f_y, f_z, 0.5f, 0.5f );
							if( !info_x.end_at_pos )
								renderBeamX( tessellator, info_x, f_x, f_y, f_z, 0.0625f * info_y.thickness, 0.5f );
							if( !info_x.end_at_neg )
								renderBeamX( tessellator, info_x, f_x, f_y, f_z, 0.5f, 0.0625f * info_y.thickness );
						} else {
							if( !info_x.end_at_pos )
								renderBeamX( tessellator, info_x, f_x, f_y, f_z, -0.0625f * info_x.thickness, 0.5f );
							if( !info_x.end_at_neg )
								renderBeamX( tessellator, info_x, f_x, f_y, f_z, 0.5f, -0.0625f * info_x.thickness );
							if( !info_y.end_at_pos )
								renderBeamY( tessellator, info_y, f_x, f_y, f_z, -0.0625f * info_x.thickness, 0.5f );
							if( !info_y.end_at_neg )
								renderBeamY( tessellator, info_y, f_x, f_y, f_z, 0.5f, -0.0625f * info_x.thickness );

							tessellator.startDrawing( 7 );
							tessellator.setColorRGBA_I( info_x.color | info_y.color, info_y.alpha );

							double power = info_x.thickness * 0.0625;

							if( info_y.cap_pos ) {
								tessellator.addVertex( f_x - power, f_y + power, f_z - power );
								tessellator.addVertex( f_x - power, f_y + power, f_z + power );
								tessellator.addVertex( f_x + power, f_y + power, f_z + power );
								tessellator.addVertex( f_x + power, f_y + power, f_z - power );
							}

							if( info_y.cap_neg ) {
								tessellator.addVertex( f_x - power, f_y - power, f_z + power );
								tessellator.addVertex( f_x - power, f_y - power, f_z - power );
								tessellator.addVertex( f_x + power, f_y - power, f_z - power );
								tessellator.addVertex( f_x + power, f_y - power, f_z + power );
							}

							if( info_x.cap_neg ) {
								tessellator.addVertex( f_x - power, f_y - power, f_z - power );
								tessellator.addVertex( f_x - power, f_y - power, f_z + power );
								tessellator.addVertex( f_x - power, f_y + power, f_z + power );
								tessellator.addVertex( f_x - power, f_y + power, f_z - power );
							}

							if( info_x.cap_pos ) {
								tessellator.addVertex( f_x + power, f_y - power, f_z + power );
								tessellator.addVertex( f_x + power, f_y - power, f_z - power );
								tessellator.addVertex( f_x + power, f_y + power, f_z - power );
								tessellator.addVertex( f_x + power, f_y + power, f_z + power );
							}

							tessellator.addVertex( f_x - power, f_y - power, f_z - power );
							tessellator.addVertex( f_x - power, f_y + power, f_z - power );
							tessellator.addVertex( f_x + power, f_y + power, f_z - power );
							tessellator.addVertex( f_x + power, f_y - power, f_z - power );

							tessellator.addVertex( f_x - power, f_y + power, f_z + power );
							tessellator.addVertex( f_x - power, f_y - power, f_z + power );
							tessellator.addVertex( f_x + power, f_y - power, f_z + power );
							tessellator.addVertex( f_x + power, f_y + power, f_z + power );

							tessellator.draw();
						}

					}
				} else if( beam.isOnAxis( 1 ) ) {
					// x-z-intersection
					RenderInfo info_z = getBeamInfo( beam, 1, x, y, z, world );
					if( info_x.thickness > info_z.thickness ) {
						renderBeamX( tessellator, info_x, f_x, f_y, f_z, 0.5f, 0.5f );
						if( !info_z.end_at_pos )
							renderBeamZ( tessellator, info_z, f_x, f_y, f_z, -0.0625f * info_x.thickness, 0.5f );
						if( !info_z.end_at_neg )
							renderBeamZ( tessellator, info_z, f_x, f_y, f_z, 0.5f, -0.0625f * info_x.thickness );
					} else if( info_x.thickness < info_z.thickness ) {
						renderBeamZ( tessellator, info_z, f_x, f_y, f_z, 0.5f, 0.5f );
						if( !info_x.end_at_pos )
							renderBeamX( tessellator, info_x, f_x, f_y, f_z, -0.0625f * info_z.thickness, 0.5f );
						if( !info_x.end_at_neg )
							renderBeamX( tessellator, info_x, f_x, f_y, f_z, 0.5f, -0.0625f * info_z.thickness );
					} else {
						if( !info_x.end_at_pos )
							renderBeamX( tessellator, info_x, f_x, f_y, f_z, -0.0625f * info_x.thickness, 0.5f );
						if( !info_x.end_at_neg )
							renderBeamX( tessellator, info_x, f_x, f_y, f_z, 0.5f, -0.0625f * info_x.thickness );
						if( !info_z.end_at_pos )
							renderBeamZ( tessellator, info_z, f_x, f_y, f_z, -0.0625f * info_x.thickness, 0.5f );
						if( !info_z.end_at_neg )
							renderBeamZ( tessellator, info_z, f_x, f_y, f_z, 0.5f, -0.0625f * info_x.thickness );

						tessellator.startDrawing( 7 );
						tessellator.setColorRGBA_I( info_x.color | info_z.color, info_z.alpha );

						double power = info_x.thickness * 0.0625;

						tessellator.addVertex( f_x - power, f_y + power, f_z - power );
						tessellator.addVertex( f_x - power, f_y + power, f_z + power );
						tessellator.addVertex( f_x + power, f_y + power, f_z + power );
						tessellator.addVertex( f_x + power, f_y + power, f_z - power );

						tessellator.addVertex( f_x - power, f_y - power, f_z + power );
						tessellator.addVertex( f_x - power, f_y - power, f_z - power );
						tessellator.addVertex( f_x + power, f_y - power, f_z - power );
						tessellator.addVertex( f_x + power, f_y - power, f_z + power );

						if( info_x.cap_neg ) {
							tessellator.addVertex( f_x - power, f_y - power, f_z - power );
							tessellator.addVertex( f_x - power, f_y - power, f_z + power );
							tessellator.addVertex( f_x - power, f_y + power, f_z + power );
							tessellator.addVertex( f_x - power, f_y + power, f_z - power );
						}

						if( info_x.cap_pos ) {
							tessellator.addVertex( f_x + power, f_y - power, f_z + power );
							tessellator.addVertex( f_x + power, f_y - power, f_z - power );
							tessellator.addVertex( f_x + power, f_y + power, f_z - power );
							tessellator.addVertex( f_x + power, f_y + power, f_z + power );
						}

						if( info_z.cap_neg ) {
							tessellator.addVertex( f_x - power, f_y - power, f_z - power );
							tessellator.addVertex( f_x - power, f_y + power, f_z - power );
							tessellator.addVertex( f_x + power, f_y + power, f_z - power );
							tessellator.addVertex( f_x + power, f_y - power, f_z - power );
						}

						if( info_z.cap_pos ) {
							tessellator.addVertex( f_x - power, f_y + power, f_z + power );
							tessellator.addVertex( f_x - power, f_y - power, f_z + power );
							tessellator.addVertex( f_x + power, f_y - power, f_z + power );
							tessellator.addVertex( f_x + power, f_y + power, f_z + power );
						}
						tessellator.draw();
					}
				} else {
					// straight x-beam
					renderBeamX( tessellator, info_x, f_x, f_y, f_z, 0.5f, 0.5f );

				}
			} else if( beam.isOnAxis( 0 ) ) {
				RenderInfo info_y = getBeamInfo( beam, 0, x, y, z, world );

				if( beam.isOnAxis( 1 ) ) {
					// y-z-intersection
					RenderInfo info_z = getBeamInfo( beam, 1, x, y, z, world );
					if( info_z.thickness > info_y.thickness ) {
						renderBeamZ( tessellator, info_z, f_x, f_y, f_z, 0.5f, 0.5f );
						if( !info_y.end_at_pos )
							renderBeamY( tessellator, info_y, f_x, f_y, f_z, -0.0625f * info_z.thickness, 0.5f );
						if( !info_y.end_at_neg )
							renderBeamY( tessellator, info_y, f_x, f_y, f_z, 0.5f, -0.0625f * info_z.thickness );
					} else if( info_z.thickness < info_y.thickness ) {
						renderBeamY( tessellator, info_y, f_x, f_y, f_z, 0.5f, 0.5f );
						if( !info_z.end_at_pos )
							renderBeamZ( tessellator, info_z, f_x, f_y, f_z, -0.0625f * info_y.thickness, 0.5f );
						if( !info_z.end_at_neg )
							renderBeamZ( tessellator, info_z, f_x, f_y, f_z, 0.5f, -0.0625f * info_y.thickness );
					} else {
						if( !info_z.end_at_pos )
							renderBeamZ( tessellator, info_z, f_x, f_y, f_z, -0.0625f * info_z.thickness, 0.5f );
						if( !info_z.end_at_neg )
							renderBeamZ( tessellator, info_z, f_x, f_y, f_z, 0.5f, -0.0625f * info_z.thickness );
						if( !info_y.end_at_pos )
							renderBeamY( tessellator, info_y, f_x, f_y, f_z, -0.0625f * info_z.thickness, 0.5f );
						if( !info_y.end_at_neg )
							renderBeamY( tessellator, info_y, f_x, f_y, f_z, 0.5f, -0.0625f * info_z.thickness );

						tessellator.startDrawing( 7 );
						tessellator.setColorRGBA_I( info_z.color | info_y.color, info_y.alpha );

						double power = info_z.thickness * 0.0625;

						if( info_y.cap_pos ) {
							tessellator.addVertex( f_x - power, f_y + power, f_z - power );
							tessellator.addVertex( f_x - power, f_y + power, f_z + power );
							tessellator.addVertex( f_x + power, f_y + power, f_z + power );
							tessellator.addVertex( f_x + power, f_y + power, f_z - power );
						}

						if( info_y.cap_neg ) {
							tessellator.addVertex( f_x - power, f_y - power, f_z + power );
							tessellator.addVertex( f_x - power, f_y - power, f_z - power );
							tessellator.addVertex( f_x + power, f_y - power, f_z - power );
							tessellator.addVertex( f_x + power, f_y - power, f_z + power );
						}

						tessellator.addVertex( f_x - power, f_y - power, f_z - power );
						tessellator.addVertex( f_x - power, f_y - power, f_z + power );
						tessellator.addVertex( f_x - power, f_y + power, f_z + power );
						tessellator.addVertex( f_x - power, f_y + power, f_z - power );

						tessellator.addVertex( f_x + power, f_y - power, f_z + power );
						tessellator.addVertex( f_x + power, f_y - power, f_z - power );
						tessellator.addVertex( f_x + power, f_y + power, f_z - power );
						tessellator.addVertex( f_x + power, f_y + power, f_z + power );

						if( info_z.cap_neg ) {
							tessellator.addVertex( f_x - power, f_y - power, f_z - power );
							tessellator.addVertex( f_x - power, f_y + power, f_z - power );
							tessellator.addVertex( f_x + power, f_y + power, f_z - power );
							tessellator.addVertex( f_x + power, f_y - power, f_z - power );
						}

						if( info_z.cap_pos ) {
							tessellator.addVertex( f_x - power, f_y + power, f_z + power );
							tessellator.addVertex( f_x - power, f_y - power, f_z + power );
							tessellator.addVertex( f_x + power, f_y - power, f_z + power );
							tessellator.addVertex( f_x + power, f_y + power, f_z + power );
						}

						tessellator.draw();
					}
				} else {
					// straight y-beam
					renderBeamY( tessellator, info_y, f_x, f_y, f_z, 0.5f, 0.5f );

				}
			} else if( beam.isOnAxis( 1 ) ) {
				// straight z-beam
				RenderInfo info_z = getBeamInfo( beam, 1, x, y, z, world );
				renderBeamZ( tessellator, info_z, f_x, f_y, f_z, 0.5f, 0.5f );

			}
		}
		GL11.glEnable( GL11.GL_TEXTURE_2D );
		renderLens( tessellator, world, beam, x, y, z );

		tessellator.startDrawing( 7 );

		//engine.bindTexture( engine.getTexture( "/terrain.png" ) );

		if( BlockBeam.renderPass == 0 && BlockBeam.inGlass( world, x, y, z ) ) {
			blocks.renderStandardBlock( Block.blocksList[ world.getBlockId( x, y, z )], x, y, z );
			tessellator.draw();
			tessellator.startDrawing( 7 );
		}
	}

	private RenderInfo getBeamInfo( EntityBeam beam, int axis, int x, int y, int z, IBlockAccess world ) {
		axis <<= 1;

		RenderInfo ret = new RenderInfo();
		Beam pos = beam.get( axis );
		Beam neg = beam.get( axis + 1 );
		
		if( pos != null ) {
			if( pos.hasBeamAt( x, y, z ) ) {
				if( neg != null ) {
					if( neg.hasBeamAt( x, y, z ) ) {
						if( pos.getPower() > neg.getPower() ) {
							ret.color = pos.getColor().toRGB();
							ret.power = pos.getPower();
						} else if( neg.getPower() > pos.getPower() ) {
							ret.color = neg.getColor().toRGB();
							ret.power = neg.getPower();
						} else {
							if( pos.getColor() == neg.getColor() ) {
								ret.color = pos.getColor().toRGB();
							} else {
								ret.color = pos.getColor().mix( neg.getColor() ).toRGB();
							}
							ret.power = pos.getPower();
							
						}
					}
					if( neg.lens != null && neg.isStart( x, y, z ) )
						ret.lens_at_neg = true;
				} else {
					ret.color = pos.getColor().toRGB();
					ret.power = pos.getPower();
					ret.cap_neg = pos.isEnd( x, y, z );
					ret.end_at_neg = pos.isMax( x, y, z );
				}
			}
			if( pos.lens != null && pos.isStart( x, y, z ) )
				ret.lens_at_pos = true;
		} else if( neg != null ) {
			if( neg.hasBeamAt( x, y, z ) ) {
				ret.color = neg.getColor().toRGB();
				ret.power = neg.getPower();
				ret.cap_pos = neg.isEnd( x, y, z );
				ret.end_at_pos = neg.isMax( x, y, z );
			}
			if( neg.lens != null && neg.isStart( x, y, z ) )
				ret.lens_at_neg = true;
		}
		if( ret.power != 0 ) {
			int mod = (int)((9 - ret.power - 1) * 10);
			
			if( pos != null && !pos.getInvisible() ) mod = 0;
			if( neg != null && !neg.getInvisible() ) mod = 0;
			ret.thickness = ( ret.power - 1 ) / 3 + 1;
			ret.alpha = 80 - mod;
		}
		if( mod_PigbearLaser.beamTransparency == 0 ) {
			int red = ( ret.color & 0xFF0000 ) >> 16;
			int green = ( ret.color & 0xFF00 ) >> 8;
			int blue = ret.color & 0xFF;
			red = ( red * mod_PigbearLaser.colorMultiplier ) >> 8;
			green = ( green * mod_PigbearLaser.colorMultiplier ) >> 8;
			blue = ( blue * mod_PigbearLaser.colorMultiplier ) >> 8;
			ret.color = ( red << 16 ) | ( green << 8 ) | blue;
			if( (pos == null || pos.getInvisible()) && (neg == null || neg.getInvisible()) ) {
				ret.power = 0;
			}
		} else {
			
			ret.alpha *= 3f - mod_PigbearLaser.beamTransparency / 100f * 2f;
		}
		return ret;
	}

	static void renderBeamX( Tessellator tessellator, RenderInfo info, float x, float y, float z, float start, float end ) {
		if( info.cap_neg && start > 0.0f && info.end_at_neg )
			start = 0.0f;
		if( info.cap_pos && end > 0.0f && info.end_at_pos )
			end = 0.0f;

		if( info.lens_at_pos && ( end > 0.375f ) )
			end = 0.375f;
		if( info.lens_at_neg && ( start > 0.375f ) )
			start = 0.375f;

		float power = ( 1.0f / 16.0f ) * info.thickness;
		float x0 = x - start;
		float x1 = x + end;
		float y0 = y - power;
		float y1 = y + power;
		float z0 = z - power;
		float z1 = z + power;

		tessellator.startDrawing( 5 );
		tessellator.setColorRGBA_I( info.color, info.alpha );
		//tessellator.setColorOpaque_I( info.color );
		tessellator.setBrightness( 0xF00070 );
		tessellator.addVertex( x0, y1, z0 );
		tessellator.addVertex( x1, y1, z0 );
		tessellator.addVertex( x0, y0, z0 );
		tessellator.addVertex( x1, y0, z0 );
		tessellator.addVertex( x0, y0, z1 );
		tessellator.addVertex( x1, y0, z1 );
		tessellator.addVertex( x0, y1, z1 );
		tessellator.addVertex( x1, y1, z1 );
		tessellator.addVertex( x0, y1, z0 );
		tessellator.addVertex( x1, y1, z0 );
		tessellator.draw();

		if( info.cap_neg && start >= 0.0f ) {
			tessellator.startDrawing( 5 );
			tessellator.setColorRGBA_I( info.color, info.alpha );
			//tessellator.setColorOpaque_I( info.color );
			tessellator.setBrightness( brightness );
			tessellator.addVertex( x0, y1, z1 );
			tessellator.addVertex( x0, y1, z0 );
			tessellator.addVertex( x0, y0, z1 );
			tessellator.addVertex( x0, y0, z0 );
			tessellator.draw();
		}
		if( info.cap_pos && end >= 0.0f ) {
			tessellator.startDrawing( 5 );
			tessellator.setColorRGBA_I( info.color, info.alpha );
			//tessellator.setColorOpaque_I( info.color );
			tessellator.setBrightness( brightness );
			tessellator.addVertex( x1, y1, z0 );
			tessellator.addVertex( x1, y1, z1 );
			tessellator.addVertex( x1, y0, z0 );
			tessellator.addVertex( x1, y0, z1 );
			tessellator.draw();
		}
	}

	static void renderBeamY( Tessellator tessellator, RenderInfo info, float x, float y, float z, float start, float end ) {
		if( info.cap_neg && start > 0.0f && info.end_at_neg )
			start = 0.0f;
		if( info.cap_pos && end > 0.0f && info.end_at_pos )
			end = 0.0f;

		if( info.lens_at_pos && ( end > 0.375f ) )
			end = 0.375f;
		if( info.lens_at_neg && ( start > 0.375f ) )
			start = 0.375f;

		float power = ( 1.0f / 16.0f ) * info.thickness;
		float x0 = x - power;
		float x1 = x + power;
		float y0 = y - start;
		float y1 = y + end;
		float z0 = z - power;
		float z1 = z + power;

		tessellator.startDrawing( 5 );
		tessellator.setColorRGBA_I( info.color, info.alpha );
		//tessellator.setColorOpaque_I( info.color );
		tessellator.setBrightness( 0xF00070 );
		
		tessellator.addVertex( x0, y0, z0 );
		tessellator.addVertex( x0, y1, z0 );
		tessellator.addVertex( x1, y0, z0 );
		tessellator.addVertex( x1, y1, z0 );


		tessellator.addVertex( x1, y0, z1 );
		
		tessellator.addVertex( x1, y1, z1 );
		
		tessellator.addVertex( x0, y0, z1 );
		
		tessellator.addVertex( x0, y1, z1 );
		
		tessellator.addVertex( x0, y0, z0 );
		
		tessellator.addVertex( x0, y1, z0 );
		
		tessellator.draw();

		if( info.cap_neg && start >= 0.0f ) {
			tessellator.startDrawing( 5 );
			tessellator.setColorRGBA_I( info.color, info.alpha );
			//tessellator.setColorOpaque_I( info.color );
			tessellator.setBrightness( brightness );
			tessellator.addVertex( x1, y0, z1 );
			tessellator.addVertex( x0, y0, z1 );
			tessellator.addVertex( x1, y0, z0 );
			tessellator.addVertex( x0, y0, z0 );
			tessellator.draw();
		}

		if( info.cap_pos && end >= 0.0f ) {
			tessellator.startDrawing( 5 );
			tessellator.setColorRGBA_I( info.color, info.alpha );
			//tessellator.setColorOpaque_I( info.color );
			tessellator.setBrightness( brightness );
			tessellator.addVertex( x0, y1, z1 );
			tessellator.addVertex( x1, y1, z1 );
			tessellator.addVertex( x0, y1, z0 );
			tessellator.addVertex( x1, y1, z0 );
			tessellator.draw();
		}
	}

	static void renderBeamZ( Tessellator tessellator, RenderInfo info, float x, float y, float z, float start, float end ) {
		if( info.cap_neg && start > 0.0f && info.end_at_neg )
			start = 0.0f;
		if( info.cap_pos && end > 0.0f && info.end_at_pos )
			end = 0.0f;

		if( info.lens_at_pos && ( end > 0.375f ) )
			end = 0.375f;
		if( info.lens_at_neg && ( start > 0.375f ) )
			start = 0.375f;

		float power = ( 1.0f / 16.0f ) * info.thickness;
		float x0 = x - power;
		float x1 = x + power;
		float y0 = y - power;
		float y1 = y + power;
		float z0 = z - start;
		float z1 = z + end;

		tessellator.startDrawing( 5 );
		//tessellator.setColorOpaque_I( info.color );
		tessellator.setBrightness( 0xF00070 );
		tessellator.setColorRGBA_I( info.color, info.alpha );

		tessellator.addVertex( x0, y1, z0 );
		tessellator.addVertex( x0, y1, z1 );
		tessellator.addVertex( x1, y1, z0 );
		tessellator.addVertex( x1, y1, z1 );

		tessellator.addVertex( x1, y0, z0 );
		tessellator.addVertex( x1, y0, z1 );

		tessellator.addVertex( x0, y0, z0 );
		tessellator.addVertex( x0, y0, z1 );

		tessellator.addVertex( x0, y1, z0 );
		tessellator.addVertex( x0, y1, z1 );
		tessellator.draw();

		if( info.cap_neg && start >= 0.0f ) {
			tessellator.startDrawing( 5 );
			tessellator.setColorRGBA_I( info.color, info.alpha );
			//tessellator.setColorOpaque_I( info.color );
			tessellator.setBrightness( brightness );
			tessellator.addVertex( x0, y0, z0 );
			tessellator.addVertex( x0, y1, z0 );
			tessellator.addVertex( x1, y0, z0 );
			tessellator.addVertex( x1, y1, z0 );
			tessellator.draw();
		}
		if( info.cap_pos && end >= 0.0f ) {
			tessellator.startDrawing( 5 );
			tessellator.setColorRGBA_I( info.color, info.alpha );
			//tessellator.setColorOpaque_I( info.color );
			tessellator.setBrightness( brightness );
			tessellator.addVertex( x0, y1, z1 );
			tessellator.addVertex( x0, y0, z1 );
			tessellator.addVertex( x1, y1, z1 );
			tessellator.addVertex( x1, y0, z1 );
			tessellator.draw();
		}

	}

	private void renderLens( Tessellator tessellator, IBlockAccess world, EntityBeam beam, int x, int y, int z ) {
		float pixel = 0.0625f * 0.0625f;
		float halfUV = pixel * 8;

		push();
		scale( ( float ) ( 1.0 / 16.0 ) );
		translate( -.5f, -.5f, -.5f );
		move( .5f + x, .5f + y, .5f + z );
		scaleUV( pixel, pixel );
		translateUV( -0.0625f * .5f, -0.0625f * .5f );

		Beam b = beam.get( 0 );
		if( b != null && b.isStart( x, y, z ) && b.lens != null ) {
			int tex = b.lens.getFrame();
			float tx = 0.0625f * ( tex & 0xF ) + halfUV;
			float ty = 0.0625f * ( tex >> 4 ) + halfUV;
			if( BlockBeam.renderPass == 1 ) {
				push();
				rotate( Axis.X, 90 );
				renderLens( b.lens.color.toRGB(), Axis.Y, -1 );
				pop();
			} else {
				if( mod_PigbearLaser.lensTransparency == 0 ) {
					push();
					rotate( Axis.X, 90 );
					renderLens( b.lens.color.multiply( mod_PigbearLaser.colorMultiplier ), Axis.Y, -1 );
					pop();
				}
				push();
				rotate( Axis.X, 90 );
				moveUV( tx, ty );
				renderFrame( Axis.Y );
				pop();
			}
		}

		b = beam.get( 1 );
		if( b != null && b.isStart( x, y, z ) && b.lens != null ) {
			int tex = b.lens.getFrame();
			float tx = 0.0625f * ( tex & 0xF ) + halfUV;
			float ty = 0.0625f * ( tex >> 4 ) + halfUV;
			if( BlockBeam.renderPass == 1 ) {
				push();
				rotate( Axis.X, -90 );
				renderLens( b.lens.color.toRGB(), Axis.Y, 1 );
				pop();
			} else {
				if( mod_PigbearLaser.lensTransparency == 0 ) {
					push();
					rotate( Axis.X, -90 );
					renderLens( b.lens.color.multiply( mod_PigbearLaser.colorMultiplier ), Axis.Y, 1 );
					pop();
				}
				push();
				rotate( Axis.X, -90 );
				moveUV( tx, ty );
				renderFrame( Axis.Y );
				pop();
			}
		}
		b = beam.get( 2 );
		if( b != null && b.isStart( x, y, z ) && b.lens != null ) {
			int tex = b.lens.getFrame();
			float tx = 0.0625f * ( tex & 0xF ) + halfUV;
			float ty = 0.0625f * ( tex >> 4 ) + halfUV;
			if( BlockBeam.renderPass == 1 ) {
				push();
				rotate( Axis.Y, 180 );
				renderLens( b.lens.color.toRGB(), Axis.Z, -1 );
				pop();
			} else {
				if( mod_PigbearLaser.lensTransparency == 0 ) {
					push();
					rotate( Axis.Y, 180 );
					renderLens( b.lens.color.multiply( mod_PigbearLaser.colorMultiplier ), Axis.Z, -1 );
					pop();		
				}
				push();
				rotate( Axis.Y, 180 );
				moveUV( tx, ty );
				renderFrame( Axis.Z );
				pop();
			}
		}
		b = beam.get( 3 );
		if( b != null && b.isStart( x, y, z ) && b.lens != null ) {
			int tex = b.lens.getFrame();
			float tx = 0.0625f * ( tex & 0xF ) + halfUV;
			float ty = 0.0625f * ( tex >> 4 ) + halfUV;
			if( BlockBeam.renderPass == 1 ) {
				push();
				renderLens( b.lens.color.toRGB(), Axis.Z, 1 );
				pop();
			} else {
				if( mod_PigbearLaser.lensTransparency == 0 ) {
					push();
					renderLens( b.lens.color.multiply( mod_PigbearLaser.colorMultiplier ), Axis.Z, 1 );
					pop();
				}
				push();
				moveUV( tx, ty );
				renderFrame( Axis.Z );
				pop();
			}
		}
		b = beam.get( 4 );
		if( b != null && b.isStart( x, y, z ) && b.lens != null ) {
			int tex = b.lens.getFrame();
			float tx = 0.0625f * ( tex & 0xF ) + halfUV;
			float ty = 0.0625f * ( tex >> 4 ) + halfUV;
			if( BlockBeam.renderPass == 1 ) {
				push();
				rotate( Axis.Y, -90 );
				renderLens( b.lens.color.toRGB(), Axis.X, -1 );
				pop();
			} else {
				if( mod_PigbearLaser.lensTransparency == 0 ) {
					push();
					rotate( Axis.Y, -90 );
					renderLens( b.lens.color.multiply( mod_PigbearLaser.colorMultiplier ), Axis.X, -1 );
					pop();
				}
				push();
				moveUV( tx, ty );
				rotate( Axis.Y, -90 );
				renderFrame( Axis.X );
				pop();
			}
		}
		b = beam.get( 5 );
		if( b != null && b.isStart( x, y, z ) && b.lens != null ) {
			int tex = b.lens.getFrame();
			float tx = 0.0625f * ( tex & 0xF ) + halfUV;
			float ty = 0.0625f * ( tex >> 4 ) + halfUV;
			if( BlockBeam.renderPass == 1 ) {
				push();
				rotate( Axis.Y, 90 );
				renderLens( b.lens.color.toRGB(), Axis.X, 1 );
				pop();
			} else {
				if( mod_PigbearLaser.lensTransparency == 0 ) {
					push();
					rotate( Axis.Y, 90 );
					renderLens( b.lens.color.multiply( mod_PigbearLaser.colorMultiplier ), Axis.X, 1 );
					pop();
				}
				push();
				moveUV( tx, ty );
				rotate( Axis.Y, 90 );
				renderFrame( Axis.X );
				pop();
			}
		}
		pop();
	}

	private void renderFrame( Axis axis ) {
		Tessellator.instance.startDrawing( 7 );
		Tessellator.instance.setColorOpaque_I( 0xFFFFFF );
		Tessellator.instance.setBrightness( brightness );
		for( int i = 0; i < 4; i++ ) {
			addWithUV( 1, 5, 2, 5, 0 );
			addWithUV( 1, 11, 2, 11, 0 );
			addWithUV( 1, 11, 0, 11, 2 );
			addWithUV( 1, 5, 0, 5, 2 );

			addWithUV( 3, 10, 2, 11, 0 );
			addWithUV( 3, 6, 2, 5, 0 );
			addWithUV( 3, 6, 0, 5, 2 );
			addWithUV( 3, 10, 0, 11, 2 );

			addWithUV( 1, 11, 2, 1, 11 );
			addWithUV( 1, 5, 2, 1, 5 );
			addWithUV( 3, 6, 2, 3, 6 );
			addWithUV( 3, 10, 2, 3, 10 );

			addWithUV( 5, 1, 2, 0, 0 );
			addWithUV( 1, 5, 2, 5, 0 );
			addWithUV( 1, 5, 0, 5, 2 );
			addWithUV( 5, 1, 0, 0, 2 );

			addWithUV( 3, 6, 2, 5, 0 );
			addWithUV( 6, 3, 2, 0, 0 );
			addWithUV( 6, 3, 0, 0, 2 );
			addWithUV( 3, 6, 0, 5, 2 );

			addWithUV( 3, 6, 2, 0, 0 );
			addWithUV( 1, 5, 2, 0, 2 );
			addWithUV( 5, 1, 2, 5, 2 );
			addWithUV( 6, 3, 2, 5, 0 );

			rotate( axis, 90 );
			rotateUV( 90 );
		}
		Tessellator.instance.draw();
	}

	private void renderLens( int color, Axis axis, int mul ) {
		mul *= 90;
		GL11.glDisable( GL11.GL_TEXTURE_2D );
		Tessellator.instance.startDrawing( GL11.GL_TRIANGLE_FAN );
		if( mod_PigbearLaser.lensTransparency == 0 ) {
			Tessellator.instance.setColorRGBA_I( color, mod_PigbearLaser.colorMultiplier );
		} else {
			Tessellator.instance.setColorRGBA_I( color, ( int )( 0x80 * ( 2f - mod_PigbearLaser.lensTransparency / 100f * 1f ) ) );
		}
		Tessellator.instance.setBrightness( brightness );
		addVertex( 8, 8, 1 );
		for( int i = 0; i < 4; i++ ) {
			addVertex( 3, 6, 1 );
			addVertex( 6, 3, 1 );
			rotate( axis, mul );
		}
		addVertex( 3, 6, 1 );
		Tessellator.instance.draw();
		GL11.glEnable( GL11.GL_TEXTURE_2D );
	}

	@Override
	public void renderInventory( RenderBlocks blocks, Block block, int damage ) {
	}

}

class RenderInfo {

	public int color = 0;
	public float power = 0;
	public int alpha = 0;
	public float thickness = 0;
	public boolean cap_pos = false;
	public boolean cap_neg = false;
	public boolean lens_at_pos = false;
	public boolean lens_at_neg = false;
	public boolean end_at_pos = false;
	public boolean end_at_neg = false;
}
