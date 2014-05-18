package romejanic.world.gen;

import romejanic.util.Constants;
import romejanic.world.World;
import romejanic.world.block.Block;

public class WorldGenOre extends WorldGen {

	public WorldGenOre(World world) {
		
		super(world);
	
	}

	@Override
	public int[] genLayers() {

		return new int[] { Constants.GEN_LAYER_STONE };
		
	}

	@Override
	public void generate(int x, int y, int layer) {
		
		if(layer == Constants.GEN_LAYER_STONE) {
			
			int meta = World.randomMeta(Block.ore);
			
			world.setBlock(Block.ore, meta, x, y);
			
		}

	}

}
