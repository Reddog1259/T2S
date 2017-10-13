package com.gmail.nicoq1259.game.world.structure;

import com.gmail.nicoq1259.game.world.Chunk;
import com.gmail.nicoq1259.game.world.CubeType;
import com.gmail.nicoq1259.game.world.World;

public class Mine extends Structure {

	public Mine() {
		this.sizeX = 5;
		this.sizeY = 5;
		this.chance = 0.01f;
		this.minDeep = 1;
		this.maxDeep = 20;
		this.baseX = 2;
		this.baseY = 0;
		this.struct = new CubeType[][]{{CubeType.Wood, CubeType.Wood, CubeType.Wood, CubeType.Wood, CubeType.Wood},
									   {CubeType.Air,  CubeType.Wood, CubeType.Air,  CubeType.Wood, CubeType.Air},
									   {CubeType.Air,  CubeType.Wood, CubeType.Air,  CubeType.Wood, CubeType.Air},
									   {CubeType.Air,  CubeType.Wood, CubeType.Air,  CubeType.Wood, CubeType.Air},
									   {CubeType.Wood, CubeType.Wood, CubeType.Wood, CubeType.Wood, CubeType.Wood}};
	
	}

	public boolean canPlace(World w, int x, int y){
		for(int a = 0; a < sizeX; a++){
			for(int b = 1; b <= sizeY; b++){
				if(y + b - 1 < Chunk.sizeY && x + a < World.mapSize){
					if(w.getCube(x + a, y + b - 1).type == CubeType.Air)
						return false;
				}else{
					return false;
				}
			}
		}
		return true;
	}
}
