package com.gmail.nicoq1259.entity;

import org.lwjgl.util.vector.Vector2f;
import com.gmail.nicoq1259.inventory.Inventory;
import com.gmail.nicoq1259.render.Camera;
import com.gmail.nicoq1259.render.Texture;

import static org.lwjgl.opengl.GL11.*;

public class Player extends Entity{

	public Inventory inventory = new Inventory();
	
	public Vector2f hand = new Vector2f();
	public boolean handOk = false;
	
	public Player(float x, float y) {
		this.x = x;
		this.y = y;
		hand = new Vector2f(x, y);
		this.sizeX = 35;
		this.sizeY = 64;
		this.mass = 50f;
	}
	
	public void render(){
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glBindTexture(GL_TEXTURE_2D, Texture.player.textureID);
		glBegin(GL_QUADS);
			glColor3f(1f, 1f, 1f);
			glVertex2f(x, y); glTexCoord2f(1, 1);
			glVertex2f(x + sizeX, y); glTexCoord2f(1, 0);
			glVertex2f(x + sizeX, y + sizeY); glTexCoord2f(0, 0);
			glVertex2f(x, y + sizeY); glTexCoord2f(0, 1);
		glEnd();
		
		glBindTexture(GL_TEXTURE_2D, Texture.hand.textureID);
		glBegin(GL_QUADS);
			glVertex2f(x + sizeX / 2 + hand.x, y + sizeY / 2 + hand.y); glTexCoord2f(1, 1);
			glVertex2f(x + sizeX / 2 + hand.x + 10, y + sizeY / 2 + hand.y); glTexCoord2f(1, 0);
			glVertex2f(x + sizeX / 2 + hand.x + 10, y + sizeY / 2 + hand.y + 10); glTexCoord2f(0, 0);
			glVertex2f(x + sizeX / 2 + hand.x, y + sizeY / 2 + hand.y + 10); glTexCoord2f(0, 1);
		glEnd();	
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
	}
	
	public void update(){
		inventory.update();
		applyGravity();
		updatePosition();
		Camera.setPosition(x, y);
	}

	public float distanceTo(Entity e) {
		return (float) Math.sqrt((this.x - e.x) * (this.x - e.x) + (this.y - e.y) * (this.y - e.y));
	}
	

}
