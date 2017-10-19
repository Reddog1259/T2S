package com.gmail.nicoq1259.game.world;

import java.io.Serializable;

import com.gmail.nicoq1259.render.Texture;

public enum CubeType implements Serializable{
//  nom(id, render)
	Air				(  0,  true, new float[]{0f, 0f, 0.6f, 1f},              false,   0.7f,    0f, false, false, new Texture("air.png")), 
	Dirt			(  1,  true, new float[]{0.53333f, 0.2588f, 0.1137f, 1f},  true,  0.5f,  0.8f,  true,  true, new Texture("dirt.png")),
	Grass			(  2,  true, new float[]{0.0f, 0.6f, 0f, 1f},              true,  0.5f, 0.85f,  true,  true, new Texture("grass.png")),
	Stone			(  3,  true, new float[]{0.1f, 0.1f, 1f, 1f},              true,  0.5f,    6f,  true,  true, new Texture("stone.png")),

	Ladder			( 10,  true, new float[]{0.1f, 0.1f, 1f, 1f},             false,  0.8f,    6f, false,  true, new Texture("ladder.png")),

	Wood			( 40,  true, new float[]{0.53333f, 0.2588f, 0.1137f, 1f},  true,  0.5f,    1f, false,  true, new Texture("wood.png")),
	
	Leaf			( 50,  true, new float[]{0.0f, 0.6f, 0f, 1f},              true,  0.5f,  0.1f, false,  true, new Texture("leaf.png")),
	
	Water			( 60,  true, new float[]{0.0f, 0.0f, 0.75f, 0.75f},       false,  0.8f,  999f, false,  true, new Texture("water.png")),
	
	Iron			( 70,  true, new float[]{0.1f, 0.1f, 1f, 1f},              true,  0.5f,   10f,  true,  true, new Texture("iron.png")),
	
	WoodenFloor		( 80,  true, new float[]{0.1f, 0.1f, 1f, 1f},              true,  0.5f,    6f,  true,  true, new Texture("woodenFloor.png")),
	
	Sappling		( 90,  true, new float[]{0.53333f, 0.2588f, 0.1137f, 1f},  true,  0.5f,    1f, false,  true, new Texture("sappling.png")),
	
	WoodenStair		(100,  true, new float[]{0.53333f, 0.2588f, 0.1137f, 1f},  true,  0.5f,    6f, true,  true, new Texture("woodenstair.png")),
	
	
	Unbreakable		(999,  true, new float[]{0.0f, 0.0f, 0f, 1f},              true,  0.5f,  999f,  true,  true, new Texture("unbreakable.png"));
	
	
	
	public int id = 0;
	boolean render = false;
	boolean solide = false;
	public float color[] = new float[]{1f, 1f, 1f, 1f};
	public float friction = 0f;
	public float resitance = 0f;
	
	public boolean textured = false;
	public Texture texture;
	public boolean foreground = true;
	public boolean liquide = false;
	
	CubeType(int id, boolean render, float color[], boolean solide, float friction, float resitance, boolean foreground, boolean textured, Texture texture){
		this.id = id;
		this.render = render;
		this.color = color;
		this.solide = solide;
		this.friction = friction;
		this.resitance = resitance;
		this.textured = textured;
		this.texture = texture;
		this.foreground = foreground;
		if(id >= 60 && id < 70)
			this.liquide = true;
		if(id == 10)
			this.liquide = true;	
	}
	
	public boolean isSolide(){
		return solide;
	}
	
	public boolean isLiquide(){
		if((id >= 60 && id < 70) || id == 10)
			return true;
		return false;
	}
	
	public static CubeType get(int id){
		for(CubeType t : CubeType.values()){
			if(id == t.id)
				return t;
		}
		return Air;
	}
	

	public boolean isLeaf(){
		return (id >= 50 && id < 60);
	}
	
	public boolean isStair(){
		return (id >= 100 && id < 110);
	}
	
	public boolean isWood(){
		return (id >= 40 && id < 50);
	}
	
	public boolean isPlatform(){
		return (id >= 80 && id < 90);
	}
	
	public boolean isSappling(){
		return (id >= 90 && id < 100);
	}
	
	public boolean isLadder(){
		return (id >= 10 && id < 20);
	}
}

