package cat.atridas87.ld26.gameobjects;

import org.lwjgl.opengl.GL11;

import cat.atridas87.ld26.render.Model;

public class Battleground {
	private Model model;
	
	public Battleground() {
		
		float positions[] = {0,600,0,   600,600,0, 600,0,0, 0,0,0, 
				550,550,0, 50,50,0, 50,400,0, 100,450,0,
				150,500,0, 200,550,0, 400,50,0, 450,100,0, 500,150,0, 550,200,0
				};
		float colors[]    = {0,1,0,1,  1,0.5f,0,1, 1,0,1,1, 0,0.5f,1,0,
				1,0.5f,0,1, 0,0.5f,1,1,
				0,1,0,1, 0,1,0,1, 0,1,0,1, 0,1,0,1, 
				1,0,1,1, 1,0,1,1, 1,0,1,1, 1,0,1,1};
		
		short indexs[] = {0,1, 1,2, 2,3, 3,0, 4,13, 13,12, 12,8, 8,9, 9,4, 10,11, 11,7, 7,6, 6,5, 5,10};
		
		model = new Model(positions, colors, indexs, GL11.GL_LINES);
	}
	
	public void render() {
		model.draw();
	}
	
}
