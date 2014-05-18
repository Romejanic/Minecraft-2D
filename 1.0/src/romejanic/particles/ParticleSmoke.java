package romejanic.particles;

import org.lwjgl.opengl.GL11;

import romejanic.engine.RenderEngine;

public class ParticleSmoke extends Particle {

	public float r = 255f, g = 255f, b = 255f;
	
	public ParticleSmoke(float posX, float posY) {
		
		super(posX, posY, 0f, 0.25f);
		
	}
	
	public ParticleSmoke setR(float r) {
		
		this.r = r > 255f ? 255f : r < 0f ? 0f : r;
		
		return this;
		
	}
	
	public ParticleSmoke setG(float g) {
		
		this.g = g > 255f ? 255f : g < 0f ? 0f : g;
		
		return this;
		
	}
	
	public ParticleSmoke setB(float b) {
		
		this.b = b > 255f ? 255f : b < 0f ? 0f : b;
		
		return this;
		
	}

	@Override
	public void render(RenderEngine engine) {
		
		GL11.glColor3f(r, g, b);

		engine.getTexture("particle_smoke", ParticleSmoke.class.getResourceAsStream("/textures/particles.png")).bind();
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex2f(posX, posY);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(posX + 10, posY);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(posX + 10, posY + 10);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(posX, posY + 10);
		GL11.glEnd();
		
		GL11.glColor3f(255f, 255f, 255f);
		
	}

}
