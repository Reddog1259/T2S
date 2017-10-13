package com.gmail.nicoq1259.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class ConfigFile {
	private File file;
	private ArrayList<param> arg = new ArrayList<param>();

	public ConfigFile(String path, String file){
		if(!(new File(path)).exists())
			(new File(path)).mkdirs();
		this.file = new File(path + file);
		if(this.file.exists())
			load();
	}

	private boolean load(){
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(file));
			String m = reader.readLine();
			do{
				String ct[] = m.split(":");
				arg.add(new param(ct[0], ct[1]));
				m = reader.readLine();
			}while(m != null);
			reader.close();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public boolean save(){
		BufferedWriter writer = null;
		try{
			writer = new BufferedWriter(new FileWriter(file));
			for(param m : arg){
				writer.write(m.a + ":" + m.stuff);
				writer.newLine();
			}
			writer.close();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public Object get(String p){
		for(param m : arg){
			if(p.equalsIgnoreCase(m.a))
				return m.stuff;
		}
		return "ERREUR";
	}
	
	public void set(String p, Object s){
		boolean isok = false;
		for(param m : arg){
			if(p.equalsIgnoreCase(m.a)){
				m.stuff = s;
				isok = true;
			}
		}
		if(!isok)
			arg.add(new param(p, s));
	}
	
	public boolean exist(){
		return file.exists();
	}

	public class param {
		public String a;
		public Object stuff;
		public param(String a, Object s){
			this.a = a;
			this.stuff = s;
		}
	}
}
