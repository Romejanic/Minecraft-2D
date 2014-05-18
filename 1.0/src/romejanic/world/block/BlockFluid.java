package romejanic.world.block;

import romejanic.main.Minecraft;
import romejanic.world.World;

public class BlockFluid extends Block {

	public int moveChance;
	
	public BlockFluid(int id, int moveChance) {

		super(id);
		
		this.moveChance = moveChance - 1;

	}
	
	public boolean canBeReplaced() {
		
		return true;
		
	}

	public void update(int x, int y, int meta, World world) {

		if(Minecraft.random.nextInt(moveChance) == 0) {

			if(x > -1 && y > -1 && x < world.width) {

				if(world.getID(x + 1, y) == 0) {

					world.setBlock(this, x + 1, y);

				}
				else if(world.getID(x - 1, y) == 0) {

					world.setBlock(this, x - 1, y);

				}
				else if(world.getID(x, y - 1) == 0) {

					world.setBlock(this, x, y - 1);

				}

			}

		}

	}

	public boolean updates() {

		return true;

	}

}
