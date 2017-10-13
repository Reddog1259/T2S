package com.gmail.nicoq1259.inventory;


import com.gmail.nicoq1259.entity.Entity;

public class Items extends Entity{
	public int id = 0;
	public int amount = 0;
	public ItemType type = null;
	
	public Items(int id, int amount) {
		this.id = id;
		this.amount = amount;
		if(id < 256)
			type = ItemType.Cube;
	}
	
	public enum ItemType{
		Cube, Axe, PickAxe, Sword;
	}
	public enum ItemLevel{
		
	}
}
