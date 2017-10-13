package com.gmail.nicoq1259.game.world.structure;

import com.gmail.nicoq1259.game.world.CubeType;
import com.gmail.nicoq1259.game.world.World;

public class Structure {
	public int sizeX, sizeY;
	public int baseX = 0, baseY = 0;
	public int minDeep, maxDeep;

	public float chance; //pourcentage

	public CubeType struct[][] = new CubeType[sizeX][sizeY];



	public Structure() {

	}

	public boolean canPlace(World w, int x, int y){
		return false;
	}

	public void place(World w, int x, int y){
		for(int a = 0; a < sizeX; a++){
			for(int b = 1; b <= sizeY; b++){
				if(struct[sizeY - b][a] != null){
					w.setCube(x + a, y + b - 1, struct[sizeY - b][a]);
				}
			}
		}
	}

}
