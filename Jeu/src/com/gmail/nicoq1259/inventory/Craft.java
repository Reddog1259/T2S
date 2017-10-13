package com.gmail.nicoq1259.inventory;

import java.util.ArrayList;

import com.gmail.nicoq1259.game.world.CubeType;

public class Craft {
	public static ArrayList<Craft> craftGuide = new ArrayList<Craft>();
	
	public static void init(){
		new Craft(new Items[]{new Items(CubeType.Wood.id, 2)}, new Items[]{new Items(CubeType.Ladder.id, 5)});
		new Craft(new Items[]{new Items(CubeType.Wood.id, 3)}, new Items[]{new Items(CubeType.WoodenFloor.id, 2)});
		new Craft(new Items[]{new Items(CubeType.Wood.id, 2)}, new Items[]{new Items(CubeType.WoodenStair.id, 3)});
		
		
		new Craft(new Items[]{new Items(CubeType.Dirt.id, 2), new Items(CubeType.Grass.id, 1)}, new Items[]{new Items(CubeType.Dirt.id, 2)});
	}
	
	public Items needed[];
	public Items result[];
	public Craft(Items needed[], Items result[]) {
		this.needed = needed;
		this.result = result;
		craftGuide.add(this);
	}

}
