package romejanic.world.block;

import romejanic.entity.EntityFallingBlock;
import romejanic.world.World;

public class BlockSand extends Block {

	public BlockSand(int id) {
		
		super(id);
		
	}


	public void update(int x, int y, int meta, World world) {

		if(world.getID(x, y - 1) == 0) {
			
			world.setBlock(air, x, y);
			
			world.spawnEntity(new EntityFallingBlock(x, y, world, id, meta));
			
		}
		
	}

	public boolean updates() {

		return true;

	}
	
}
