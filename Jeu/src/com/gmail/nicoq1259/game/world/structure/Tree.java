package com.gmail.nicoq1259.game.world.structure;

import com.gmail.nicoq1259.game.world.Chunk;
import com.gmail.nicoq1259.game.world.CubeType;
import com.gmail.nicoq1259.game.world.World;

public class Tree extends Structure {

	public Tree() {
		this.sizeX = 5;
		this.sizeY = 8;
		this.chance = 85;
		this.minDeep = 0;
		this.maxDeep = 0;
		this.baseX = 2;
		this.baseY = 0;
		this.struct = new CubeType[][]{{null,         				 null, 	CubeType.Leaf, null, 			null},
									   {null, 				CubeType.Leaf, 	CubeType.Leaf, CubeType.Leaf, 	null},
									   {null, 				CubeType.Leaf, 	CubeType.Leaf, CubeType.Leaf, 	null},
									   {CubeType.Leaf, 		CubeType.Leaf, 	CubeType.Leaf, CubeType.Leaf, 	CubeType.Leaf},
									   {null, 						null, 	CubeType.Wood, null, 			null},
									   {null, 						null, 	CubeType.Wood, null, 			null},
									   {null, 						null, 	CubeType.Wood, null, 			null},
									   {null, 						null, 	CubeType.Wood, null, 			null}};
	}
	
	public boolean canPlace(World w, int x, int y){
		if(x > 0 && y > 0){
			if(y - 1 > 0 && x + baseX < World.mapSize)
				if(w.getCube(x + baseX, y - 1).type != CubeType.Grass && w.getCube(x + baseX, y - 1).type != CubeType.Dirt)
					return false;
			for(int a = 0; a < sizeX; a++){
				for(int b = 1; b <= sizeY; b++){
					if(y + b - 1 < Chunk.sizeY && x + a < World.mapSize){
						if((w.getCube(x + a, y + b - 1).type != CubeType.Air && !w.getCube(x + a, y + b - 1).type.isSappling())&& struct[sizeY - b][a] != null)
							return false;
					}else{
						return false;
					}
				}
			}
			return true;
		}else{
			return false;
		}
	}
}
