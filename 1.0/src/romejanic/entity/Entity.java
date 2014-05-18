package romejanic.entity;

import romejanic.engine.RenderEngine;
import romejanic.world.World;

public abstract class Entity {

	public float posX;
	public float posY;
	public World world;
	
	public boolean dead = false;
	
	public float moveSpeed = 0.25f;
	
	public Entity(float x, float y, World world) {
		
		this.posX = x;
		this.posY = y;
		this.world = world;
		
	}
	
	public void update() {
		
		if(world.getID((int)posX, ((int)posY) - 1) == 0) {
			
			posY -= moveSpeed;
			
		}
		
	}
	
	public abstract void render(RenderEngine engine);
	
}
