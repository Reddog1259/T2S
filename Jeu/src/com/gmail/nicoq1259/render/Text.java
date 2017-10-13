package com.gmail.nicoq1259.render;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Text {
	private static String ex[][] = {{ "^5", "^5", "^5", "^5", "^5", "^5", "^5", "^5", "^5", "^5", "^5", "^5", "^5", "^5", "^5", "^5"},
								  { "^5", "^5", "^5", "^5", "^5", "^5", "^5", "^5", "^5", "^5", "^5", "^5", "^5", "^5", "^5", "^5"},
								  { "^5", "!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+", ",", "-", ".", "/"},
								  { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ":", "'", "<", "=", ">", "?"},
								  { "@", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"},
								  { "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "[", "\\", "]", "^", "_"},
								  { "^5", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o"},
								  { "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "{", "|", "}", "~", "^5"}
	};
	
	private static BufferedImage font;
	private static ArrayList<Integer> fontTexture = new ArrayList<Integer>();
	public static void init() {
		File img = new File("Ressources/ascii.png");
		font = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		try { 
			font = ImageIO.read(img);
			for(int y = 0; y < font.getHeight() / 8; y++){
				for(int x = 0; x < font.getWidth() / 8; x++){
					fontTexture.add(Texture.loadTexture(font.getSubimage(x * 8, y * 8, 8, 8)));
				}
			}  
		} 
		catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public static int getFont(int Id){
		return fontTexture.get(Id);
	}
	public static void renderLettre(String l, float xr, float yr, int size){
		size *= 8;
		for(int y = 0; y < ex.length; y++){
			for(int x = 0; x < ex[0].length; x++){
				if(ex[y][x].equals(l)){
					glEnable(GL_BLEND);
					glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
					glEnable(GL_TEXTURE_2D);
					glBindTexture(GL_TEXTURE_2D, Text.getFont(y * 16 + x));
					glBegin(GL_QUADS);
						glColor3f(1, 1, 1);
						glVertex2f(xr, yr); glTexCoord2f(1, 1);
						glVertex2f(xr + size, yr); glTexCoord2f(1, 0);
						glVertex2f(xr + size, yr + size); glTexCoord2f(0, 0);
						glVertex2f(xr, yr + size); glTexCoord2f(0, 1);
					glEnd();
					glDisable(GL_TEXTURE_2D);
					glDisable(GL_BLEND);
				}
			}
		}  
	}
	public static void renderTexte(String s, float x, float y, int size){
		for(int i = 0; i < s.length(); i++){
			renderLettre(s.substring(i, i + 1), x + i * size * 8, y, size);
			//System.out.println(s.substring(i, i + 1));
		}
	}

	public static void renderTexte(String s, int x, int y, int size, float[] fs) {
		for(int i = 0; i < s.length(); i++){
			renderLettre(s.substring(i, i + 1), x + i * size * 8, y, size, fs);
			//System.out.println(s.substring(i, i + 1));
		}
	}

	private static void renderLettre(String l, int xr, int yr, int size,
			float[] fs) {
		size *= 8;
		for(int y = 0; y < ex.length; y++){
			for(int x = 0; x < ex[0].length; x++){
				if(ex[y][x].equals(l)){
					glEnable(GL_BLEND);
					glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
					glEnable(GL_TEXTURE_2D);
					glBindTexture(GL_TEXTURE_2D, Text.getFont(y * 16 + x));
					glBegin(GL_QUADS);
						glColor3f(fs[0], fs[1], fs[2]);
						glVertex2f(xr, yr); glTexCoord2f(1, 1);
						glVertex2f(xr + size, yr); glTexCoord2f(1, 0);
						glVertex2f(xr + size, yr + size); glTexCoord2f(0, 0);
						glVertex2f(xr, yr + size); glTexCoord2f(0, 1);
					glEnd();
					glDisable(GL_TEXTURE_2D);
					glDisable(GL_BLEND);
				}
			}
		}  
	}
	
}
