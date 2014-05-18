package romejanic.world.block.render;

import org.newdawn.slick.opengl.Texture;

import romejanic.engine.RenderEngine;
import romejanic.main.Minecraft;
import romejanic.world.block.Block;

public class EnchantmentTableRenderer extends BlockRenderer {

	public EnchantmentTableRenderer() {
		
		super(Block.enchantment);
		
	}

	@Override
	public void render(int x, int y, int meta, RenderEngine engine, boolean mouseOver) {
		
		if(Minecraft.instance().world.getID(x, y + 1) == Block.water.id) {
			
			engine.blockRenderers.get(Block.water).render(x, y, meta, engine, false);
			
		}
		
		Texture texture = engine.getTexture("enchantment_idle", EnchantmentTableRenderer.class.getResourceAsStream("/textures/blocks/enchantment_idle.png"));

		if(mouseOver) {
			
			texture = engine.getTexture("enchantment_active", EnchantmentTableRenderer.class.getResourceAsStream("/textures/blocks/enchantment_active.png"));
			
		}
		
		texture.bind();
		
		engine.drawBlock(getBlock().toString(), x, y, 50);
		
	}

}
