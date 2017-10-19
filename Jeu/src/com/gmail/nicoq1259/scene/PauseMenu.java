package com.gmail.nicoq1259.scene;



import org.lwjgl.input.Mouse;

import com.gmail.nicoq1259.component;
import com.gmail.nicoq1259.game.Game;
import com.gmail.nicoq1259.render.Camera;
import com.gmail.nicoq1259.render.Text;

public class PauseMenu extends Scene{

	public PauseMenu() {
	}
	
	public void update(){
		if(Mouse.isGrabbed()){
			Mouse.setGrabbed(false);
			Game.SAVE.saveAll();
		}
		Game.openCraft = false;
	}
	
	public void render(){
		Text.renderTexte("Pause", -Camera.getPosition().x + (-"Pause".length() * 8 * 5 + component.width) / 2, -Camera.getPosition().y + component.height - 8 * 5 - 10, 5);
	}

}
