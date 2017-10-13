package com.gmail.nicoq1259.scene;

import com.gmail.nicoq1259.game.Action;
import com.gmail.nicoq1259.game.Game;

public class Scene {
	public static Scene instance = new Scene();
	
	public static Game game;
	public static PauseMenu pause;
	
	public static void init(){
		Action.init();
		game = new Game();
		pause = new PauseMenu();
	}
	
	
	
	
	
	public void render(){
	}
	
	public void update(){
	}
}
