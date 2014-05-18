package romejanic.world.block;

import romejanic.world.World;

public class BlockPortal extends Block {

	public BlockPortal(int id) {

		super(id);

	}

	public void blockDestroyed(int x, int y, int meta, World world) {

		world.setBlock(air, x, y);
		world.setBlock(air, x + 1, y);
		world.setBlock(air, x, y + 1);
		world.setBlock(air, x + 1, y + 1);
		world.setBlock(air, x, y + 2);
		world.setBlock(air, x + 1, y + 2);

	}

	public int idPicked() {

		return 0;

	}

}
