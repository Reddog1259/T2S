package com.gmail.nicoq1259.game;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.util.vector.Vector2f;

import com.gmail.nicoq1259.Listener.KeyboardListener;
import com.gmail.nicoq1259.entity.Entity;
import com.gmail.nicoq1259.game.world.CubeType;
import com.gmail.nicoq1259.inventory.Craft;
import com.gmail.nicoq1259.inventory.Items;
import com.gmail.nicoq1259.render.Text;
import com.gmail.nicoq1259.render.Texture;

public class CraftingHUD {
	
	public static int sizeX = 500;
	public static int sizeY = 450;
	
	private static int offset = 0;
	private static int selected = 0;
	
	public static void render(Vector2f origine){
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, Texture.craftingHUD.textureID);
		glBegin(GL_QUADS);
			glColor4f(1, 1, 1, 1f);
			glVertex2f(origine.x, origine.y); glTexCoord2f(1, 1);
			glVertex2f(origine.x + sizeX, origine.y); glTexCoord2f(1, 0);
			glVertex2f(origine.x + sizeX, origine.y + sizeY); glTexCoord2f(0, 0);
			glVertex2f(origine.x, origine.y + sizeY); glTexCoord2f(0, 1);
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		
		
		
		Vector2f recipeOrigine = new Vector2f(origine.x + 25, origine.y + 275);
		for(int i = 0; i < 4; i++){
			if(selected == offset + i && selected < Craft.craftGuide.size()){
				glEnable(GL_BLEND);
				glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
				glBegin(GL_QUADS);
					glColor4f(0.3f, 0.3f, 0.3f, 0.5f);
					glVertex2f(recipeOrigine.x - 5, recipeOrigine.y - 50 * i);
					glVertex2f(recipeOrigine.x + 455, recipeOrigine.y - 50 * i);
					glVertex2f(recipeOrigine.x + 455, recipeOrigine.y + 50 - 50 * i);
					glVertex2f(recipeOrigine.x - 5, recipeOrigine.y + 50 - 50 * i);
				glEnd();
				glDisable(GL_BLEND);
			}
			if(offset + i < Craft.craftGuide.size()){
				Craft craft = Craft.craftGuide.get(offset + i);
				if(craft != null){
					int a = 0;
					for(Items n : craft.needed){
						glEnable(GL_BLEND);
						glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
						glEnable(GL_TEXTURE_2D);
						glBindTexture(GL_TEXTURE_2D, CubeType.get(n.id).texture.textureID);
						glBegin(GL_QUADS);
							glColor4f(1, 1, 1, 1f);
							glVertex2f(recipeOrigine.x + 32 * a + a * 5, recipeOrigine.y - 50 * i + 9); glTexCoord2f(1, 1);
							glVertex2f(recipeOrigine.x + 32 + 32 * a + a * 5, recipeOrigine.y - 50 * i + 9); glTexCoord2f(1, 0);
							glVertex2f(recipeOrigine.x + 32 + 32 * a + a * 5, recipeOrigine.y + 50 - 50 * i - 9); glTexCoord2f(0, 0);
							glVertex2f(recipeOrigine.x + 32 * a + a * 5, recipeOrigine.y + 50 - 50 * i - 9); glTexCoord2f(0, 1);
						glEnd();
						glDisable(GL_BLEND);
						glDisable(GL_TEXTURE_2D);
						Text.renderTexte(n.amount + "", recipeOrigine.x + 32 * a + a * 5 + (n.amount + "").length() * 8, recipeOrigine.y - 50 * i + 9 + 3, 1);
						a++;
					}
					Text.renderTexte("=", recipeOrigine.x + 32 * a + a * 5 + 12, recipeOrigine.y - 50 * i + 9 + 3, 3);
					a++;
					
					for(Items n : craft.result){
						glEnable(GL_BLEND);
						glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
						glEnable(GL_TEXTURE_2D);
						glBindTexture(GL_TEXTURE_2D, CubeType.get(n.id).texture.textureID);
						glBegin(GL_QUADS);
							glColor4f(1, 1, 1, 1f);
							glVertex2f(recipeOrigine.x + 32 * a + a * 5, recipeOrigine.y - 50 * i + 9); glTexCoord2f(1, 1);
							glVertex2f(recipeOrigine.x + 32 + 32 * a + a * 5, recipeOrigine.y - 50 * i + 9); glTexCoord2f(1, 0);
							glVertex2f(recipeOrigine.x + 32 + 32 * a + a * 5, recipeOrigine.y + 50 - 50 * i - 9); glTexCoord2f(0, 0);
							glVertex2f(recipeOrigine.x + 32 * a + a * 5, recipeOrigine.y + 50 - 50 * i - 9); glTexCoord2f(0, 1);
						glEnd();
						glDisable(GL_TEXTURE_2D);
						glDisable(GL_BLEND);
						Text.renderTexte(n.amount + "", recipeOrigine.x + 32 * a + a * 5 + (n.amount + "").length() * 8, recipeOrigine.y - 50 * i + 9 + 3, 1);
						a++;
					}
				}
			}
		}
		
	}
	
	public static void update(Vector2f origine){
		if(selected > 0){
			if(KeyboardListener.getKeyPressed().equalsIgnoreCase("UP")){
				selected--;
			}
		}
		if(selected < Craft.craftGuide.size() - 1){
			if(KeyboardListener.getKeyPressed().equalsIgnoreCase("DOWN")){
				selected++;
			}
		}
		
		if(KeyboardListener.getKeyPressed().equalsIgnoreCase("RETURN")){
			Craft craft = Craft.craftGuide.get(selected);
			int ok = 0;
			for(Items n : craft.needed){
				for(int i = 0; i < Entity.getPlayer().inventory.max_item; i ++){
					if(Entity.getPlayer().inventory.get(i) != null)
						if(Entity.getPlayer().inventory.get(i).id == n.id){
							if(Entity.getPlayer().inventory.get(i).amount >= n.amount){
								ok++;
							}
						}
				}
			}
			if(ok == craft.needed.length){
				for(Items n : craft.needed){
					for(int i = 0; i < Entity.getPlayer().inventory.max_item; i ++){
						if(Entity.getPlayer().inventory.get(i) != null)
							if(Entity.getPlayer().inventory.get(i).id == n.id){
								Entity.getPlayer().inventory.add(new Items(n.id, - n.amount));
							}
					}
				}
				for(Items n : craft.result)
					Entity.getPlayer().inventory.add(new Items(n.id, n.amount));

			}
		}
		
		if(selected > offset + 3){
			offset++;
		}
		
		if(selected < offset){
			offset--;
		}
	}

}
