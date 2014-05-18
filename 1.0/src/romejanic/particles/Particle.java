package romejanic.particles;

import romejanic.engine.RenderEngine;

public abstract class Particle {

	public int age;
	public int maxAge = 50;
	public int width;
	public int height;
	public float posX = 0f;
	public float posY = 0f;
	public float velocityX = 0f;
	public float velocityY = 0f;
	public boolean reverse = false;
	public boolean dead = false;
	
	public Particle(float posX, float posY, float velocityX, float velocityY) {
	
		this.posX = posX;
		this.posY = posY;
		this.velocityX = velocityX;
		this.velocityY = velocityY > 0.0f ? velocityY : 0.25f;
		
	}
	
	public void update() {
		
		if(reverse) {
			
			posX -= velocityX;
			posY -= velocityY;
			
		}
		else {
			
			posX += velocityX;
			posY += velocityY;
			
		}
		
		age++;
		
		if(age >= maxAge) {
			
			destroy();
			
		}
		
	}
	
	public void destroy() {
		
		dead = true;
		
	}
	
	public abstract void render(RenderEngine engine);
	
}
