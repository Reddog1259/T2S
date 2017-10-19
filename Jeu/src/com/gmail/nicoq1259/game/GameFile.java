package com.gmail.nicoq1259.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.gmail.nicoq1259.entity.Entity;
import com.gmail.nicoq1259.entity.Player;
import com.gmail.nicoq1259.game.world.Chunk;
import com.gmail.nicoq1259.game.world.Cube;
import com.gmail.nicoq1259.game.world.World;

public class GameFile {
	public String name;
	public ArrayList<Cube> modification = new ArrayList<Cube>();
	
	public GameFile(String name) {
		this.name = name;
	}
	
	public void loadAll(){
		File f = new File("/save/" + name);
		if(!f.exists())
			return;
		
		f = new File("/save/" + name + "/player");
		if(!f.exists())
			return;
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			Player p = (Player) ois.readObject();
			Player tempo = new Player(ois.readFloat(), ois.readFloat());
			tempo.inventory = p.inventory;
			Entity.entities.set(Game.playerId, tempo);
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		

		f = new File("/save/" + name + "/map");
		if(!f.exists())
			return;
		try {
			ObjectInputStream ois = null;
			for(int x = 0; x < World.mapSize; x++){
				if(x % Chunk.sizeX == 0){
					f = new File("/save/" + name + "/map/chunk" + (int) x / Chunk.sizeX);
					ois = new ObjectInputStream(new FileInputStream(f));
				}
				for(int y = 0; y < Chunk.sizeY; y++){
					Cube t = (Cube) ois.readObject();
					if(t != null)
						Game.world.setCube(x, y, t);
				}
				if(x % Chunk.sizeX == 9){
					ois.close();
				}
			}
			ois.close();
		} catch (IOException | ClassNotFoundException e) {}

	}
	
	public void saveChunk(Chunk c){
		File f;
		try {
			ObjectOutputStream oos = null;
			f = new File("/save/" + name + "/map/");
			if(!f.exists())
				f.mkdirs();
			f = new File("/save/" + name + "/map/chunk" + c.chunkId);
			oos = new ObjectOutputStream(new FileOutputStream(f));
			
			for(int x = 0; x < Chunk.sizeX; x++){
				for(int y = 0; y < Chunk.sizeY; y++){
					oos.writeObject(Game.world.getCube(c.chunkId * Chunk.sizeX + x, y));
				}
			}
			oos.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveAll(){
		File f = new File("/save/" + name);
		if(!f.exists())
			f.mkdirs();
		
		f = new File("/save/" + name + "/player");
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(Entity.getPlayer());
			oos.writeFloat(Entity.getPlayer().x);
			oos.writeFloat(Entity.getPlayer().y);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
