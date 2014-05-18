package romejanic.world.block;

import romejanic.world.block.render.BlockRenderer;
import romejanic.world.block.render.EnchantmentTableRenderer;

public class BlockEnchantmentTable extends Block {

	public BlockEnchantmentTable(int id) {
		
		super(id);
	
	}
	
	@Override
	public BlockRenderer getCustomRenderer() {
		
		return new EnchantmentTableRenderer();
		
	}

}
