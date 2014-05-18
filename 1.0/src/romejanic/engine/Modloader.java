package romejanic.engine;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import romejanic.main.Minecraft;
import romejanic.mods.Mod;

public class Modloader implements Engine, FileFilter {

	public File modsFolder;
	public ArrayList<Mod> mods = new ArrayList<Mod>();

	@Override
	public void init() {

		modsFolder = new File(Minecraft.instance().workingDirectory, "mods");
		
		if(!modsFolder.exists()) {

			modsFolder.mkdirs();

		}

		addModsFromFolder(modsFolder);
		
		System.out.println("Loaded " + mods.size() + " mods");

	}

	@SuppressWarnings("resource")	
	public void addModsFromFolder(File folder) {

		System.out.println("Loading mods from " + folder + "...");
		
		for(File file : folder.listFiles(this)) {

			if(file.isFile()) {

				try {

					JarFile jarFile = new JarFile(file);
					Enumeration<JarEntry> entries = jarFile.entries();
					URLClassLoader loader = new URLClassLoader(new URL[] { new URL("jar:file:" + file + "!/") });

					while(entries.hasMoreElements()) {
						
						JarEntry entry = entries.nextElement();
						
						if(entry.isDirectory() || !entry.getName().endsWith(".class")) {
							
							continue;
							
						}
						
						@SuppressWarnings("rawtypes")
						Class theClass = loader.loadClass(entry.getName().substring(0, entry.getName().length() - 6).replace('/', '.'));
						
						if(theClass.getSuperclass() == Mod.class) {
							
							Mod mod = (Mod)theClass.newInstance();
							
							mods.add(mod);
							
							mods.get(mods.indexOf(mod)).init(Minecraft.instance(), Minecraft.instance().renderEngine);
							
							System.out.println("Loaded mod: " + mod);
							
						}
						
					}
					
				} catch(Exception e) {

					Minecraft.printStackTrace(e, "Cannot load mod!");
					
					continue;

				}

			}
			else {

				addModsFromFolder(file);

			}

		}

	}

	@Override
	public void update() {
		
		Iterator<Mod> iterator = mods.iterator();
		
		while(iterator.hasNext()) {
			
			iterator.next().tick(Minecraft.instance());
			
		}

	}

	@Override
	public boolean accept(File arg0) {

		return arg0.getName().endsWith(".jar");

	}

}
