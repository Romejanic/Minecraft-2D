package romejanic.world.block;

import org.newdawn.slick.opengl.Texture;

import romejanic.main.Minecraft;

public class BlockMineral extends Block {

	public String[] oreName = new String[] { "Diamond", "Emerald", "Gold", "Iron", "Lapis", "Redstone" };
	
	public BlockMineral(int id) {
		
		super(id);
		
	}
	
	public Texture getTexture(int meta) {
		
		if(oreName[meta] != null) {
			
			return Minecraft.instance().renderEngine.getTexture(getCodeName() + "_" + oreName[meta].toLowerCase(), BlockMineral.class.getResourceAsStream("/textures/blocks/" + oreName[meta].toLowerCase() + "_block.png"));
			
		}
		
		return Block.stone.getTexture(meta);
		
	}
	
	public String getDisplayName(int meta) {
		
		return oreName[meta] != null ? oreName[meta] + " Block" : "Mineral Block";
		
	}

}
