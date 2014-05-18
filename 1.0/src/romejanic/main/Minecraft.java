package romejanic.main;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import net.lingala.zip4j.core.ZipFile;

import org.apache.commons.io.FileUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import romejanic.engine.ControlEngine;
import romejanic.engine.Modloader;
import romejanic.engine.RenderEngine;
import romejanic.engine.SoundEngine;
import romejanic.util.Constants;
import romejanic.world.World;
import romejanic.world.block.Block;

public class Minecraft {

	private static final Minecraft INSTANCE = new Minecraft();

	public static final Random random = new Random();
	public static final String currentVersion = "1.0";
	private static final String updateURL = "https://dl.dropboxusercontent.com/s/8yxyhpgt76x99hz/Minecraft2D.txt?dl=1&token_hash=AAG_WtDR8LggUx9sdkWlQlSm8crVtu1jPQEj-xsBjzyqQg";

	public File workingDirectory = new File(new File(System.getProperty("user.home"), "romejanic"), "Minecraft 2D");
	public RenderEngine renderEngine = new RenderEngine();
	public ControlEngine controls = new ControlEngine();
	public SoundEngine sounds = new SoundEngine();
	public Modloader modLoader = new Modloader();
	public World world;
	public File savesFolder = new File(workingDirectory, "saves");
	public File worldSave = null;
	public int indexPlacing = Block.grass.id;
	public int selectedIndex = Block.grass.id - 1;
	public int metaPlacing = 0;
	public boolean isApplet = false;
	public boolean upToDate = true;
	public boolean checkForUpdates = true;
	public boolean checkedForUpdates = false;

	public void init() throws Throwable {

		System.out.println("Setting up...");

		if(!isApplet) {

			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.setResizable(false);
			Display.setTitle("Minecraft 2D by Romejanic (v" + currentVersion + ")");

		}

		Display.create();
		Mouse.create();
		Keyboard.create();

		modLoader.init();
		renderEngine.init();
		controls.init();
		sounds.init();

		world = new World(16, 13);

		checkForUpdates();

		System.out.println("Started up!");

	}

	public void saveWorld() {

		if(world == null) {

			return;

		}

		if(worldSave == null) {

			if(JOptionPane.showConfirmDialog(null, "This world has not been saved yet.\n\nDo you want to save it now?", "Save World?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(savesFolder);

				if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

					worldSave = fc.getSelectedFile();

				}
				else {

					return;

				}

			}
			else {

				return;

			}

		}

		world.save(worldSave);

	}

	public void run() throws Throwable {

		try {

			init();

		} catch (Throwable e) {

			crash(e, "Crash while initializing!");

		}

		while(!Display.isCloseRequested()) {

			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

			renderEngine.update();
			controls.update();
			world.update();
			modLoader.update();

			indexPlacing = Block.allowedIDs.get(selectedIndex);

			if(!checkedForUpdates) {

				checkedForUpdates = true;
				
				if(!upToDate) {

					String theUpdateURL = "http://planetminecraft.com/member/romejanicdev/";

					System.out.println("Update available for download at \"" + theUpdateURL + "\"!");

					if(JOptionPane.showConfirmDialog(null, "A new version of Minecraft 2D is avaliable!\nYou can get it manually at:\n" + theUpdateURL + "\n\nWould you like to get it now?", "Update Avaliable!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

						Desktop.getDesktop().browse(URI.create(theUpdateURL));

					}

				}
				else {

					System.out.println("All up to date!");

				}

			}

			Display.update();
			Display.sync(100);

		}

		exit();

	}

	public void exit(int exitCode) {

		if(exitCode == 0) System.out.println("Exiting safely!");
		else if(exitCode == -1) System.err.print("Exiting because of a crash!");
		else System.err.println("Exiting because of an unknown problem!");

		saveWorld();

		if(Display.isCreated()) Display.destroy();
		if(Keyboard.isCreated()) Keyboard.destroy();
		if(Mouse.isCreated()) Mouse.destroy();
		if(AL.isCreated()) AL.destroy();

		System.exit(exitCode);

	}

	public void exit() {

		exit(0);

	}

	public void crash(Throwable e, String cause) {

		printStackTrace(e, "!!! THE GAME HAS CRASHED !!!", cause);

		displayAlert("Game Crashed", "Sorry, but the game has crashed!\nDon't worry! Your world will be saved before the game closes.\n\nCaused by: " + e.toString(), Constants.ALERT_INFORM);

		exit(-1);

	}

	public void setFullscreen(boolean fullscreen) {

		try {

			Display.setFullscreen(fullscreen);

		} catch (LWJGLException e) {

			printStackTrace(e, "Cannot enter fullscreen");

		}

	}

	public int getWidth() {

		return isApplet ? MinecraftApplet.instance.getWidth() : Display.getWidth();

	}

	public int getHeight() {

		return isApplet ? MinecraftApplet.instance.getHeight() : Display.getHeight();

	}

	public void checkForUpdates() {

		if(!checkForUpdates) {

			return;

		}

		System.out.println("Checking for updates...");

		try {

			InputStream stream = new URL(updateURL).openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line;

			while((line = reader.readLine()) != null) {

				String[] parts = line.split("=");

				if(parts[0].equalsIgnoreCase(currentVersion)) {

					upToDate = parts[1].equalsIgnoreCase("y");

				}

			}



		} catch(Exception e) {

			printStackTrace(e, "Cannot check for updates!");

		}

	}

	public static void displayAlert(String title, String message, int type) {

		boolean fullscreen = Display.isFullscreen();
		boolean locked = Mouse.isGrabbed();

		if(fullscreen) instance().setFullscreen(false);
		if(locked) Mouse.setGrabbed(false);

		JOptionPane.showMessageDialog(null, message, title, type);

		instance().setFullscreen(fullscreen);
		Mouse.setGrabbed(locked);

	}

	public static boolean showConfirm(String title, String message) {

		return JOptionPane.showConfirmDialog(Display.getParent() != null ? Display.getParent() : null, message, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

	}

	public static List<String> getStackTrace(Throwable e, String... causes) {

		ArrayList<String> arr = new ArrayList<String>();

		for(String cause : causes) {

			arr.add("// " + cause);

		}

		arr.add("Caused by: " + e.toString());

		for(StackTraceElement ste : e.getStackTrace()) {

			arr.add("at " + ste.toString());

		}

		return arr;

	}

	public static void printStackTrace(Throwable e, String... causes) {

		Iterator<String> iterator = getStackTrace(e, causes).iterator();

		while(iterator.hasNext()) {

			System.err.println(iterator.next());

		}

	}

	public static void main(String[] args) {

		try {

			if(!instance().workingDirectory.exists()) {

				instance().workingDirectory.mkdirs();

			}

			if(!instance().savesFolder.exists()) {

				instance().savesFolder.mkdirs();

			}

			loadNatives();

			instance().run();

		} catch(Throwable e) {

			instance().crash(e, "Crash while running");

		}

	}

	public static void loadNatives() throws Throwable {

		String location = "/natives/natives_%s.zip";
		String os = System.getProperty("os.name");

		if(os.contains("Windows")) {

			location = String.format(location, "win");

		}
		else if(os.equalsIgnoreCase("Mac OS X")) {

			location = String.format(location, "mac");

		}
		else if(os.contains("Linux") || os.contains("Unix")) {

			location = String.format(location, "lin");

		}
		else if(os.contains("Solaris") || os.contains("Sun")) {

			location = String.format(location, "sol");

		}
		else {

			displayAlert("Unsupported OS", "Sorry, but we think your operating system may be unsupported.\n\nTo ask, send a email to romejanic@gmail.com\n\nYour OS is: " + os, Constants.ALERT_INFORM);

			instance().exit();

		}

		File theFile = new File(instance().workingDirectory, "natives");

		if(!theFile.exists()) {

			File zipFile = new File(instance().workingDirectory, "natives_temp.zip");

			FileUtils.copyInputStreamToFile(Minecraft.class.getResourceAsStream(location), zipFile);

			ZipFile zip = new ZipFile(zipFile);
			zip.extractAll(theFile.getAbsolutePath());

			zipFile.delete();

		}

		System.setProperty("org.lwjgl.librarypath", theFile.getAbsolutePath());
		System.setProperty("net.java.games.input.librarypath", theFile.getAbsolutePath());

	}

	public static Minecraft instance() {

		return INSTANCE;

	}

}
