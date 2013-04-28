package cat.atridas87.ld26.gameobjects;

import java.nio.FloatBuffer;

import javax.vecmath.Vector2f;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import cat.atridas87.ld26.HUD;
import cat.atridas87.ld26.render.ShaderManager;

public class Formation {

	public final int numBots[];
	public final int price;
	
	public final Vector2f position;
	
	private static final FloatBuffer fb;
	private static final Bot bots[];
	
	public Formation(int _numBots[], int _price, Vector2f _position) {
		numBots = new int[Bot.Type.values().length];
		for(int i = 0; i < Bot.Type.values().length; i++) {
			numBots[i] = _numBots[i];
		}
		
		price = _price;
		
		position = new Vector2f(_position);
		
	}
	
	static {	
		float vb[] = {0,0, 80,0, 
				80,0, 80,80,
				80,80, 0,80,
				0,80, 0,0};

		fb = BufferUtils.createFloatBuffer(vb.length);
		for (int i = 0; i < vb.length; i++) {
			fb.put(vb[i]);
		}
		fb.flip();
		
		
		bots = new Bot[Bot.Type.values().length];
		for(int i = 0; i < Bot.Type.values().length; i++) {
			bots[i] = new Bot(true, Bot.Type.values()[i], Lane.MIDDLE);
		}
	}
	
	
	public void render(int _money) {
		if(_money >= price) {
			ShaderManager.instance.setColor(0, 1, 0, 1);
		} else {
			ShaderManager.instance.setColor(0, 0, 0, 1);
		}

		ShaderManager.instance.setPosition(position.x, position.y);

		GL20.glEnableVertexAttribArray(ShaderManager.POSITION_ATTRIBUTE);
		GL20.glVertexAttribPointer(ShaderManager.POSITION_ATTRIBUTE, 2, false,
				2 * 4, fb);
		
		GL11.glDrawArrays(GL11.GL_LINES, 0, fb.capacity() / 2);
		
		HUD.renderText(Integer.toString(price), position.x + 10, position.y + 10);

		int botPosX = 10;
		int botPosY = 70;

		GL11.glLineWidth(1);
		GL11.glPointSize(5);
		for(int t = 0; t < Bot.Type.values().length; t++) {
			for(int i = 0; i < numBots[t]; i++) {
				bots[t].position.x = botPosX + position.x;
				bots[t].position.y = botPosY + position.y;
				bots[t].render();
				
				botPosX += 10;
				while(botPosX > 70) {
					botPosX -= 70;
					botPosY -= 10;
				}
			}
		}
		
	}
	
}
