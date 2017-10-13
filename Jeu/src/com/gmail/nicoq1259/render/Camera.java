package com.gmail.nicoq1259.render;

import org.lwjgl.util.vector.Vector2f;

import com.gmail.nicoq1259.component;
import com.gmail.nicoq1259.game.world.Cube;
import com.gmail.nicoq1259.game.world.World;

import static org.lwjgl.opengl.GL11.*;
public class Camera {
	
	private static Vector2f oldPosition = new Vector2f(0, 0);
	private static Vector2f Position = new Vector2f(0, 0);
	
	public static void moveCamera(){
		glTranslatef(-oldPosition.x + Position.x, -oldPosition.y + Position.y, 0);
		oldPosition = new Vector2f(Position);
	}
	
	public static void setPosition(float x, float y){
		if(x - component.width / 2 >= 0 && x - component.width / 2 < World.mapSize * Cube.size - component.width)
			Position.x = component.width / 2 - x;
		if(y - component.height / 2 >= 0 )//&& y - component.height / 2 < Chunk.sizeY * Cube.size - component.height)
			Position.y = component.height / 2 - y;
	}
	
	public static Vector2f getPosition(){
		return Position;
	}
	

}
