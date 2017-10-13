package com.gmail.nicoq1259.game.world;

import static org.lwjgl.opengl.GL11.*;

import com.gmail.nicoq1259.game.Game;


public class Chunk {
	public static int sizeX = 10;
	public static int sizeY = 100;
	
	public int climate = 0;
	
	public int chunkId = -1;

	public Cube cube[][] = new Cube[sizeX][sizeY];
	private int forgroundDisplayList = -1;
	private int BackGroundDisplayList = -1;
	
	private boolean gen = false;
	public boolean visible = false;

	public Chunk(int i) {
		this.chunkId = i;
		for(int x = 0; x < sizeX; x++){
			for(int y = 0; y < sizeY; y++){
				cube[x][y] = new Cube(chunkId * sizeX + x, y, CubeType.Air);
			}
		}
		genLists();
	}
	public void removeLists(){
		glDeleteLists(forgroundDisplayList, 1);
		glDeleteLists(BackGroundDisplayList, 1);
	}
	
	public void genLists(){
		removeLists();
		forgroundDisplayList = glGenLists(1);
		glNewList(forgroundDisplayList, GL_COMPILE);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		for(int x = 0; x < sizeX; x++){
			for(int y = 0; y < sizeY; y++){
				if(cube[x][y].type.foreground || cube[x][y].type.isLiquide())
					cube[x][y].render();
			}
		}
		glDisable(GL_BLEND);
		glEndList();
		
		BackGroundDisplayList = glGenLists(1);
		glNewList(BackGroundDisplayList, GL_COMPILE);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		for(int x = 0; x < sizeX; x++){
			for(int y = 0; y < sizeY; y++){
				if(!cube[x][y].type.foreground && !cube[x][y].type.isLiquide())
					cube[x][y].render();
			}
		}
		glDisable(GL_BLEND);
		glEndList();
	}

	public void renderForeground(){
		if(forgroundDisplayList != -1)
			glCallList(forgroundDisplayList);
	}
	
	public void renderBackground(){
		if(BackGroundDisplayList != -1)
			glCallList(BackGroundDisplayList);
	}

	public void updateChunk(){
		gen = true;
	}

	public void update(){
		for(int x = 0; x < sizeX; x++){
			for(int y = 0; y < sizeY; y++){
					cube[x][y].update();
			}
		}
		if(gen){
			genLists();
			gen = false;
		}
	}

	public void calcDeep(){
		for(int x = 0; x < sizeX; x++){
			int top = Game.world.getTopper(x + chunkId * sizeX);
			for(int y = 0; y < sizeY; y++){
				cube[x][y].deep = top - y;
			}
		}
	}
}
