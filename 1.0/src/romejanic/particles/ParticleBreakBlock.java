package romejanic.particles;

import org.lwjgl.opengl.GL11;

import romejanic.engine.RenderEngine;
import romejanic.main.Minecraft;
import romejanic.world.block.Block;

public class ParticleBreakBlock extends Particle {

	public int blockID = 0;
	public int blockMeta = 0;

	public ParticleBreakBlock(float posX, float posY, int blockID, int blockMeta) {

		super(posX, posY, Minecraft.random.nextFloat() % 1f, 2.5f);

		if(Minecraft.random.nextInt(1) == 0) {
			
			this.reverse = true;
			
		}
		
		this.maxAge = 500;
		this.blockID = blockID;
		this.blockMeta = blockMeta;

	}

	@Override
	public void render(RenderEngine engine) {
		
		if(blockID > 0) {

			Block.blocks.get(blockID).getTexture(blockMeta).bind();

			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(posX, posY);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(posX + 10, posY);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(posX + 10, posY + 10);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(posX, posY + 10);
			GL11.glEnd();

		}
		else {
			
			destroy();
			
		}

	}

}
