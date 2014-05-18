package romejanic.world.block;

import org.newdawn.slick.opengl.Texture;

import romejanic.main.Minecraft;

public class BlockOre extends Block {

	public String[] oreName = new String[] { "diamond", "emerald", "gold", "iron", "lapis", "redstone" };
	
	public BlockOre(int id) {
		
		super(id);
		
	}
	
	@Override
	public String getDisplayName(int meta) {
		
		return meta < oreName.length ? oreName[meta] + " Ore" : "Ore";
		
	}
	
	@Override
	public Texture getTexture(int meta) {
		
		if(oreName[meta] != null) {
			
			return Minecraft.instance().renderEngine.getTexture(getCodeName() + "_" + oreName[meta], BlockOre.class.getResourceAsStream("/textures/blocks/" + oreName[meta] + "_ore.png"));
			
		}
		
		return Block.stone.getTexture(meta);
		
	}

}
