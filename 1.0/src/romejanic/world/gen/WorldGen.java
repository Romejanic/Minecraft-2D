package romejanic.world.gen;

import java.util.ArrayList;

import romejanic.world.World;

public abstract class WorldGen {

	public static final ArrayList<WorldGen> worldGenerators = new ArrayList<WorldGen>();

	public World world;

	public WorldGen(World world) {

		this.world = world;

	}

	public abstract int[] genLayers();

	public abstract void generate(int x, int y, int layer);

	public static final void registerWorldGenerator(WorldGen gen) {

		if(worldGenerators.contains(gen)) {

			System.err.println("Tried to register " + gen + ", but it already exists!");

			return;

		}

		worldGenerators.add(gen);
		
		System.out.println("Registered world generator: " + gen.getClass().getSimpleName());

	}

	public static final WorldGen[] getWorldGenerators(World world) {

		return ((WorldGen[]) worldGenerators.toArray(new WorldGen[0]));

	}

}
