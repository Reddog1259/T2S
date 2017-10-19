package com.gmail.nicoq1259.game;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import com.gmail.nicoq1259.component;
import com.gmail.nicoq1259.entity.Entity;
import com.gmail.nicoq1259.entity.Player;
import com.gmail.nicoq1259.game.world.Chunk;
import com.gmail.nicoq1259.game.world.Cube;
import com.gmail.nicoq1259.game.world.World;
import com.gmail.nicoq1259.inventory.Craft;
import com.gmail.nicoq1259.inventory.Loot;
import com.gmail.nicoq1259.render.Camera;
import com.gmail.nicoq1259.render.Text;
import com.gmail.nicoq1259.scene.Scene;

public class Game extends Scene implements Runnable {
	public static World world = new World();
	public static boolean DEBUG = false;
	public static int playerId = 0;
	public static boolean openCraft = false;
	public static GameFile SAVE = new GameFile("test");	
	
	public Game(){
		(new Thread(this)).start();
	}
	
	public void render(){
		if(World.state == 10){
			Camera.moveCamera();
			
			world.renderBackground();
			
			Entity.renders();
			world.render();
			Hud.render(new Vector2f(-Camera.getPosition().x, -Camera.getPosition().y));
			if(openCraft){
				CraftingHUD.render(new Vector2f((-Camera.getPosition().x + (component.width - CraftingHUD.sizeX) / 2), (-Camera.getPosition().y) + (component.height - CraftingHUD.sizeY) / 2));
			}
		}else if(World.state == 1){
			Text.renderTexte("Chargement", -Camera.getPosition().x + (-"Chargement".length() * 8 * 5 + component.width) / 2, -Camera.getPosition().y + component.height - 75, 5);
			Text.renderTexte("Creation de la map", -Camera.getPosition().x + (-"Creation de la map".length() * 8 * 3 + component.width) / 2, -Camera.getPosition().y + component.height - 150, 3);
			Text.renderTexte(World.progression + "%", -Camera.getPosition().x + (-(World.progression + "%").length() * 8 * 3 + component.width) / 2, -Camera.getPosition().y + component.height - 190, 3);
		}else if(World.state == 2){
			Text.renderTexte("Chargement", -Camera.getPosition().x + (-"Chargement".length() * 8 * 5 + component.width) / 2, -Camera.getPosition().y + component.height - 75, 5);
			String desc = "Preparation pour les structures";
			Text.renderTexte(desc, -Camera.getPosition().x + (-desc.length() * 8 * 3 + component.width) / 2, -Camera.getPosition().y + component.height - 150, 3);
			Text.renderTexte(World.progression + "%", -Camera.getPosition().x + (-(World.progression + "%").length() * 8 * 3 + component.width) / 2, -Camera.getPosition().y + component.height - 190, 3);
		}else if(World.state == 3){
			Text.renderTexte("Chargement", -Camera.getPosition().x + (-"Chargement".length() * 8 * 5 + component.width) / 2, -Camera.getPosition().y + component.height - 75, 5);
			String desc = "Dieu créa l'eau";
			Text.renderTexte(desc, -Camera.getPosition().x + (-desc.length() * 8 * 3 + component.width) / 2, -Camera.getPosition().y + component.height - 150, 3);
			Text.renderTexte(World.progression + "%", -Camera.getPosition().x + (-(World.progression + "%").length() * 8 * 3 + component.width) / 2, -Camera.getPosition().y + component.height - 190, 3);
		}else if(World.state == 4){
			Text.renderTexte("Chargement", -Camera.getPosition().x + (-"Chargement".length() * 8 * 5 + component.width) / 2, -Camera.getPosition().y + component.height - 75, 5);
			String desc = "Generation des structures";
			Text.renderTexte(desc, -Camera.getPosition().x + (-desc.length() * 8 * 3 + component.width) / 2, -Camera.getPosition().y + component.height - 150, 3);
			Text.renderTexte(World.progression + "%", -Camera.getPosition().x + (-(World.progression + "%").length() * 8 * 3 + component.width) / 2, -Camera.getPosition().y + component.height - 190, 3);
		}
	}

	
	public void update(){
		
		if(World.state == 10){
			world.update();
			Entity.updates();
			if(!Mouse.isGrabbed() && !openCraft){
				Entity.getPlayer().handOk = false;
				Mouse.setGrabbed(true);
			}
			if(openCraft){
				if(Mouse.isGrabbed()){
					Mouse.setCursorPosition(component.width / 2, component.height / 2);
					Mouse.setGrabbed(false);
				}
				CraftingHUD.update(new Vector2f((-Camera.getPosition().x + (component.width - CraftingHUD.sizeX) / 2), (-Camera.getPosition().y) + (component.height - CraftingHUD.sizeY) / 2));
			}else{
				Control.update();
			}
		}else{
			if(World.state == 9){
				for(int i = 0; i < World.mapSize / Chunk.sizeX; i++){	
					world.chunks[i].update();
				}
				playerId = (int) Entity.add(new Player(100, world.getTopperFromTop(100 / Cube.size) * Cube.size));
				Loot.initCubeLoot();
				Craft.init();
				SAVE.loadAll();
				for(int i = 0; i < World.mapSize / Chunk.sizeX; i++){	
					world.chunks[i].updateChunk();
				}
				World.state = 10;
			}
		}
	}

	@Override
	public void run() {
		world.createRandomWorld(4456);
	}
}
