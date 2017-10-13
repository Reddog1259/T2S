package com.gmail.nicoq1259.game;


import static org.lwjgl.opengl.GL11.*;

import java.util.Date;

import org.lwjgl.util.vector.Vector2f;

import com.gmail.nicoq1259.component;
import com.gmail.nicoq1259.entity.Entity;
import com.gmail.nicoq1259.entity.Player;
import com.gmail.nicoq1259.game.world.Cube;
import com.gmail.nicoq1259.game.world.CubeType;
import com.gmail.nicoq1259.game.world.World;
import com.gmail.nicoq1259.inventory.Items;
import com.gmail.nicoq1259.render.Text;
import com.gmail.nicoq1259.render.Texture;

public class Hud {

	@SuppressWarnings("deprecation")
	public static void render(Vector2f origine){
		if(Game.DEBUG){
			String chunkcounter = "Chunk : " + World.countChunk + "/" + Game.world.chunks.length;
			String stats = "FPS : " + component.FPS + "  TICKS : " + component.TICKS;
			Player p = (Player) Entity.entities.get(Game.playerId);
			String playerPos = "x : " + (int) (p.x / Cube.size) + "  y : " + (int) (p.y / Cube.size);
			String tick = "OT : " + (int) component.OCCUPATION_TICKS + "%  AT : " + (int) component.AVERAGE_TICKS + "%";
			
			Text.renderTexte(chunkcounter, origine.x + 5, origine.y + (component.height - 8 - 45), 1);	
			Text.renderTexte(stats, origine.x + 5, origine.y + (component.height - 8 - 55), 1);	
			Text.renderTexte(playerPos, origine.x + 5, origine.y + (component.height - 8 - 65), 1);	
			Text.renderTexte(tick, origine.x + 5, origine.y + (component.height - 8 - 75), 1);	
			
		}
		renderHUDBar(origine);
		Date t = new Date();
		renderTime(origine, t.getHours(), t.getMinutes());
		showInventory(origine);
	}

	public static void renderTime(Vector2f origine, int h, int m){
		int taille = 2;
		String text;
		h = h % 24;
		m = m % 60;
		if(h < 10)
			text = "0" + h + ":";
		else
			text = h + ":";
		if(m < 10)
			text += "0" + m;
		else
			text += m;
		Text.renderTexte(text, origine.x + (component.width - text.length() * 8 * taille) / 2, origine.y + (component.height - 8 * taille - 15), taille);
	}
	public static void renderHUDBar(Vector2f origine){
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, Texture.HUD.textureID);
		glBegin(GL_QUADS);
			glColor4f(1, 1, 1, 1f);
			glVertex2f(origine.x, origine.y); glTexCoord2f(1, 1);
			glVertex2f(origine.x + component.width, origine.y); glTexCoord2f(1, 0);
			glVertex2f(origine.x + component.width, origine.y + component.height); glTexCoord2f(0, 0);
			glVertex2f(origine.x, origine.y + component.height); glTexCoord2f(0, 1);
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
	}
	
	public static void showInventory(Vector2f origine){
		int itemSize = 20;
		int posX = (int) (25);
		int posY = (int) component.height - 33 + (32 - itemSize) / 2;

		
		int space = 5;
		for(int a = 0; a < Entity.getPlayer().inventory.max_item; a++){
			if(Entity.getPlayer().inventory.get(a) != null){
				Items i = Entity.getPlayer().inventory.get(a);
				glEnable(GL_BLEND);
				glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
				glEnable(GL_TEXTURE_2D);
				glBindTexture(GL_TEXTURE_2D, CubeType.get(i.id).texture.textureID);
				glBegin(GL_QUADS);
					glColor4f(1, 1, 1, CubeType.get(i.id).color[3]);
					glVertex2f(origine.x + posX + (itemSize + space) * a, origine.y + posY); glTexCoord2f(1, 1);
					glVertex2f(origine.x + posX + (itemSize + space) * a + itemSize, origine.y + posY); glTexCoord2f(1, 0);
					glVertex2f(origine.x + posX + (itemSize + space) * a + itemSize, origine.y + posY + itemSize); glTexCoord2f(0, 0);
					glVertex2f(origine.x + posX + (itemSize + space) * a, origine.y + posY + itemSize); glTexCoord2f(0, 1);
				glEnd();
				glDisable(GL_BLEND);
				glDisable(GL_TEXTURE_2D);
				Text.renderTexte("" + i.amount, origine.x + posX + (itemSize + space) * a + (itemSize - ("" + i.amount).length() * 8), origine.y + posY + 2, 1);
			}
		}
		
		int a = Entity.getPlayer().inventory.selector;
		glLineWidth(2);
		glBegin(GL_LINES);
			
			glColor4f(0, 0, 0, 1f);
			glVertex2f(-2 + origine.x + posX + (itemSize + space) * a, origine.y + posY - 1);
			glVertex2f(2 + origine.x + posX + (itemSize + space) * a + itemSize, origine.y + posY - 1);
			glVertex2f(2 + origine.x + posX + (itemSize + space) * a + itemSize, origine.y + posY + itemSize + 1);
			glVertex2f(-2 + origine.x + posX + (itemSize + space) * a, origine.y + posY + itemSize + 1);
			
			glVertex2f(-1 + origine.x + posX + (itemSize + space) * a, origine.y + posY - 1);
			glVertex2f(-1 + origine.x + posX + (itemSize + space) * a, origine.y + posY + itemSize + 1);
			glVertex2f(1 + origine.x + posX + (itemSize + space) * a + itemSize, origine.y + posY - 1);
			glVertex2f(1 + origine.x + posX + (itemSize + space) * a + itemSize, origine.y + posY + itemSize + 1);
			
		glEnd();
	}
}
