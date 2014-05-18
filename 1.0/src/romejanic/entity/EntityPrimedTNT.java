package romejanic.entity;

import org.newdawn.slick.Color;

import romejanic.engine.RenderEngine;
import romejanic.main.Minecraft;
import romejanic.particles.ParticleSmoke;
import romejanic.world.World;
import romejanic.world.block.Block;

public class EntityPrimedTNT extends Entity {

	public float fuse = 250f;
	public float renderScale = 50f;
	
	public EntityPrimedTNT(float x, float y, World world) {
		
		super(x, y, world);
		
	}

	@Override
	public void update() {
		
		//super.update();
		
		--fuse;
		
		Minecraft.instance().renderEngine.particles.add(new ParticleSmoke((posX + 1f) / 2, posY + 1).setR(0f).setG(0f).setB(0f));
		
		if(fuse < 0f) {
			
			world.explode((int)posX, (int)posY, 4);
			
			dead = true;
			
		}
		else if(fuse < 50) {
			
			renderScale += 0.025;
			
		}
		
	}
	
	@Override
	public void render(RenderEngine engine) {
		
		getRenderColor().bind();
		Block.tnt.getTexture(0).bind();
		
		engine.drawBlock("tnt", posX, posY, renderScale);
		
	}

	public Color getRenderColor() {
		
		if(fuse > 50 && fuse < 100) {
			
			return Color.gray;
			
		}
		else if(fuse > 150 && fuse < 200) {
			
			return Color.gray;
			
		}
		else if(fuse > 250 && fuse < 300) {
			
			return Color.gray;
			
		}
		else if(fuse > 350 && fuse < 400) {
			
			return Color.gray;
			
		}
		else if(fuse > 450 && fuse < 500) {
			
			return Color.gray;
			
		}
		else if(fuse > 550 && fuse < 600) {
			
			return Color.gray;
			
		}
		else if(fuse > 650 && fuse < 700) {
			
			return Color.gray;
			
		}
		else if(fuse > 750 && fuse < 800) {
			
			return Color.gray;
			
		}
		else if(fuse > 850 && fuse < 900) {
			
			return Color.gray;
			
		}
		else if(fuse > 950 && fuse < 1000) {
			
			return Color.gray;
			
		}
		
		return Color.white;
		
	}
	
}
