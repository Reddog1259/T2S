package com.gmail.nicoq1259.Listener;

import org.lwjgl.input.Mouse;

import com.gmail.nicoq1259.render.Camera;

public class MouseListener {
	static float x, y;
	static String Buttonpress;
	static String Buttonrelease;
	static String Buttondown;
	
	public static String getButtonPressed(){
		return Buttonpress;
	}
	
	public static String getButtonRelease(){
		return Buttonrelease;
	}
	
	public static String getButtonDown(){
		return Buttondown;
	}
	
	public static void update(){
		Buttonpress = "";
		Buttonrelease = "";
		x = Mouse.getX();
		y = Mouse.getY();
		if(Mouse.next()){
			if(Mouse.getEventButtonState()){
				Buttonpress = Mouse.getButtonName(Mouse.getEventButton());
				Buttondown = Mouse.getButtonName(Mouse.getEventButton());
			}else {
				Buttondown = "";
				Buttonrelease = Mouse.getButtonName(Mouse.getEventButton());
			}
		}
	}
	
	public static float getX(){
		return (float) x;
	}
	
	public static float getY(){
		return (float) y;
	}
	
	public static float getXonWorld(){
		return (float) x - Camera.getPosition().x;
	}
	
	public static float getYonWorld(){
		return (float) y - Camera.getPosition().y;
	}
}
