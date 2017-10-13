package com.gmail.nicoq1259.game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.gmail.nicoq1259.entity.Entity;
import com.gmail.nicoq1259.game.world.Chunk;
import com.gmail.nicoq1259.game.world.Cube;
import com.gmail.nicoq1259.game.world.CubeType;
import com.gmail.nicoq1259.game.world.World;
import com.gmail.nicoq1259.inventory.Items;

public class Control {
	
	static int tick = 0;
	static boolean placed = false;
	public static void update(){
		if(tick % 10 == 0)
			placed = false;
		if(Entity.getPlayer().handOk){
			Mouse.setCursorPosition(0, 0);
			Entity.getPlayer().hand.x += Mouse.getDX();
			Entity.getPlayer().hand.y += Mouse.getDY();
			
			if(Entity.getPlayer().hand.y > Cube.size * 2.5f)
				Entity.getPlayer().hand.y = Cube.size * 2.5f;
			if(Entity.getPlayer().hand.y < -Cube.size * 2.5f)
				Entity.getPlayer().hand.y = -Cube.size * 2.5f;
			if(Entity.getPlayer().hand.x > Cube.size * 2.5f)
				Entity.getPlayer().hand.x = Cube.size * 2.5f;
			if(Entity.getPlayer().hand.x < -Cube.size * 2.5f)
				Entity.getPlayer().hand.x = -Cube.size * 2.5f;
			
			if(Mouse.isButtonDown(0) && !placed){
				int cx = (int) (Entity.getPlayer().hand.x + Entity.getPlayer().x + Entity.getPlayer().sizeX / 2) / Cube.size; 
				int cy = (int) (Entity.getPlayer().hand.y + Entity.getPlayer().y + Entity.getPlayer().sizeY / 2) / Cube.size;
	
				int ci = cx / 10;
				if(cx < World.mapSize && cx > 0 && cy > 0 && cy < Chunk.sizeY){
					Cube c = Game.world.chunks[ci].cube[cx - ci * 10][cy];
					if(c != null && c.type != CubeType.Air && (!c.type.isLiquide() || c.type.id == 10)){
						//System.out.println(placed);
						if(c.damage(200f, Entity.getPlayer())){
							placed = true;
						}
					}
				}
			}else if(Mouse.isButtonDown(1) && !placed){
				int cx = (int) (Entity.getPlayer().hand.x + Entity.getPlayer().x + Entity.getPlayer().sizeX / 2) / Cube.size; 
				int cy = (int) (Entity.getPlayer().hand.y + Entity.getPlayer().y + Entity.getPlayer().sizeY / 2) / Cube.size;
	
				if(cx < World.mapSize && cx > 0 && cy > 0 && cy < Chunk.sizeY){
					Items item = Entity.getPlayer().inventory.get(Entity.getPlayer().inventory.selector);
					if(item != null){
						if(item.amount > 0){
							int data = 8;
							if(CubeType.get(item.id).isLadder() || CubeType.get(item.id).isStair()){
								if(Entity.getPlayer().hand.x < 0)
									data = 30;
							}
							if(Game.world.place(cx, cy, CubeType.get(item.id), data)){
								placed = true;
								item.amount--;
								if(item.amount <= 0)
									Entity.getPlayer().inventory.remove(item);
							}					
						}
					}
						
				}
			}
		}else{
			Entity.getPlayer().hand.x = 0;
			Entity.getPlayer().hand.y = 0;
			Entity.getPlayer().handOk = true;
		}
		int dw = Mouse.getDWheel();
		if(dw < 0){
			if(Entity.getPlayer().inventory.selector < Entity.getPlayer().inventory.max_item - 1)
				Entity.getPlayer().inventory.selector++;
		}
		if(dw > 0){
			if(Entity.getPlayer().inventory.selector > 0)
				Entity.getPlayer().inventory.selector--;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			Entity.getPlayer().speed = 5f;
		else
			Entity.getPlayer().speed = 2.5f;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
			Entity.getPlayer().vx += Entity.getPlayer().speed;
		if(Keyboard.isKeyDown(Keyboard.KEY_Q))
			Entity.getPlayer().vx -= Entity.getPlayer().speed;
		if(Keyboard.isKeyDown(Keyboard.KEY_Z) && Entity.getPlayer().isGrounded() && !Entity.getPlayer().onWater())
			Entity.getPlayer().vy += 0.75 * Entity.getPlayer().mass / 3 / (CubeType.Air.friction / 1.5);
		if(Keyboard.isKeyDown(Keyboard.KEY_Z) && Entity.getPlayer().onWater())
			Entity.getPlayer().vy += 0.25 * Entity.getPlayer().mass / 6;
		if(Keyboard.isKeyDown(Keyboard.KEY_S) && Entity.getPlayer().isGrounded()){
			Entity.getPlayer().vy -= 0.25 * Entity.getPlayer().mass / 1.5f;
		}
		
		if(tick >= 60)
			tick = 0;
		else
			tick++;
	}

}
