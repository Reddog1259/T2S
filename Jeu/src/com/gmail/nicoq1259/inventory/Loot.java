package com.gmail.nicoq1259.inventory;

import java.util.ArrayList;

import com.gmail.nicoq1259.game.world.CubeType;

public class Loot {
	public static ArrayList<Loot> cubeLoot = new ArrayList<>();
	
	public static void initCubeLoot(){
		cubeLoot.add(new Loot(new Items(CubeType.Dirt.id, 1), CubeType.Dirt));
		cubeLoot.add(new Loot(new Items(CubeType.Dirt.id, 1), CubeType.Grass));
		cubeLoot.add(new Loot(new Items(CubeType.Iron.id, 1), CubeType.Iron));
		cubeLoot.add(new Loot(new Items(CubeType.Ladder.id, 1), CubeType.Ladder));
		cubeLoot.add(new Loot(new Items(CubeType.Sappling.id, 1), CubeType.Leaf, 0.15f));
		cubeLoot.add(new Loot(new Items(CubeType.Stone.id, 1), CubeType.Stone));
		cubeLoot.add(new Loot(new Items(CubeType.Wood.id, 1), CubeType.Wood));
		cubeLoot.add(new Loot(new Items(CubeType.WoodenFloor.id, 1), CubeType.WoodenFloor));
		cubeLoot.add(new Loot(new Items(CubeType.Sappling.id, 1), CubeType.Sappling));
		cubeLoot.add(new Loot(new Items(CubeType.WoodenStair.id, 1), CubeType.WoodenStair));
		
	}
	
	public static Loot getCubeLoot(CubeType ctype){
		for(Loot l : cubeLoot){
			if(l.ctype == ctype){
				return l;
			}
		}
		return new Loot(1, new Items[]{new Items(-1, 0)}, new float[]{0});
	}
	
	
	
	
	public Items items[];
	public float chance[];
	public CubeType ctype;
	public Items hand;
	
	public Loot(int maxLoot, Items item[], float chance[]) {
		this.items = new Items[maxLoot];
		this.chance = new float[maxLoot];
		for(int i = 0; i < maxLoot; i++){
			this.items[i] = item[i];
			this.chance[i] = chance[i];
		}
	}
	
	public Loot(Items item, CubeType ctype) {
		this.items = new Items[1];
		this.chance = new float[1];
		this.items[0] = item;
		this.chance[0] = 1;
		this.ctype = ctype;
		cubeLoot.add(this);
	}
	
	public Loot(Items item, CubeType ctype, float chance) {
		this.items = new Items[1];
		this.chance = new float[1];
		this.items[0] = item;
		this.chance[0] = chance;
		this.ctype = ctype;
		cubeLoot.add(this);
	}
	
	public Loot(int maxLoot, Items item[], float chance[], CubeType ctype) {
		this.items = new Items[maxLoot];
		this.chance = new float[maxLoot];
		for(int i = 0; i < maxLoot; i++){
			this.items[i] = item[i];
			this.chance[i] = chance[i];
		}
		this.ctype = ctype;
		cubeLoot.add(this);
	}
	
	public Loot(int maxLoot, Items item[], float chance[], CubeType ctype, Items hand) {
		this.items = new Items[maxLoot];
		this.chance = new float[maxLoot];
		for(int i = 0; i < maxLoot; i++){
			this.items[i] = item[i];
			this.chance[i] = chance[i];
		}
		this.ctype = ctype;
		this.hand = hand;
	}

}
