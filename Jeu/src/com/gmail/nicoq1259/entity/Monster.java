package com.gmail.nicoq1259.entity;

import static org.lwjgl.opengl.GL11.*;

import com.gmail.nicoq1259.game.world.CubeType;
import com.gmail.nicoq1259.render.Texture;

public class Monster extends Entity {
	public Player target;

	public Monster(float x, float y) {
		this.x = x;
		this.y = y;
		this.sizeX = 35;
		this.sizeY = 64;
		this.mass = 50f;
	}

	public void render(){
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glBindTexture(GL_TEXTURE_2D, Texture.monster.textureID);
		glBegin(GL_QUADS);
		glColor3f(1f, 1f, 1f);
		glVertex2f(x, y); glTexCoord2f(1, 1);
		glVertex2f(x + sizeX, y); glTexCoord2f(1, 0);
		glVertex2f(x + sizeX, y + sizeY); glTexCoord2f(0, 0);
		glVertex2f(x, y + sizeY); glTexCoord2f(0, 1);
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
	}
	public void update(){
		//find target
		if(Entity.getPlayer().distanceTo(this) < 2000){
			target = Entity.getPlayer();
		}else{
			target = null;
		}
		//

		//localiser et trouvé le chemin

		if(target != null){
			float dx = (target.x + target.sizeX / 2) - (this.x + this.sizeX / 2);
			//float dy = (target.y + target.sizeY / 4) - (this.y + this.sizeY / 4);
			
			if(dx - 4 < 0)
				this.vx -= 1.5f;
			if(dx + 4 > 0)
				this.vx += 1.5f;
			if(isCollideWorld(this.vx, 0) && isGrounded() && Math.abs(dx) > 4){
				this.vy += 0.95 * mass / 3 / (CubeType.Air.friction / 1.5);
			}
			
		}
		//
		applyGravity();
		updatePosition();
	}
}
