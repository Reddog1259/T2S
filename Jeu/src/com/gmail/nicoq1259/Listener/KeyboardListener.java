package com.gmail.nicoq1259.Listener;

import org.lwjgl.input.Keyboard;

public class KeyboardListener {
	static String keypress;
	static String keyrelease;
	static String keydown;
	
	public static String getKeyPressed(){
		return keypress;
	}
	
	public static String getKeyRelease(){
		return keyrelease;
	}
	
	public static String getKeyDown(){
		return keydown;
	}
	
	public static void update(){
		keypress = "";
		keyrelease = "";
		if(Keyboard.next()){
			if(Keyboard.getEventKeyState()){
				keypress = Keyboard.getKeyName(Keyboard.getEventKey());
				keydown = Keyboard.getKeyName(Keyboard.getEventKey());
			}else {
				keydown = "";
				keyrelease = Keyboard.getKeyName(Keyboard.getEventKey());
			}
		}
	}

}
