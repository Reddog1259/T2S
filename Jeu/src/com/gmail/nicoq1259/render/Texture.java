package com.gmail.nicoq1259.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class Texture {
	public static Texture HUD = new Texture("HUD.png");
	public static Texture craftingHUD = new Texture("craftingHUD.png");
	
	public static Texture player = new Texture("player.png");
	public static Texture monster = new Texture("monster.png");
	public static Texture hand = new Texture("hand.png");
	
	public static void init(){
	};
	
	public int textureID = -1;
	public static int counter = 1;
	public Texture(String link){
		File img = new File("Ressources/" + link);
		BufferedImage buffImg = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);

		try { 
		    buffImg = ImageIO.read(img); 
		    textureID = loadTexture(buffImg);
		} 
		catch (Exception e) {
			System.err.println(e);
		}
	}
	
	
	private static final int BYTES_PER_PIXEL = 4;
	public static int loadTexture(BufferedImage image){

		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB

		for(int y = 0; y < image.getHeight(); y++){
			for(int x = 0; x < image.getWidth(); x++){
				int pixel = pixels[y * image.getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
				buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
				buffer.put((byte) (pixel & 0xFF));               // Blue component
				buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
			}
		}

		buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS

		// You now have a ByteBuffer filled with the color data of each pixel.
		// Now just create a texture ID and bind it. Then you can load it using 
		// whatever OpenGL method you want, for example:

		int textureID = glGenTextures(); //Generate texture ID
		glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID

		//Setup wrap mode
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

		//Setup texture scaling filtering
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		//Send texel data to OpenGL
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

		//Return the texture ID so we can bind it later again
		return textureID;
	}
}
