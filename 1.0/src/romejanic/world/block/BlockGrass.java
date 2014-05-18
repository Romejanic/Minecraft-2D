package romejanic.world.block;

import romejanic.main.Minecraft;
import romejanic.world.World;

public class BlockGrass extends Block {

	public BlockGrass(int id) {

		super(id);

	}
	
	public void update(int x, int y, int meta, World world) {

		if(this.id == dirt.id) {

			if(Minecraft.random.nextInt(49) == 0) {

				if(world.getID(x, y + 1) == 0) {

					world.setBlock(grass, x, y);

				}

			}

		}
		else {

			if(Minecraft.random.nextInt(49) == 0) {
				
				if(world.getID(x, y + 1) != 0 && world.getID(x, y + 1) != water.id) {
					
					world.setBlock(dirt, x, y);
				
				}
			
			}

		}

	}

	public boolean updates() {

		return true;

	}

}
