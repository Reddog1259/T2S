package com.gmail.nicoq1259.game.world;

import java.util.Random;

import com.gmail.nicoq1259.component;
import com.gmail.nicoq1259.game.Game;
import com.gmail.nicoq1259.game.world.structure.IronChunk;
import com.gmail.nicoq1259.game.world.structure.Mine;
import com.gmail.nicoq1259.game.world.structure.Tree;
import com.gmail.nicoq1259.render.Camera;

public class World {
	
	public static int state = 0;
	public static int progression = 0;
	public static int mapSize = 1100;
	
	public static int WATER_LEVEL = 10;

	public Chunk chunks[] = new Chunk[mapSize / Chunk.sizeX];
	public static int countChunk = 0;

	public World() {
		for(int i = 0; i < mapSize / Chunk.sizeX; i++){
			chunks[i] = new Chunk(i);
		}
	}

	public void createRandomWorld(int seed){
		state = 1;
		randomWorld(seed);
		
		state = 2;
		progression = 0;
		for(int i = 0; i < mapSize / Chunk.sizeX; i++){	
			progression = 100 * i / (mapSize / Chunk.sizeX);
			chunks[i].calcDeep();
		}
		
		state = 3;
		progression = 0;
		placeWater();
		
		state = 4;
		progression = 0;
		generateStructure(seed);
		
		state = 2;
		progression = 0;
		for(int i = 0; i < mapSize / Chunk.sizeX; i++){	
			progression = 100 * i / (mapSize / Chunk.sizeX);
			chunks[i].calcDeep();
		}
		state = 9;
		progression = 0;
	}
	
	public void placeWater(){
		progression = 0;
		for(int x = 0; x < mapSize; x++){
			progression = 100 * x / mapSize;
			for(int y = 0; y < WATER_LEVEL + 1; y++){
				if(!this.getCube(x, y).type.foreground)
					this.setCube(x, y, CubeType.Water);
			}
		}
	}
	
	public void render(){
		countChunk = 0;
		for(int i = 0; i < mapSize / Chunk.sizeX; i++){
			if(chunks[i].visible){
				chunks[i].renderForeground();
				countChunk++;
			}
		}
	}
	
	public void renderBackground(){
		for(int i = 0; i < mapSize / Chunk.sizeX; i++){
			if(chunks[i].visible){
				chunks[i].renderBackground();
			}
		}
	}

	int Ticks = 0;
	public void update(){
		for(int i = 0; i < mapSize / Chunk.sizeX; i++){
			if(Ticks % 30 == 0)
				chunks[i].updateChunk();
			if((i) * Chunk.sizeX * Cube.size < Math.abs(-Camera.getPosition().x + component.width) && (i + 1) * Chunk.sizeX * Cube.size > -Camera.getPosition().x){
				chunks[i].visible = true;
				chunks[i].update();
			}else{
				chunks[i].visible = false;
			}
		}
		if(Ticks == 60)
			Ticks = 0;
		else
			Ticks++;
	}

	public void randomWorld(int seed){
		float STEP_MAX = 0.7f;
		float STEP_CHANGE = 1.0f;
		float MAX_HEIGHT = Chunk.sizeY - 5;

		Random random = new Random(seed);
		float height = (float) (random.nextFloat() * MAX_HEIGHT);
		float slope = (float) ((random.nextFloat() * STEP_MAX) * 2 - STEP_MAX);
		progression = 0;
		for(int z = 0; z < mapSize; z++){
			progression = 100 * z / (mapSize - 1);
			if(z % (int) (Chunk.sizeX * (random.nextInt(8) + 1)) == 0){
				STEP_MAX = random.nextFloat() * 2.5f;
			}
			chunks[z / Chunk.sizeX].climate = (int) STEP_MAX;

			height += slope;
			slope += (random.nextFloat() * STEP_CHANGE) * 2 - STEP_CHANGE;
			if (slope > STEP_MAX) { slope = STEP_MAX; }
			if (slope < -STEP_MAX) { slope = -STEP_MAX; }

			if (height > MAX_HEIGHT) { 
				height = MAX_HEIGHT;
				slope *= -1;
			}
			if (height < 1) { 
				height = 1;
				slope *= -1;
			}
			for(int y = 0; y < height; y++){
				if(y == 0)
					setCube(z, y, CubeType.Unbreakable);
				else if(y < height - (random.nextInt(6) + 1))
					setCube(z, y, CubeType.Stone);
				else if(y < height - 1)
					setCube(z, y, CubeType.Dirt);
				else if(y < height)
					setCube(z, y, CubeType.Grass);
			}
		}
	}

	public void generateStructure(int seed){
		progression = 0;
		Random r = new Random(seed);
		for(int a = 0; a < mapSize; a++){
			progression = 100 * a / 3 * mapSize;
			int b = this.getTopper(a);
			Tree tree = new Tree();
			if(tree.canPlace(this, a, b)){
				if(r.nextFloat() * 100 < tree.chance){
					tree.place(this, a, b);
				}
			}
		}
		for(int a = 0; a < mapSize; a++){
			progression = 100 / 3 + 100 * a / 3 * mapSize;
			for(int y = this.getTopper(a); y > 0; y--){
				Mine mine = new Mine();
				if(mine.canPlace(this, a, y)){
					if(r.nextFloat() * 100 < mine.chance){
						mine.place(this, a, y);
						for(int i = 0; i < 10; i++){
							if(r.nextFloat() * 100 < 99 * (11 - i) / 10){
								if(mine.canPlace(this, a + i * mine.sizeX, y)){
									mine.place(this, a + i * mine.sizeX, y);
								}
							}else{
								break;
							}
						}
					}
				}
			}
		}
		for(int a = 0; a < mapSize; a++){
			progression = 100 * 2 / 3 + 100 * a / 3 * mapSize;
			for(int y = this.getTopper(a); y > 0; y--){
				IronChunk mine = new IronChunk();
				if(mine.canPlace(this, a, y)){
					if(r.nextFloat() * 100 < mine.chance){
						mine.place(this, a, y);
					}
				}
			}
		}
	}

	public void setCube(int x, int y, CubeType type){
		if(x >= mapSize || x < 0 || y < 0 || y >= Chunk.sizeY)
			return;
		int chunkId = x / Chunk.sizeX;

		this.chunks[chunkId].cube[x - chunkId * Chunk.sizeX][y].type = type;
	}

	public Cube getCube(int x, int y){
		if(x >= mapSize || x < 0 || y < 0 || y >= Chunk.sizeY)
			return null;
		int chunkId = x / Chunk.sizeX;
		return chunks[chunkId].cube[x - chunkId * Chunk.sizeX][y];
	}

	public int getTopper(int x){
		if(x < 0 || x >= mapSize)
			return -1;
		int chunkId = x / Chunk.sizeX;
		for(int y = 0; y < Chunk.sizeY; y++)
			if(!chunks[chunkId].cube[x - chunkId * Chunk.sizeX][y].isSolide() || !chunks[chunkId].cube[x - chunkId * Chunk.sizeX][y].type.foreground)
				return y - 1;
		return -1;
	}
	
	public int getTopperFromTop(int x){
		if(x < 0 || x >= mapSize)
			return -1;
		int chunkId = x / Chunk.sizeX;
		for(int y = Chunk.sizeY - 1; y >= 0; y--)
			if(!chunks[chunkId].cube[x - chunkId * Chunk.sizeX][y].isSolide() || !chunks[chunkId].cube[x - chunkId * Chunk.sizeX][y].type.foreground)
				return y - 1;
		return -1;
	}
	
	public boolean place(int x, int y, CubeType type){
		Cube c = Game.world.getCube(x, y);
		if(c.type == CubeType.Air || (c.type.isLiquide() && c.type.id != 10) || (c.type.isSappling() && type == CubeType.Wood)){
			c.type = type;
			chunks[x / Chunk.sizeX].updateChunk();
			return true;
		}
		return false;
	}
	
	public boolean place(int x, int y, CubeType type, int data){
		Cube c = Game.world.getCube(x, y);
		if(c.type == CubeType.Air || (c.type.isLiquide() && c.type.id != 10) || (c.type.isSappling() && type == CubeType.Wood)){
			c.type = type;
			c.data = data;
			chunks[x / Chunk.sizeX].updateChunk();
			return true;
		}
		return false;
	}
}
