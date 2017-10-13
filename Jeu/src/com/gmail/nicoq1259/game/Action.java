package com.gmail.nicoq1259.game;

import java.util.ArrayList;

import com.gmail.nicoq1259.Listener.KeyboardListener;
import com.gmail.nicoq1259.entity.Entity;
import com.gmail.nicoq1259.entity.Player;
import com.gmail.nicoq1259.game.world.Chunk;
import com.gmail.nicoq1259.game.world.World;
import com.gmail.nicoq1259.scene.Scene;

public abstract class Action {
	private String key;
	
	public Action(String key){
		this.key = key;
	}
	
	public void run(){
		
	}
	
	
	
	public static ArrayList<Action> actions = new ArrayList<Action>();
	
	public static void init(){
		actions.add(new Action("e"){
			public void run(){
				Game.openCraft = !Game.openCraft;
			}
		});
		
		actions.add(new Action("F4"){
			public void run(){
				for(int i = 0; i < World.mapSize / Chunk.sizeX; i++){			
					 Game.world.chunks[i].updateChunk();
				}
			}
		});
		
		actions.add(new Action("ESCAPE"){
			public void run(){
				if(Scene.instance == Scene.pause)
					Scene.instance = Scene.game;
				else if(Scene.instance == Scene.game)
					Scene.instance = Scene.pause;
			}
		});
		
		actions.add(new Action("F3"){
			public void run(){
				Game.DEBUG = !Game.DEBUG;
			}
		});
		
		actions.add(new Action("1"){
			public void run(){
				Player p = (Player) Entity.entities.get(Game.playerId);
				p.inventory.selector = 0;
			}
		});
		actions.add(new Action("2"){
			public void run(){
				Player p = (Player) Entity.entities.get(Game.playerId);
				p.inventory.selector = 1;
			}
		});
		actions.add(new Action("3"){
			public void run(){
				Player p = (Player) Entity.entities.get(Game.playerId);
				p.inventory.selector = 2;
			}
		});
		actions.add(new Action("4"){
			public void run(){
				Player p = (Player) Entity.entities.get(Game.playerId);
				p.inventory.selector = 3;
			}
		});
		actions.add(new Action("5"){
			public void run(){
				Player p = (Player) Entity.entities.get(Game.playerId);
				p.inventory.selector = 4;	
			}
		});
		actions.add(new Action("6"){
			public void run(){
				Player p = (Player) Entity.entities.get(Game.playerId);
				p.inventory.selector = 5;	
			}
		});
		actions.add(new Action("7"){
			public void run(){
				Player p = (Player) Entity.entities.get(Game.playerId);
				p.inventory.selector = 6;	
			}
		});
		actions.add(new Action("8"){
			public void run(){
				Player p = (Player) Entity.entities.get(Game.playerId);
				p.inventory.selector = 7;	
			}
		});
		actions.add(new Action("9"){
			public void run(){
				Player p = (Player) Entity.entities.get(Game.playerId);
				p.inventory.selector = 8;	
			}
		});
		actions.add(new Action("0"){
			public void run(){
				Player p = (Player) Entity.entities.get(Game.playerId);
				p.inventory.selector = 9;	
			}
		});
	}
	
	public static void update(){
		for(Action a : actions){
			if(KeyboardListener.getKeyPressed().equalsIgnoreCase(a.key)){
				a.run();
			}
		}
	}
}
