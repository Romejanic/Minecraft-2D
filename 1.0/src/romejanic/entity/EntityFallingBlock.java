package romejanic.entity;

import romejanic.engine.RenderEngine;
import romejanic.world.World;
import romejanic.world.block.Block;

public class EntityFallingBlock extends Entity {

	public int id;
	public int meta;
	
	public EntityFallingBlock(float x, float y, World world, int id, int meta) {
		
		super(x, y, world);
		
		this.id = id;
		this.meta = meta;
		
		this.moveSpeed = 0.05f;
		
	}

	@Override
	public void update() {
		
		if(world.getID((int)posX, ((int)posY) - 1) == 0) {
			
			posY -= moveSpeed;
			
		}
		else {
			
			world.setBlock(Block.blocks.get(id), meta, (int)posX, (int)posY);
			
		}
		
	}
	
	@Override
	public void render(RenderEngine engine) {
		
		Block.blocks.get(id).getTexture(meta).bind();
		
		engine.drawBlock(Block.blocks.get(id).toString(), posX, posY, 50);

	}

}
