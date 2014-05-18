package romejanic.particles;

import org.lwjgl.opengl.GL11;

import romejanic.engine.RenderEngine;

public class ParticleRain extends Particle {

	public ParticleRain(float posX, float posY, float velocityX, float velocityY) {
		
		super(posX, posY, velocityX, velocityY);
		
		this.reverse = true;

	}

	@Override
	public void render(RenderEngine engine) {
		
		GL11.glColor3f(255f, 255f, 255f);

		engine.getTexture("rain_particle", ParticleRain.class.getResourceAsStream("/textures/particles/rain.png")).bind();
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(posX, posY);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(posX + 30, posY);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(posX + 30, posY + 100);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(posX, posY + 100);
		GL11.glEnd();
		
	}

}
