package romejanic.world.block;

import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

import org.newdawn.slick.opengl.Texture;

import romejanic.main.Minecraft;
import romejanic.world.World;
import romejanic.world.block.render.BlockRenderer;

public class Block {

	public static SortedMap<Integer, Block> blocks = new TreeMap<Integer, Block>();
	public static SortedMap<Integer, StepSound> stepSounds = new TreeMap<Integer, StepSound>();
	public static int blockCount = 0;
	public static ArrayList<Integer> allowedIDs = new ArrayList<Integer>();

	public static final StepSound stepSoundStone = new StepSound("stone");
	public static final StepSound stepSoundDirt = new StepSound("dirt");
	public static final StepSound stepSoundGrass = new StepSound("grass");
	public static final StepSound stepSoundObsidian = new StepSound("obsidian");

	public static final Block air = ((Block) null);
	public static final Block stone = new Block(1).setCodeName("stone").setDisplayName("Stone");
	public static final Block dirt = new BlockGrass(2).setStepSound(stepSoundDirt).setCodeName("dirt").setDisplayName("Dirt");
	public static final Block grass = new BlockGrass(3).setStepSound(stepSoundGrass).setCodeName("grass").setDisplayName("Grass");
	public static final Block ore = new BlockOre(4).setCodeName("ore").setMaxMeta(5);
	public static final Block bedrock = new Block(5).setCanWaterDripThrough(false).setCodeName("bedrock").setDisplayName("Bedrock");
	public static final Block mineral = new BlockMineral(6).setCodeName("mineral").setMaxMeta(5);
	public static final Block enchantment = new BlockEnchantmentTable(7).setStepSound(stepSoundObsidian).setCodeName("enchantment_idle").setDisplayName("Enchantment Table");
	public static final Block water = new BlockFluid(8, 50).setCodeName("water").setDisplayName("Water");
	public static final Block tnt = new BlockTNT(9).setCodeName("tnt").setDisplayName("TNT");
	public static final Block obsidian = new Block(10).setCodeName("obsidian").setDisplayName("Obsidian");
	public static final Block fire = new Block(11).setCodeName("fire").setDisplayName("Fire");
	public static final Block portal = new BlockPortal(12).setNotAllowed().setCodeName("portal").setDisplayName("Portal");
	public static final Block lava = new BlockFluid(13, 100).setCodeName("lava").setDisplayName("Lava");
	public static final Block sand = new BlockSand(14).setCodeName("sand").setDisplayName("Sand");
	
	public int id;
	public StepSound stepSound = stepSoundStone;

	private String codeName;
	private String displayName;
	private int maxMeta = 0;
	private Texture texture = null;
	private boolean waterDripThrough = true;

	public Block(int id) {

		this.id = id;

		if(blocks.get(id) != null) {

			System.err.println("Tried to register " + this + "in slot " + id + ", but it's already taken by " + blocks.get(id));

			return;

		}

		blocks.put(id, this);

		blockCount++;

		allowedIDs.add(id);

	}

	public int idPicked() {

		return id;

	}

	public Block setNotAllowed() {

		allowedIDs.remove(id - 1);

		return this;

	}

	public void update(int x, int y, int meta, World world) {

		// water drip, coming in a future version!!
		/*if((world.getID(x, y + 1) == water.id || world.getID(x, y + 2) == water.id || world.getID(x, y + 3) == water.id) && world.getID(x, y - 1) == 0 && canWaterDripThrough()) {

			if(Minecraft.random.nextInt(49) == 0) {

				for(float f = 0f; f < 1f; f += 0.25f) {

					Minecraft.instance().renderEngine.particles.add(new ParticleBreakBlock((float)x + (f * 10), (float)y, water.id, 0));

				}

			}

		}*/

	}

	public boolean updates() {

		return false;

	}

	public boolean canBlockBeActivated() {

		return false;

	}

	public boolean createBreakParticles() {

		return true;

	}

	public void blockDestroyed(int x, int y, int meta, World world) {}

	public void blockActivated(int x, int y, int meta, World world) {}

	public String getBlockTooltip(int meta) {

		return null;

	}

	public boolean canBeReplaced() {

		return false;

	}

	public boolean canWaterDripThrough() {

		return waterDripThrough;

	}

	public Block setTexture(Texture texture) {

		this.texture = texture;

		return this;

	}

	public Block setStepSound(StepSound stepSound) {

		this.stepSound = stepSound;

		return this;

	}

	public Block setMaxMeta(int maxMeta) {

		this.maxMeta = maxMeta;

		return this;

	}

	public Block setCodeName(String codeName) {

		this.codeName = codeName;

		return this;

	}

	public Block setDisplayName(String displayName) {

		this.displayName = displayName;

		return this;

	}

	public Block setCanWaterDripThrough(boolean waterDripThrough) {

		this.waterDripThrough = waterDripThrough;

		return this;

	}

	public int getMaxMeta() {

		return maxMeta;

	}

	public Texture getTexture(int meta) {

		return texture == null ? texture = Minecraft.instance().renderEngine.getTexture(getCodeName(), Block.class.getResourceAsStream("/textures/blocks/" + getCodeName() + ".png")) : texture;

	}

	public String getCodeName() {

		return codeName;

	}

	public String getDisplayName(int meta) {

		return displayName != null ? displayName : getCodeName();

	}

	public final boolean hasCustomRenderer() {

		return getCustomRenderer() != null;

	}

	public BlockRenderer getCustomRenderer() {

		return null;

	}

	public static Block randomBlock() {

		Block result = blocks.get(Minecraft.random.nextInt(blockCount));

		if(result != null) {

			return result;

		}

		return air;

	}

}
