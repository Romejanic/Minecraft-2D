package romejanic.world;

import romejanic.world.block.Block;

public class BlockData {

	private int x;
	private int y;
	private int id;
	private int meta;
	private Block block;

	public BlockData(int x, int y, Block block, int meta) {

		this.x = x;
		this.y = y;
		this.id = block != null ? block.id : 0;
		this.meta = meta;
		this.block = block;

	}

	public int getX() {

		return x;

	}

	public int getY() {

		return y;

	}

	public int getID() {

		return id;

	}
	
	public Block getBlock() {
		
		return block;
		
	}
	
	public int getMeta() {

		return meta;

	}

	@Override
	public String toString() {

		return "BlockData[id=" + getID() + ", meta=" + getMeta() + ", x=" + getX() + ", y=" + getY() + "]";

	}

	public static BlockData parse(String input) {

		int id = 0;
		int meta = 0;
		int x = 0;
		int y = 0;

		String[] lines = input.replace("BlockData[", "").replace("]", "").replaceAll(", ", "\n").split("\n");

		for(String line : lines) {

			try {

				String[] parts = line.split("=");

				if(parts[0].equalsIgnoreCase("id")) {
					
					id = Integer.parseInt(parts[1]);
					
				}
				else if(parts[0].equalsIgnoreCase("meta")) {
					
					meta = Integer.parseInt(parts[1]);
					
				}
				else if(parts[0].equalsIgnoreCase("x")) {
					
					x = Integer.parseInt(parts[1]);
					
				}
				else if(parts[0].equalsIgnoreCase("y")) {
					
					y = Integer.parseInt(parts[1]);
					
				}

			} catch(Exception e) {

				continue;

			}

		}

		return new BlockData(x, y, Block.blocks.get(id), meta);

	}

}
