package romejanic.world.structure;

import romejanic.world.World;
import romejanic.world.block.Block;

public class StructurePortal extends Structure {

	@Override
	public void onBlockPlaced(int x, int y, int id, int meta, World world) {
		
		if(id == Block.fire.id) {
			
			if(world.getID(x - 1, y) == Block.obsidian.id && 
					world.getID(x - 1, y + 1) == Block.obsidian.id &&
					world.getID(x - 1, y + 2) == Block.obsidian.id &&
					world.getID(x, y - 1) == Block.obsidian.id &&
					world.getID(x + 1, y - 1) == Block.obsidian.id &&
					world.getID(x + 2, y) == Block.obsidian.id &&
					world.getID(x + 2, y + 1) == Block.obsidian.id &&
					world.getID(x + 2, y + 2) == Block.obsidian.id &&
					world.getID(x, y + 3) == Block.obsidian.id &&
					world.getID(x + 1, y + 3) == Block.obsidian.id) {
				
				world.setBlock(Block.portal, x, y);
				world.setBlock(Block.portal, x + 1, y);
				world.setBlock(Block.portal, x, y + 1);
				world.setBlock(Block.portal, x + 1, y + 1);
				world.setBlock(Block.portal, x, y + 2);
				world.setBlock(Block.portal, x + 1, y + 2);
				
			}
			
		}
		
	}

	@Override
	public int[] idsAllowed() {
		
		return new int[] { Block.fire.id };
		
	}

}
