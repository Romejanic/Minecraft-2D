package romejanic.world;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import romejanic.entity.Entity;
import romejanic.main.Minecraft;
import romejanic.particles.ParticleRain;
import romejanic.util.Constants;
import romejanic.world.block.Block;
import romejanic.world.gen.WorldGen;
import romejanic.world.gen.WorldGenOre;
import romejanic.world.structure.*;

public class World {

	public BlockData[] blocks;

	public int width;
	public int height;

	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public ArrayList<Structure> structures = new ArrayList<Structure>();
	
	public boolean raining = false;

	public World(int width, int height) {

		this.width = width;
		this.height = height;

		blocks = new BlockData[width * height];

		WorldGen.registerWorldGenerator(new WorldGenOre(this));

		int index = 0;

		for(int x = 0; x < width; x++) {

			for(int y = 0; y < height; y++) {

				blocks[index] = new BlockData(x, y, Block.air, 0);

				index++;

			}

		}

		generateNewWorld();

		structures.add(new StructurePortal());

	}

	public void generateNewWorld() {

		for(BlockData block : blocks) {

			if(block.getY() > 4 && block.getY() < 8) {

				setBlock(Block.grass, block.getX(), block.getY());

				if(Minecraft.random.nextInt(4) == 0) {

					WorldGen[] generators = WorldGen.getWorldGenerators(this);

					for(WorldGen generator : generators) {

						for(int layer : generator.genLayers()) {

							if(layer == Constants.GEN_LAYER_GRASS) {

								generator.generate(block.getX(), block.getY(), Constants.GEN_LAYER_STONE);

							}

						}

					}

				}

				if(block.getY() < 7) {

					setBlock(Block.dirt, block.getX(), block.getY());

					if(Minecraft.random.nextInt(4) == 0) {

						WorldGen[] generators = WorldGen.getWorldGenerators(this);

						for(WorldGen generator : generators) {

							for(int layer : generator.genLayers()) {

								if(layer == Constants.GEN_LAYER_DIRT) {

									generator.generate(block.getX(), block.getY(), Constants.GEN_LAYER_STONE);

								}

							}

						}

					}

				}

			}
			else if(block.getY() < 5 && block.getY() > 0) {

				setBlock(Block.stone, block.getX(), block.getY());

				if(Minecraft.random.nextInt(4) == 0) {

					WorldGen[] generators = WorldGen.getWorldGenerators(this);

					for(WorldGen generator : generators) {

						for(int layer : generator.genLayers()) {

							if(layer == Constants.GEN_LAYER_STONE) {

								generator.generate(block.getX(), block.getY(), Constants.GEN_LAYER_STONE);

							}

						}

					}

				}

			}
			else if(block.getY() == 0) {

				setBlock(Block.bedrock, block.getX(), block.getY());

				if(Minecraft.random.nextInt(4) == 0) {

					WorldGen[] generators = WorldGen.getWorldGenerators(this);

					for(WorldGen generator : generators) {

						for(int layer : generator.genLayers()) {

							if(layer == Constants.GEN_LAYER_BEDROCK) {

								generator.generate(block.getX(), block.getY(), Constants.GEN_LAYER_STONE);

							}

						}

					}

				}

			}

		}

	}

	public void load(File file) {

		System.out.println("Loading world...");

		try {

			InputStream is = new GZIPInputStream(new FileInputStream(file));
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;
			ArrayList<String> lines = new ArrayList<String>();

			while((line = reader.readLine()) != null) {

				lines.add(line);

			}

			String[] linesArr = ((String[]) lines.toArray(new String[0]));

			for(int i = 0; i < linesArr.length; i++) {

				blocks[i] = BlockData.parse(linesArr[i]);

			}

			reader.close();

			System.out.println("Loaded world from: " + file);

		} catch(Exception e) {

			Minecraft.printStackTrace(e, "Cannot load world!");

			Minecraft.displayAlert("Cannot load world!", "Sorry, but that world cannot be loaded!", Constants.ALERT_ERROR);

			generateNewWorld();

		}

	}

	public void save(File file) {

		System.out.println("Saving world...");

		try {

			if(!file.exists()) {

				file.createNewFile();

			}

			PrintStream writer = new PrintStream(new GZIPOutputStream(new FileOutputStream(file)));

			for(BlockData block : blocks) {

				writer.println(block);

			}

			writer.close();

			System.out.println("Saved world to: " + file);

		} catch(Exception e) {

			Minecraft.printStackTrace(e, "Cannot save world!");

			Minecraft.displayAlert("Cannot save world!", "Sorry, but the world could not be saved!", Constants.ALERT_ERROR);

		}

	}

	public void update() {

		for(BlockData dat : blocks) {

			if(dat != null && dat.getBlock() != null && dat.getBlock().updates()) {

				dat.getBlock().update(dat.getX(), dat.getY(), dat.getMeta(), this);

			}

		}

		for(Entity entity : ((Entity[]) entities.toArray(new Entity[0]))) {

			entity.update();

			if(entity.dead) {

				entities.remove(entity);

			}

		}
		
		if(Minecraft.random.nextInt(5000000 - 1) == 0) {
			
			//raining = !raining;
			
		}
		
		if(raining) {
			
			GL11.glClearColor(255f, 255f, 255f, 100f);
			
			Minecraft.instance().renderEngine.particles.add(new ParticleRain(Mouse.getX(), 0f, 0f, 1f));
			
		}

	}

	public void spawnEntity(Entity entity) {

		if(!entity.dead) {

			entities.add(entity);

		}

	}

	public void explode(int x, int y, int size) {

		for(int posX = x - size; posX < x + size; posX++) {

			for(int posY = y - size; posY < y + size; posY++) {

				setBlock(Block.air, x, y);

			}

		}

	}

	public void setBlock(Block block, int meta, int x, int y) {

		blocks[indexOf(x, y)] = new BlockData(x, y, block, meta);

	}

	public void setBlock(Block block, int x, int y) {

		setBlock(block, 0, x, y);

	}

	public int getMaxY(int x) {

		for(int y = 0; y < height; y++) {

			if(blockAt(x, y) != null) {

				return y;

			}

		}

		return height;

	}

	public int getID(int x, int y) {

		for(BlockData block : blocks) {

			if(block.getX() == x && block.getY() == y) {

				return block.getID();

			}

		}

		return -1;

	}

	public int getMeta(int x, int y) {

		for(BlockData block : blocks) {

			if(block.getX() == x && block.getY() == y) {

				return block.getMeta();

			}

		}

		return 0;

	}

	public boolean canBlockBeReplaced(int x, int y) {

		BlockData data = blockAt(x, y);

		return data != null && data.getBlock() != null && data.getBlock().canBeReplaced();

	}

	public boolean canActivateBlock(int x, int y) {

		BlockData data = blockAt(x, y);

		return data != null && data.getBlock() != null && data.getBlock().canBlockBeActivated();

	}

	public int indexOf(int x, int y) {

		for(int i = 0; i < blocks.length; i++) {

			BlockData data = blocks[i];

			if(data.getX() == x && data.getY() == y) {

				return i;

			}

			continue;

		}

		return -1;

	}

	public BlockData blockAt(int x, int y) {

		return blocks[indexOf(x, y)];

	}

	public static int randomMeta(Block block) {

		return block.getMaxMeta() > 0 ? Minecraft.random.nextInt(block.getMaxMeta()) : 0;

	}

}
