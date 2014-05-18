package romejanic.world.structure;

import romejanic.world.World;

public abstract class Structure {
	
	public abstract int[] idsAllowed();
	
	public abstract void onBlockPlaced(int x, int y, int id, int meta, World world);

}
