package com.gmail.nicoq1259;

import java.io.File;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;

import com.gmail.nicoq1259.Listener.KeyboardListener;
import com.gmail.nicoq1259.Listener.MouseListener;
import com.gmail.nicoq1259.game.Action;
import com.gmail.nicoq1259.game.Game;
import com.gmail.nicoq1259.render.Text;
import com.gmail.nicoq1259.render.Texture;
import com.gmail.nicoq1259.scene.Scene;

import static org.lwjgl.opengl.GL11.*;
public class component {
	
	public static int height = 720;
	public static int width = height * 16 / 9;
	
	public static String Title = "Time To Survive";
	
	public boolean run = false;
	
	public static int FPS = 0;
	public static int TICKS = 0;
	public static float OCCUPATION_TICKS  = 0;
	private static float AVERAGE_OCCUPATION_TICKS  = 0;
	public static float AVERAGE_TICKS  = 0;
	
	DisplayMode mode = new DisplayMode(width, height);
	public static boolean tick = false;
	public static boolean render = false;
	public static boolean debugMode = true;
	
	

	public component() {
		try {
			Display.setDisplayMode(mode);
			Display.setResizable(false);
			Display.setFullscreen(false);
			Display.setTitle(Title);
			Display.create();
			
			init();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		run = true;
		loop();
	}
	
	public void stop() {
		run = false;
	}
	
	public void exit(){
		Display.destroy();
		System.exit(0);
	}
	
	public void loop(){
		long timer = System.currentTimeMillis();
		
		long oldTime = System.nanoTime();
		double nanoSeconds = 1000000000.0 / 60.0;
		
		int ticks = 0;
		int fps = 0;
		
		while(run){
			if(Display.isCloseRequested())
				stop();
			Display.update();
			
			tick = false;
			render = false;
			
			long time = System.nanoTime();
			if(time - oldTime >= nanoSeconds){
				oldTime += nanoSeconds; 
				tick = true;
				ticks++;
			}else{
				render = true;
				fps++;
			}
			
			if(render) render();
			if(tick) update();
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				if(Game.DEBUG) System.out.println("[DEBUG] FPS : " + fps + " || Ticks : " +  ticks);
				FPS = fps; 
				TICKS = ticks;
				AVERAGE_TICKS = AVERAGE_OCCUPATION_TICKS / ticks;
				AVERAGE_OCCUPATION_TICKS = 0;
				ticks = 0;
				fps = 0;
			}
		}
		exit();
	}
	static int px = 0;
	public void render(){
		glClear(GL_COLOR_BUFFER_BIT);
		float[] c = new float[]{0f, 0f, 0.6f};
		glClearColor(c[0], c[1], c[2], 1.0f);
		
		
		if(Scene.instance != null)
			Scene.instance.render();
		
	}
	
	public void update(){
		long t = System.nanoTime();
		KeyboardListener.update();
		
		Action.update();
		if(Scene.instance != null)
			Scene.instance.update();
		OCCUPATION_TICKS = (float) (100 * (System.nanoTime() - t) / (1000000000.0 / 60.0));
		AVERAGE_OCCUPATION_TICKS += OCCUPATION_TICKS;
		MouseListener.update();
	}
	
	private void init(){
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLU.gluOrtho2D(0, width, 0, height);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		Texture.init();
		Text.init();
		
		Scene.init();
		Scene.instance = Scene.game;
	}
	
	public static void main(String[] args) {
		System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
		component main = new component();
		main.start();
	}

}
