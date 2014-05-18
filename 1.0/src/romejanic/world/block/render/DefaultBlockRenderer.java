package romejanic.world.block.render;

import romejanic.engine.RenderEngine;
import romejanic.main.Minecraft;
import romejanic.world.block.Block;

public class DefaultBlockRenderer extends BlockRenderer {

	public DefaultBlockRenderer(Block block) {

		super(block);

	}

	@Override
	public void render(int x, int y, int meta, RenderEngine engine, boolean mouseOver) {

		if(getBlock().id > 0) {

			try {

				getBlock().getTexture(meta).bind();

			} catch(Exception e) {

				Minecraft.printStackTrace(e, "Cannot bind block texture!");

				engine.missingTexture.bind();

			}
			
			engine.drawBlock(getBlock().toString(), x, y, 50);
			
			if(mouseOver) {
				
				org.newdawn.slick.Color.gray.bind();
				engine.drawBlock(getBlock().toString(), x, y, 50);
				org.newdawn.slick.Color.white.bind();
					
			}

		}

	}

}
