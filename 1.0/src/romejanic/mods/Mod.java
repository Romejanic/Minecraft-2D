package romejanic.mods;

import java.io.InputStream;

import romejanic.engine.RenderEngine;
import romejanic.main.Minecraft;

public abstract class Mod {
	
	private static Mod INSTANCE;
	
	public Mod() {
		
		INSTANCE = this;
		
	}
	
	public String getModName() {
		
		return "Minecraft 2D Mod";
		
	}
	
	public String getModAuthor() {
		
		return "Unknown Author";
		
	}
	
	public String getModVersion() {
		
		return "0.0.0";
		
	}
	
	public abstract void init(Minecraft minecraft, RenderEngine renderEngine);
	
	public void tick(Minecraft minecraft) {
		
		
		
	}
	
	public InputStream getResource(String resource) {
		
		return getClass().getResourceAsStream(resource);
		
	}
	
	public static final Mod instance() {
		
		return INSTANCE;
		
	}

}
