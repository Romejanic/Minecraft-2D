package romejanic.world.block;

import java.io.InputStream;

import romejanic.engine.SoundEngine;

public class StepSound {
	
	private String material;
	
	public StepSound(String material) {
		
		this.material = material;

		Block.stepSounds.put(Block.stepSounds.size(), this);
		
	}
	
	public void register(SoundEngine engine) {
		
		try {
			
			engine.registerSound(getBreakSound(), new InputStream[] { StepSound.class.getResourceAsStream("/sounds/block/" + material + "_break1.ogg"), StepSound.class.getResourceAsStream("/sounds/block/" + material + "_break2.ogg"), StepSound.class.getResourceAsStream("/sounds/block/" + material + "_break3.ogg"), StepSound.class.getResourceAsStream("/sounds/block/" + material + "_break4.ogg") });
			
		} catch(Exception e) {
			
			System.err.println("Cannot register StepSound!");
			
		}
		
	}
	
	public String getMaterial() {
		
		return material;
		
	}
	
	public String getPlaceSound() {
		
		return "block.place." + getMaterial();
		
	}
	
	public String getBreakSound() {
		
		return "block.break." + getMaterial();
		
	}

}
