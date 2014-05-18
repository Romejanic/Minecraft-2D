package romejanic.world.block.render;

import romejanic.engine.RenderEngine;
import romejanic.world.block.Block;

public abstract class BlockRenderer {
	
	private Block block;
	
	public BlockRenderer(Block block) {
		
		this.block = block;
		
	}
	
	public Block getBlock() {
		
		return block;
		
	}
	
	public abstract void render(int x, int y, int meta, RenderEngine engine, boolean mouseOver);

}
