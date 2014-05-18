package romejanic.engine;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.Color;

import romejanic.entity.Entity;
import romejanic.main.Minecraft;
import romejanic.particles.Particle;
import romejanic.world.BlockData;
import romejanic.world.block.Block;
import romejanic.world.block.render.BlockRenderer;
import romejanic.world.block.render.DefaultBlockRenderer;

public class RenderEngine implements Engine {

	private Map<String, Texture> textures = new HashMap<String, Texture>();
	public Texture missingTexture;
	public UnicodeFont fontBlack;
	public UnicodeFont fontWhite;
	public Map<Block, BlockRenderer> blockRenderers = new HashMap<Block, BlockRenderer>();
	public ArrayList<Particle> particles = new ArrayList<Particle>();
	
	public float cameraPosX = 0;
	public float cameraPosY = 0;

	@SuppressWarnings("unchecked")
	@Override
	public void init() {

		System.out.println("Setting up OpenGL...");

		GL11.glEnable(3553);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		GL11.glMatrixMode(5888);
		GL11.glMatrixMode(5889);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, 800, 0.0D, 600, 1.0D, -1.0D);
		GL11.glMatrixMode(5888);

		GL11.glClearColor(255f, 255f, 255f, 255f);
		
		missingTexture = getTexture("missing", RenderEngine.class.getResourceAsStream("/textures/missing.png"));

		try {
			
			fontBlack = new UnicodeFont("misc/minecraft.ttf", 10, true, false);
			fontBlack.addAsciiGlyphs();
			fontBlack.addGlyphs(400, 600);
			fontBlack.getEffects().add(new ColorEffect(java.awt.Color.black));
			fontBlack.loadGlyphs();
			fontWhite = new UnicodeFont("misc/minecraft.ttf", 10, true, false);
			fontWhite.addAsciiGlyphs();
			fontWhite.addGlyphs(400, 600);
			fontWhite.getEffects().add(new ColorEffect(java.awt.Color.white));
			fontWhite.loadGlyphs();

		} catch(Exception e) {

			Minecraft.instance().crash(e, "Crash while initializing!");

		}

		for(Block block : Block.blocks.values()) {
			
			blockRenderers.put(block, block.hasCustomRenderer() ? block.getCustomRenderer() : new DefaultBlockRenderer(block));
			
		}
		
	}

	@Override
	public void update() {

		for(BlockData block : Minecraft.instance().world.blocks) {
			
			if(blockRenderers.containsKey(block.getBlock())) {
				
				blockRenderers.get(block.getBlock()).render(block.getX(), block.getY(), block.getMeta(), this, ControlEngine.getMouseX() == block.getX() && ControlEngine.getMouseY() == block.getY());
				
			}

		}
		
		for(Entity entity : ((Entity[]) Minecraft.instance().world.entities.toArray(new Entity[0]))) {
			
			entity.render(this);
			
		}
		
		String mouseX = "Mouse X: " + ControlEngine.getMouseX();
		String mouseY = "Mouse Y: " + ControlEngine.getMouseY();
		BlockData hoverBlock = Minecraft.instance().world.blockAt(ControlEngine.getMouseX(), ControlEngine.getMouseY());
		
		fontBlack.drawString(10, Display.getHeight() - 10, mouseX, Color.black);
		fontBlack.drawString(10, Display.getHeight() - 10 - fontBlack.getHeight(mouseX), mouseY, Color.black);

		String selectedBlock = "error";

		try {

			selectedBlock = "Selected block: " + Block.blocks.get(Minecraft.instance().indexPlacing).getDisplayName(Minecraft.instance().metaPlacing) + " (ID: " + Minecraft.instance().indexPlacing + ", Meta: " + Minecraft.instance().metaPlacing + ")";

		} catch(Exception e) {

			selectedBlock = "error: " + e.toString();

		}

		fontBlack.drawString(10, Display.getHeight() - 10 - fontBlack.getHeight(mouseX) - fontBlack.getHeight(mouseY), selectedBlock, Color.black);

		if(hoverBlock != null) {

			String currentBlock = "Hovering over: " + (hoverBlock.getBlock() != null ? hoverBlock.getBlock().getDisplayName(hoverBlock.getMeta()) : "Air") + " (ID: " + hoverBlock.getID() + ", Meta: " + hoverBlock.getMeta() + ")";
			fontBlack.drawString(10, Display.getHeight() - 10 - fontBlack.getHeight(mouseX) - fontBlack.getHeight(mouseY) - fontBlack.getHeight(selectedBlock), currentBlock, Color.black);

		}
		
		Color.white.bind();
		
		for(Particle particle : ((Particle[]) particles.toArray(new Particle[0]))) {
			
			particle.update();
			particle.render(this);
			
			if(particle.dead) {
				
				particles.remove(particles.indexOf(particle));
				
			}
			
		}
		
		if(Block.blocks.get(Minecraft.instance().indexPlacing) != null) blockRenderers.get(Block.blocks.get(Minecraft.instance().indexPlacing)).render(15, 11, Minecraft.instance().metaPlacing, this, false);

		if(hoverBlock.getID() > 0) {
			
			if(hoverBlock.getBlock().getBlockTooltip(hoverBlock.getMeta()) != null) {
				
				drawStringNextToMouse(hoverBlock.getBlock().getBlockTooltip(hoverBlock.getMeta()));
				
			}
			
		}
		
	}
	
	public void drawStringNextToMouse(String str) {
		
		float startX = Mouse.getX();
		float startY = Mouse.getY();
		float endX = startX + fontWhite.getWidth(str);
		float endY = startY + fontWhite.getHeight(str);
		
		if(endX > Display.getWidth()) {
			
			startX = Mouse.getX() - fontWhite.getWidth(str);
			endX = Mouse.getX();
			
		}
		
		Color.black.bind();
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(startX, startY);
		GL11.glVertex2f(startX, endY);
		GL11.glVertex2f(endX, endY);
		GL11.glVertex2f(endX, startY);
		GL11.glEnd();
		
		fontWhite.drawString(startX, startY, str);
		
	}

	public void drawBlock(String blockName, float posX, float posY) {

		drawBlock(blockName, posX, posY, 32);

	}

	public void drawBlock(String blockName, float posX, float posY, float scale) {

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(posX * scale - cameraPosX, posY * scale - cameraPosY);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(posX * scale + scale - cameraPosX, posY * scale - cameraPosY);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(posX * scale + scale - cameraPosX, posY * scale + scale - cameraPosY);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(posX * scale - cameraPosX, posY * scale + scale - cameraPosY);
		GL11.glEnd();

	}

	public void drawTexturedRect(float x, float y, float width, float height, Texture texture) {

		texture.bind();

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(x, y);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(x + width, y);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(x + width, y + height);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(x, y + height);
		GL11.glEnd();

	}

	public Texture getTexture(String name, InputStream location) {

		if(textures.containsKey(name)) {

			return textures.get(name);

		}

		Texture texture = null;

		try {

			texture = TextureLoader.getTexture("PNG", location);

			System.out.println("Loaded texture: " + name + " (GLID: " + texture.getTextureID() + ")");

		} catch(Exception e) {

			texture = null;

		}

		if(texture != null) {

			textures.put(name, texture);

			return texture;

		}
		else {

			return missingTexture;

		}

	}

}
