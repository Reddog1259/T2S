package com.gmail.nicoq1259.entity;

import java.util.ArrayList;

import com.gmail.nicoq1259.game.Game;
import com.gmail.nicoq1259.game.world.Cube;
import com.gmail.nicoq1259.game.world.World;


public class Entity {	
	public static float COLLIDE_OFFSET = 0.1f;

	public static ArrayList<Entity> entities = new ArrayList<Entity>();

	public static void updates(){
		for(Entity e : entities){
			if(getPlayer().distanceTo(e) < 5000)
				e.update();
		}
	}

	public static void renders(){
		for(Entity e : entities){
			if(getPlayer().distanceTo(e) < 5000)
				e.render();
		}
	}

	public static Player getPlayer() {
		return (Player) entities.get(Game.playerId);
	}

	public static int add(Entity e) {
		int id = entities.size();
		entities.add(id, e);
		return id;
	}


	
	
	
	
	
	public float x, y;
	public float vx, vy;
	public float mass;
	public boolean gravitable = true;
	public float sizeX, sizeY;
	public float speed;

	public Entity() {

	}

	public void render(){

	}

	public void update(){

	}

	public boolean isGrounded(){
		int lim = (int) ((this.sizeX + 2) / Cube.size);
		if((this.sizeX + 2) % Cube.size != 0)
			lim += 1;
		for(int tempo = 0; tempo < lim; tempo++){
			Cube temp = Game.world.getCube((int) (this.x / Cube.size) + tempo, (int) ((this.y - COLLIDE_OFFSET) / Cube.size));
			if(temp != null)
				if((temp.isSolide() && temp.type.foreground))
					return true;
		}
		return false;
	}

	public boolean onWater(){
		int lim = (int) ((this.sizeX + 2) / Cube.size);
		if((this.sizeX + 2) % Cube.size != 0)
			lim += 1;
		for(int tempo = 0; tempo < lim; tempo++){
			Cube temp = Game.world.getCube((int) (this.x / Cube.size) + tempo, (int) ((this.y + 2 * COLLIDE_OFFSET) / Cube.size));
			if(temp != null)
				if(temp.type.liquide)
					return true;
		}
		return false;
	}

	public void applyGravity(){
		if(gravitable){
			for(int dy = 0; dy < 75; dy++)
				if(!isGrounded()){
					if(onWater())
						this.vy -= 0.001f * this.mass / 6;
					else
						this.vy -= 0.002f * this.mass / 3;
				}
		}
	}

	public boolean isCollideWorld(float deltax, float deltay){
		boolean can = true;
		Cube p10 = Game.world.getCube((int) ((this.x + this.sizeX + COLLIDE_OFFSET + deltax) / Cube.size), (int) ((this.y + COLLIDE_OFFSET + deltay) / Cube.size));
		Cube p11 = Game.world.getCube((int) ((this.x + this.sizeX + COLLIDE_OFFSET + deltax) / Cube.size), (int) ((this.y + COLLIDE_OFFSET + Cube.size + deltay) / Cube.size));

		Cube p00 = Game.world.getCube((int) ((this.x - COLLIDE_OFFSET + deltax) / Cube.size), (int) ((this.y + COLLIDE_OFFSET + deltay) / Cube.size));
		Cube p01 = Game.world.getCube((int) ((this.x - COLLIDE_OFFSET + deltax) / Cube.size), (int) ((this.y + COLLIDE_OFFSET + Cube.size + deltay) / Cube.size));

		Cube p20 = Game.world.getCube((int) ((this.x + this.sizeX / 2 + COLLIDE_OFFSET + deltax) / Cube.size), (int) ((this.y + COLLIDE_OFFSET + this.sizeY + deltay) / Cube.size));
		Cube p201 = Game.world.getCube((int) ((this.x + COLLIDE_OFFSET + deltax) / Cube.size), (int) ((this.y + COLLIDE_OFFSET + this.sizeY + deltay) / Cube.size));
		Cube p202 = Game.world.getCube((int) ((this.x + this.sizeX - COLLIDE_OFFSET + deltax) / Cube.size), (int) ((this.y + COLLIDE_OFFSET + this.sizeY + deltay) / Cube.size));


		if(deltay > 0 && p20 != null && p201 != null && p202 != null)
			if((!p20.isSolide() || !p20.type.foreground || p20.type.isPlatform())  && (!p201.isSolide() || !p201.type.foreground || p201.type.isPlatform()) && (!p202.isSolide() || !p202.type.foreground || p202.type.isPlatform()))
				can = false;
			else
				can = true;
		else if(p20 == null && p201 == null && p202 == null)
			can = false;
		
		
		if(deltax > 0 && p10 != null & p11 != null)
			if((!p10.isSolide() || !p10.type.foreground || p10.type.isPlatform() || p10.type.isStair()) && (!p11.isSolide() || !p11.type.foreground || p11.type.isPlatform() || p11.type.isStair()))
				can = false;
			else
				can = true;
		else if(p10 == null && p11 == null)
			can = false;
		if(deltax < 0 && p00 != null & p01 != null)
			if((!p00.isSolide() || !p00.type.foreground || p00.type.isPlatform() || p00.type.isStair()) && (!p01.isSolide() || !p01.type.foreground || p01.type.isPlatform() || p01.type.isStair()))
				can = false;
			else
				can = true;
		else if(p00 == null && p01 == null)
			can = false;
		
		return can;
	}
	
	public Cube cubeOnMe(int i){
		Cube t = Game.world.getCube((int) ((this.x + this.sizeX / 2) / Cube.size), (int) ((this.y + this.sizeY * i / 4) / Cube.size));
		if(t == null)
			return null;
		return t;
	}
	
	public void updatePosition(){
		
		if(cubeOnMe(1) != null)
			if(cubeOnMe(1).type.isSolide() && cubeOnMe(1).type.foreground && !cubeOnMe(1).type.isPlatform()){
				this.y += Cube.size;
		}
		
		float deltax = (this.vx / 100);
		for(int dx = 0; dx < 100; dx++)
			if(!isCollideWorld(deltax, 0) && this.x + deltax > 0 && this.x + deltax < World.mapSize * Cube.size)
				this.x += deltax;

		float deltay = (this.vy / 100);
		for(int dy = 0; dy < 100; dy++){
			if(deltay < 0 && !isGrounded())
				this.y += deltay;
			else if(deltay > 0 && !isCollideWorld(0, deltay))
				this.y += deltay;
			else if(deltay > 0){
				this.vy = 0;
			}else if(Math.abs(deltay) < 0.001f)
					this.vy = 0;
			else if(deltay < -0.2f && isGrounded() && cubeOnMe(-1).type.isPlatform()){
				this.y -= Cube.size;
			}
		}

		Cube temp = Game.world.getCube((int) ((this.x + this.sizeX / 2) / Cube.size), (int) ((this.y - COLLIDE_OFFSET) / Cube.size));

		if(temp != null){
			vx *= 1 - temp.type.friction;
			vy *= 1 - temp.type.friction / 4;
		}	
	}
}