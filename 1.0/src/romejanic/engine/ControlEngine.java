package romejanic.engine;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.esotericsoftware.controller.device.Axis;
import com.esotericsoftware.controller.device.Button;

import romejanic.engine.controls.Xbox360Controller;
import romejanic.main.Minecraft;
import romejanic.particles.ParticleBreakBlock;
import romejanic.world.BlockData;
import romejanic.world.World;
import romejanic.world.block.Block;
import romejanic.world.structure.Structure;

public class ControlEngine implements Engine {

	public Xbox360Controller controllers[];
	public int keyPressFlag = 0;
	public Robot robot;

	public ControlEngine() {

		try {

			robot = new Robot();

		} catch (AWTException e) {

			Minecraft.printStackTrace(e, "Cannot load Robot! Xbox controls will not work!");

		}

	}

	@Override
	public void init() {

		ArrayList<Xbox360Controller> controllersList = new ArrayList<Xbox360Controller>();

		for(Controller controller : ControllerEnvironment.getDefaultEnvironment().getControllers()) {

			if(controller.getType() == Controller.Type.GAMEPAD) {

				controllersList.add(new Xbox360Controller(controller));

			}

		}

		if(controllersList.size() > 0) {

			System.out.println("Detected " + controllersList.size() + " xbox controllers!");

			controllers = ((Xbox360Controller[]) controllersList.toArray(new Xbox360Controller[0]));

		}

	}

	@Override
	public void update() {

		if(keyPressFlag > -1) {

			--keyPressFlag;

		}

		/*if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {

			if(Minecraft.instance().renderEngine.cameraPosX > 0) {

				Minecraft.instance().renderEngine.cameraPosX -= 25f;

			}

		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {

			if(Minecraft.instance().renderEngine.cameraPosX < getWorld().width) {

				Minecraft.instance().renderEngine.cameraPosX += 25f;

			}

		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {

			if(Minecraft.instance().renderEngine.cameraPosY > 0) {

				Minecraft.instance().renderEngine.cameraPosY -= 25f;

			}

		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {

			if(Minecraft.instance().renderEngine.cameraPosY < getWorld().width) {

				Minecraft.instance().renderEngine.cameraPosY += 25f;

			}

		}*/

		if(keyPressFlag < 1) {

			if(Keyboard.isKeyDown(Keyboard.KEY_A)) {

				if(Minecraft.instance().selectedIndex > 0) {

					--Minecraft.instance().selectedIndex;

				}

				keyPressFlag = 25;

			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_D)) {

				if(Minecraft.instance().selectedIndex < (Block.allowedIDs.size() - 1)) {

					Minecraft.instance().selectedIndex++;

				}

				keyPressFlag = 25;

			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {

				if(Minecraft.instance().metaPlacing > 0) {

					--Minecraft.instance().metaPlacing;

				}

				keyPressFlag = 25;

			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_W)) {

				Block block = Block.blocks.get(Minecraft.instance().indexPlacing);

				if(Minecraft.instance().metaPlacing < block.getMaxMeta()) {

					Minecraft.instance().metaPlacing++;

				}

				keyPressFlag = 25;

			}

			if(Keyboard.isKeyDown(Keyboard.KEY_L)) {

				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(Minecraft.instance().savesFolder);
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

				if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

					Minecraft.instance().saveWorld();
					Minecraft.instance().worldSave = fc.getSelectedFile();
					getWorld().load(fc.getSelectedFile());

					try {

						Thread.sleep(1000l);

					} catch (InterruptedException e) {

						Minecraft.printStackTrace(e, "Thread.sleep() interrupted");

					}

				}

				keyPressFlag = 25;

			}
			if(Keyboard.isKeyDown(Keyboard.KEY_K)) {

				Minecraft.instance().saveWorld();

				try {

					Thread.sleep(1000l);

				} catch (InterruptedException e) {

					Minecraft.printStackTrace(e, "Thread.sleep() interrupted");

				}

				keyPressFlag = 25;

			}
			if(Keyboard.isKeyDown(Keyboard.KEY_J)) {

				if(Minecraft.instance().worldSave != null) {

					if(JOptionPane.showConfirmDialog(null, "Are you sure you want to delete \"" + Minecraft.instance().worldSave.getName() + "\"?\n\nIt will be gone forever! (a long time)", "Delete World?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

						System.out.println("Deleting world \"" + Minecraft.instance().worldSave + "\"...");

						Minecraft.instance().worldSave.delete();
						Minecraft.instance().worldSave = null;

						Minecraft.instance().world.generateNewWorld();

						System.out.println("Deleted world!");

					}

				}

				try {

					Thread.sleep(1000l);

				} catch (InterruptedException e) {

					Minecraft.printStackTrace(e, "Thread.sleep() interrupted");

				}

				keyPressFlag = 25;

			}

			if(Keyboard.isKeyDown(Keyboard.KEY_P) || Mouse.isButtonDown(2) || xboxButtonDown(Button.b)) {

				BlockData block = getWorld().blockAt(getMouseX(), getMouseY());

				if(block.getID() > 0 && block.getBlock().idPicked() > 0) {

					Minecraft.instance().selectedIndex = Block.allowedIDs.indexOf(block.getBlock().idPicked());
					Minecraft.instance().metaPlacing = block.getMeta();

				}

				keyPressFlag = 25;

			}

		}

		if(Mouse.isButtonDown(1) || xboxButtonDown(Button.leftShoulder)) {

			if((getWorld().canBlockBeReplaced(getMouseX(), getMouseY()) || getWorld().blockAt(getMouseX(), getMouseY()).getID() == 0) && !getWorld().canActivateBlock(getMouseX(), getMouseY())) {

				getWorld().setBlock(Block.blocks.get(Minecraft.instance().indexPlacing), Minecraft.instance().metaPlacing, getMouseX(), getMouseY());
				//Minecraft.instance().sounds.playSound(Block.blocks.get(Minecraft.instance().indexPlacing).stepSound.getPlaceSound(), 1.0f, 1.0f);

				for(Structure structure : ((Structure[]) getWorld().structures.toArray(new Structure[0]))) {

					for(int id : structure.idsAllowed()) {

						if(Minecraft.instance().indexPlacing == id) {

							structure.onBlockPlaced(getMouseX(), getMouseY(), Minecraft.instance().indexPlacing, Minecraft.instance().metaPlacing, getWorld());

						}

					}

				}

				return;

			}
			else if(getWorld().canActivateBlock(getMouseX(), getMouseY())) {

				getWorld().blockAt(getMouseX(), getMouseY()).getBlock().blockActivated(getMouseX(), getMouseY(), getWorld().getMeta(getMouseX(), getMouseY()), getWorld());

			}

		}
		if(Mouse.isButtonDown(0) || xboxButtonDown(Button.rightShoulder)) {

			BlockData block = getWorld().blockAt(getMouseX(), getMouseY());

			if(block.getBlock() != null && block.getBlock().createBreakParticles()) {

				for(float f = 0f; f < 1f; f += 0.25f) {

					Minecraft.instance().renderEngine.particles.add(new ParticleBreakBlock(Mouse.getX() + (f * 10), Mouse.getY(), block.getID(), block.getMeta()));

				}

			}

			getWorld().setBlock(Block.air, block.getX(), block.getY());
			if(block.getBlock() != null) block.getBlock().blockDestroyed(getMouseX(), getMouseY(), block.getMeta(), getWorld());
			//if(block.getID() > 0) Minecraft.instance().sounds.playSound(Block.blocks.get(block.getID()).stepSound.getBreakSound(), 1.0f, 1.0f);

		}

		if(controllers != null && controllers.length > 0 && robot != null) {
			
			int mouseMoveSpeed = 1;
			Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
			robot.mouseMove(mouseLocation.x + (int)xboxAxis(Axis.leftStickX), mouseLocation.y + (int)xboxAxis(Axis.leftStickY));

			boolean up = xboxAxis(Axis.leftStickY) > 0.032028675;
			boolean right = xboxAxis(Axis.leftStickX) > 0.0016326904;

			robot.mouseMove(mouseLocation.x + (right ? mouseMoveSpeed : -mouseMoveSpeed), mouseLocation.y + (up ? mouseMoveSpeed : -mouseMoveSpeed));

			if(xboxButtonDown(Button.a)) {

				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);

			}
			else if(xboxButtonDown(Button.b)) {

				robot.mousePress(InputEvent.BUTTON3_MASK);
				robot.mouseRelease(InputEvent.BUTTON3_MASK);

			}
			else if(xboxButtonDown(Button.left)) {

				if(keyPressFlag < 1) {

					robot.keyPress(KeyEvent.VK_A);
					robot.keyRelease(KeyEvent.VK_A);

					keyPressFlag = 25;

				}

			}
			else if(xboxButtonDown(Button.right)) {

				if(keyPressFlag < 1) {

					robot.keyPress(KeyEvent.VK_D);
					robot.keyRelease(KeyEvent.VK_D);

					keyPressFlag = 25;

				}

			}
			else if(xboxButtonDown(Button.up)) {

				if(keyPressFlag < 1) {

					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);

					keyPressFlag = 25;

				}

			}
			else if(xboxButtonDown(Button.down)) {

				if(keyPressFlag < 1) {

					robot.keyPress(KeyEvent.VK_S);
					robot.keyRelease(KeyEvent.VK_S);

					keyPressFlag = 25;

				}

			}

		}

	}

	public World getWorld() {

		return Minecraft.instance().world;

	}

	public static int getMouseX() {

		return (Mouse.getX() / 50) + ((int)Minecraft.instance().renderEngine.cameraPosX);

	}

	public static int getMouseY() {

		return Mouse.getY() / 50 + ((int) Minecraft.instance().renderEngine.cameraPosY);

	}

	public static boolean xboxButtonDown(Button button) {

		if(Minecraft.instance().controls.controllers == null) {

			return false;

		}

		for(Xbox360Controller controller : Minecraft.instance().controls.controllers) {

			return controller.get(button);

		}

		return false;

	}

	public static float xboxAxis(Axis axis) {

		if(Minecraft.instance().controls.controllers == null) {

			return 0.0f;

		}

		for(Xbox360Controller controller : Minecraft.instance().controls.controllers) {

			return controller.get(axis);

		}

		return 0.0f;

	}

}
