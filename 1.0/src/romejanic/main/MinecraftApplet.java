package romejanic.main;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;

import javax.swing.JLabel;

import org.lwjgl.opengl.Display;

public class MinecraftApplet extends Applet {

	private static final long serialVersionUID = -5482633827569749653L;
	public static MinecraftApplet instance;

	public Canvas parent;

	public MinecraftApplet() {

		super();

		instance = this;
		setSize(800, 600);

		setLayout(new BorderLayout(0, 0));

		try {

			Minecraft.loadNatives();

			parent = new Canvas() {

				private static final long serialVersionUID = 4297655296275963141L;

				public final void addNotify() {

					super.addNotify();

					start();
					create();

				}
				public final void removeNotify() {

					destroy();

					super.removeNotify();

				}

			};
			parent.setSize(800, 600);
			add(parent, BorderLayout.CENTER);
			parent.setFocusable(true);
			parent.requestFocus();
			parent.setIgnoreRepaint(true);
			parent.setVisible(true);

			Display.setParent(parent);

		} catch (Throwable e) {

			remove(parent);

			Minecraft.printStackTrace(e, "Cannot initiate applet!");

			add(new JLabel("Cannot initiate OpenGL! Sorry :("), BorderLayout.CENTER);

			setVisible(true);
			return;

		}

		setVisible(true);

	}

	public void create() {

		try {

			Minecraft.instance().isApplet = true;
			Minecraft.instance().checkForUpdates = false;
			
			setVisible(true);
			
			Minecraft.instance().run();

		} catch(Throwable e) {

			Minecraft.instance().crash(e, "Crash while running");

		}

	}

	@Override
	public void destroy() {

		Minecraft.instance().exit();

	}

}
