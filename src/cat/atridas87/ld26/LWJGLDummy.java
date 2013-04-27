/*
 * Copyright (c) 2002-2008 LWJGL Project
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'LWJGL' nor the names of
 *   its contributors may be used to endorse or promote products derived
 *   from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package cat.atridas87.ld26;

import java.nio.FloatBuffer;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import cat.atridas87.ld26.render.Model;
import cat.atridas87.ld26.render.ShaderManager;
import cat.atridas87.ld26.render.ShaderManager.ProgramType;

import static org.lwjgl.opengl.GL11.*;

/**
 * 
 * This is a <em>very basic</em> skeleton to init a game and run it.
 * 
 * @author $Author$
 * @version $Revision$ $Id$
 */
public class LWJGLDummy extends BaseGame {

	/** Game title */
	public static final String GAME_TITLE = "My Game";

	/** A rotating square! */
	private float angle;

	private Model m;

	private Matrix4f projection = new Matrix4f();
	private Matrix4f view = new Matrix4f();
	private Matrix4f model = new Matrix4f();

	/**
	 * No constructor needed - this class is static
	 */
	public LWJGLDummy(int width, int height) {
		super(width, height);
	}

	public void init() throws Exception {

		// Start up the sound system
		AL.create();

		// TODO: Load in your textures etc here
		ShaderManager.instance = new ShaderManager();

		float ps[] = { -0.5f, -0.5f, 0, 0.5f, -0.5f, 0, 0.5f, 0.5f, 0, -0.5f, 0.5f, 0, };
		float cs[] = {1,0,0,1, 0,1,0,1, 0,0,1,0, 1,1,1,1};
		
		short ix[] = {0,1,2, 0,2,3};
		
		m = new Model(ps, cs, ix, GL11.GL_TRIANGLES);

		FloatBuffer fbAux = BufferUtils.createFloatBuffer(16);

		// Put the window into orthographic projection mode with 1:1 pixel
		// ratio.
		// We haven't used GLU here to do this to avoid an unnecessary
		// dependency.
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0.0, Display.getDisplayMode().getWidth(), 0.0, Display
				.getDisplayMode().getHeight(), -1.0, 1.0);
		//fbAux.reset();
		glGetFloat(GL_PROJECTION_MATRIX, fbAux);
		projection.m00 = fbAux.get();
		projection.m01 = fbAux.get();
		projection.m02 = fbAux.get();
		projection.m03 = fbAux.get();
		projection.m10 = fbAux.get();
		projection.m11 = fbAux.get();
		projection.m12 = fbAux.get();
		projection.m13 = fbAux.get();
		projection.m20 = fbAux.get();
		projection.m21 = fbAux.get();
		projection.m22 = fbAux.get();
		projection.m23 = fbAux.get();
		projection.m30 = fbAux.get();
		projection.m31 = fbAux.get();
		projection.m32 = fbAux.get();
		projection.m33 = fbAux.get();
		
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glViewport(0, 0, width, height);

		view.setIdentity();
		model.setIdentity();
	}

	/**
	 * Do any game-specific cleanup
	 */
	public void cleanup() {
		// TODO: save anything you want to disk here

		// Stop the sound
		AL.destroy();

		// Close the window
		Display.destroy();
	}

	/**
	 * Do all calculations, handle input, etc.
	 */
	public void update() {
		// Example input handler: we'll check for the ESC key and finish the
		// game instantly when it's pressed
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			finished = true;
		}

		// TODO: all your game logic goes here.
		angle += 2.0f % 360;
	}

	/**
	 * Render the current frame
	 */
	public void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		
		model.setIdentity();
		model.setTranslation(new Vector3f(-0.5f, 0, -5));
		
		ShaderManager.instance.setCurrentProgram(ProgramType.TEXTURED);

		

		
		ShaderManager.instance.setProjectionMatrix(projection);
		ShaderManager.instance.setViewMatrix(view);
		ShaderManager.instance.setModelMatrix(model);
		
		
		m.draw();
		
		/*
		glPushMatrix();
		glTranslatef(Display.getDisplayMode().getWidth() / 2, Display
				.getDisplayMode().getHeight() / 2, 0.0f);
		glRotatef(angle, 0, 0, 1.0f);
		glBegin(GL_QUADS);
		glVertex2i(-50, -50);
		glVertex2i(50, -50);
		glVertex2i(50, 50);
		glVertex2i(-50, 50);
		glEnd();
		glPopMatrix();
		*/
	}

	@Override
	public String getWindowName() {
		// TODO Auto-generated method stub
		return null;
	}
}
