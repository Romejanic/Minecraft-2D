package romejanic.world.block;

import romejanic.entity.EntityPrimedTNT;
import romejanic.world.World;

public class BlockTNT extends Block {

	public BlockTNT(int id) {
		
		super(id);
	
	}
	
	@Override
	public void blockDestroyed(int x, int y, int meta, World world) {
		
		world.spawnEntity(new EntityPrimedTNT(x, y, world));
		world.setBlock(air, x, y);
		
	}

	public boolean createBreakParticles() {
		
		return false;
		
	}
	
	@Override
	public String getBlockTooltip(int meta) {
		
		return "Left-Click to explode!";
		
	}
	
}
