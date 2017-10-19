package com.gmail.nicoq1259.inventory;

import java.io.Serializable;

public class Inventory implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int max_item = 10;
	private Items items[] = new Items[max_item];
	public int selector = 0;
	
	public Inventory() {
		for(int i = 0; i < max_item; i++){
			items[i] = new Items(-1, 0);
		}
	}
	
	public void add(Items item){
		int quantite = item.amount;
		for(int i = 0; i < max_item; i++){
			if(items[i].id == item.id){
				if(items[i].amount + quantite <= 64){
					items[i].amount += quantite;
					return;
				}
			}
		}
		for(int i = 0; i < max_item; i++){
			if(items[i].id == -1){
				items[i] = new Items(item.id, item.amount);
				return;
			}
		}
	}

	public Items get(int id){
		if(items[id].id != -1){
			return items[id];
		}
		return null;
	}

	public void remove(Items item) {
		for(int i = 0; i < max_item; i++){
			if(items[i].id == item.id){
				items[i] = new Items(-1, 0);
				return;
			}
		}
	}
	
	public void update(){
		for(int i = 0; i < max_item; i++){
			if(items[i].amount == 0){
				items[i] = new Items(-1, 0);
				return;
			}
		}
	}
}
