package com.gmail.nicoq1259.game.world;

import static org.lwjgl.opengl.GL11.*;

import java.util.Random;


import com.gmail.nicoq1259.entity.Player;
import com.gmail.nicoq1259.game.Game;
import com.gmail.nicoq1259.game.world.structure.Tree;
import com.gmail.nicoq1259.inventory.Loot;

public class Cube {

	public static int size = 32; 
	public int x, y;
	public CubeType type = CubeType.Air;
	public float destroyed = 0;
	public int deep = 0;
	public int data = 8;


	public Cube(int x, int y, CubeType type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}
	public boolean isRenderable = false;
	public void render(){
		if(type.render && (!type.liquide || type.id == 10)){
			if(type != CubeType.Air){
				isRenderable = type.textured && type.texture != null && deep < 2;
				int c = 0;
				for(int a = -1; a < 2; a++){
					for(int b = -1; b < 2; b++){
						if(x + a >= 0 && x + a < World.mapSize && y + b >= 0 && y + b < Chunk.sizeY){
							if(Game.world.getCube(x + a, y + b).isSolide() && Game.world.getCube(x + a, y + b).type.foreground)
								c++;
						}else{
							c++;
						}
					}
				}
				if(c == 9)
					isRenderable = false;
				else 
					isRenderable = true;
			}else{
				isRenderable = false;
			}
			if(isRenderable){
				glEnable(GL_TEXTURE_2D);
				glBindTexture(GL_TEXTURE_2D, type.texture.textureID);
			}

			glBegin(GL_QUADS);

				if(isRenderable) glColor4f(1, 1, 1, type.color[3] * (150 - destroyed) / 100);
				else if(!type.textured || type.texture == null) glColor4f(type.color[0], type.color[1], type.color[2], type.color[3] * (150 - destroyed) / 100);
				else glColor4f(0, 0, 0, type.color[3] * (150 - destroyed) / 100);	
				if(data != 30) glVertex2f(x * size, y * size); 
				else glVertex2f(x * size + size, y * size);
				glTexCoord2f(1, 1);
				
				
				if(isRenderable) glColor4f(1, 1, 1, type.color[3] * (150 - destroyed) / 100);
				else if(!type.textured || type.texture == null) glColor4f(type.color[0], type.color[1], type.color[2], type.color[3] * (150 - destroyed) / 100);
				else glColor4f(0, 0, 0, type.color[3] * (150 - destroyed) / 100);
				if(data != 30) glVertex2f(x * size + size, y * size);
				else glVertex2f(x * size, y * size); 
				glTexCoord2f(1, 0);
				
				
				if(isRenderable) glColor4f(1, 1, 1, type.color[3] * (150 - destroyed) / 100);
				else if(!type.textured || type.texture == null) glColor4f(type.color[0], type.color[1], type.color[2], type.color[3] * (150 - destroyed) / 100);
				else glColor4f(0, 0, 0, type.color[3] * (150 - destroyed) / 100);
				if(data != 30) glVertex2f(x * size + size, y * size + size);
				else glVertex2f(x * size, y * size + size);
				glTexCoord2f(0, 0);
				
				
				if(isRenderable) glColor4f(1, 1 , 1, type.color[3] * (150 - destroyed) / 100);
				else if(!type.textured || type.texture == null) glColor4f(type.color[0], type.color[1], type.color[2], type.color[3] * (150 - destroyed) / 100);
				else glColor4f(0, 0, 0, type.color[3] * (150 - destroyed) / 100);
				if(data != 30) glVertex2f(x * size, y * size + size); 
				else glVertex2f(x * size + size, y * size + size);
				glTexCoord2f(0, 1);
			glEnd();

			if(isRenderable){
				glDisable(GL_TEXTURE_2D);
			}
		}else if(type.render && type.liquide){
			int datam1 = data;
			int datap1 = data;
			if(x > 1){
				if(Game.world.getCube(x, y + 1).type != CubeType.Water){
					if(Game.world.getCube(x - 1, y).type == CubeType.Water){
						datam1 = Game.world.getCube(x - 1, y).data;
					}
					if(Game.world.getCube(x + 1, y).type == CubeType.Water){
						datap1 = Game.world.getCube(x + 1, y).data;
					}
				}else{
					datam1 = datap1 = 8;
				}
			}
			glEnable(GL_TEXTURE_2D);
			glBindTexture(GL_TEXTURE_2D, type.texture.textureID);
			glBegin(GL_QUADS);
			glColor4f(1, 1, 1, type.color[3]);
			glVertex2f(x * size, y * size); glTexCoord2f(1, 1);
			glVertex2f(x * size + size, y * size); glTexCoord2f(1, 0);
			glVertex2f(x * size + size, y * size + (datap1 * size / 8)); glTexCoord2f(0, 0);
			glVertex2f(x * size, y * size + (datam1 * size / 8)); glTexCoord2f(0, 1);
			glEnd();
			glDisable(GL_TEXTURE_2D);
		}
	}

	private int damaged = 0;
	private Player damager = null;
	
	public boolean damage(float degat, Player who){
		if(type.resitance != 999){
			destroyed += degat / type.resitance;
			damaged = 5;
			Game.world.chunks[x / 10].updateChunk();
			this.damager = who;
			if(destroyed >= 100)
				return true;
		}
		return false;
	}


	public void update(){
		if(destroyed >= 100 && type.resitance != 999 && damager != null){
			destroy(damager);
		}
		if(damaged > 0){
			damaged--;
		}
		if(damaged == 0 && destroyed != 0){
			destroyed = 0;
			damager = null;
			Game.world.chunks[x / 10].updateChunk();
			Game.world.chunks[x / Chunk.sizeX].calcDeep();
		}
		if(type.liquide && type.id != 10){
			liquideUpdate();
		}
		if(type.isLeaf() && data < 9){
			leafUpdate();
		}
		
		grassUpdate();
		if(type.isSappling()){
			TreeUpdate();
		}
	}
	
	public void TreeUpdate(){
		Tree t = new Tree();
		if(t.canPlace(Game.world, x - t.baseX, y)){
			if((new Random()).nextFloat() < 0.01f)
				if((new Random()).nextFloat() < 0.1f)
					t.place(Game.world, x - t.baseX, y);
		}
	}
	
	public void grassUpdate(){
		if(type == CubeType.Grass){
			if(y + 1 < Chunk.sizeY){
				if(Game.world.getCube(x, y + 1).isSolide() && Game.world.getCube(x, y + 1).type.foreground)
					if((new Random()).nextInt(101) > 99){
						destroy();
						type = CubeType.Dirt;
						Game.world.chunks[x / 10].updateChunk();
					}
			}
		}else if(type == CubeType.Dirt){
			if(y + 1 < Chunk.sizeY){
				if(Game.world.getCube(x, y + 1).type == CubeType.Air){
					boolean expend = false;
					for(int a = -1; a < 2; a++){
						for(int b = -1; b < 2; b++){
							if(a != 0 && !expend){
								if(x + a >= 0 && x + a < World.mapSize && y + b >= 0 && y + b < Chunk.sizeY){
									if(Game.world.getCube(a + x, b + y).type == CubeType.Grass){
										expend = true;
									}
								}
							}
						}
					}
					if(expend){
						Random r = new Random();
						if(r.nextInt(101) > 99){
							type = CubeType.Grass;
							Game.world.chunks[x / 10].updateChunk();
						}
					}
				}
			}
		}
	}
	
	public void leafUpdate(){
		int max = -1;
		for(int a = -1; a < 2; a++){
			for(int b = -1; b < 2; b++){
				if(x + a > 0 && x + a < World.mapSize && y + b > 0 && y + b < Chunk.sizeY){
					if(Game.world.getCube(x + a, y + b).type.isWood()){
						this.data = 5;
					}else{
						if(Game.world.getCube(x + a, y + b).type.isLeaf() && data < 9){
							if(Game.world.getCube(x + a, y + b).data > max){
								max = Game.world.getCube(x + a, y + b).data;
							}
						}
					}
				}
			}
		}
		if(max != -1)
			data = max - 1;
		if(data <= 0 && (new Random()).nextInt(101) < 1){
			destroy();
			if((new Random()).nextFloat() * 1000 < 100){
				Game.world.getCube(x, Game.world.getTopper(x) + 1).type = CubeType.Leaf;
				Game.world.getCube(x, Game.world.getTopper(x) + 1).data = 9;
			}
		}
	}
	
	public void liquideUpdate(){
		Cube cubem = null; 
		Cube cubep = null;
		Cube cubeb = null;
		if(x + 1 < World.mapSize)
			cubep = Game.world.getCube(x + 1, y);
		if(x - 1 > 0)
			cubem = Game.world.getCube(x - 1, y);
		if(y - 1 > 0)
			cubeb = Game.world.getCube(x, y - 1);
		int r = (int) ((new Random()).nextFloat() * 100);
		if(r < 25 || cubeb == null || cubeb.isSolide()){
			r = (int) ((new Random()).nextFloat() * 100);
			if(r < 50 && (cubem != null && !cubem.isSolide())){
				if(cubem.type == CubeType.Water){
					if(cubem.data < 8 && data > 0 && data > cubem.data){
						data--;
						cubem.data++;
					}
				}else{
					cubem.type = CubeType.Water;
					cubem.data = 1;
					data--;
				}
			}else{
				if((cubep != null && !cubep.isSolide())){
					if(cubep.type == CubeType.Water){
						if(cubep.data < 8 && data > 0 && data > cubep.data){
							data--;
							cubep.data++;
						}
					}else{
						cubep.type = CubeType.Water;
						cubep.data = 1;
						data--;
					}
				}
			}
		}else{
			if(cubeb.type == CubeType.Water){
				if(cubeb.data < 8 && data > 0){
					data--;
					cubeb.data++;
				}
			}else{
				cubeb.type = CubeType.Water;
				cubeb.data = 1;
				data--;
			}
		}
		if(data <= 0){
			destroy();
		}
	}
	
	public boolean isSolide(){
		return type.isSolide();
	}
	
	
	public void destroy(Player w){
		Loot l = Loot.getCubeLoot(type);
		if(l.items[0].id != -1){
			if(l.chance[0] == 1)
				w.inventory.add(l.items[0]);
			else{
				if((new Random()).nextFloat() <= l.chance[0])
					w.inventory.add(l.items[0]);
			}
		}
		
		type = CubeType.Air;
		data = 8;
		destroyed = 0;
		Game.world.chunks[x / 10].updateChunk();
		Game.world.chunks[x / Chunk.sizeX].calcDeep();
	}
	
	public void destroy(){
		type = CubeType.Air;
		data = 8;
		destroyed = 0;
		Game.world.chunks[x / 10].updateChunk();
		Game.world.chunks[x / Chunk.sizeX].calcDeep();
	}
}
