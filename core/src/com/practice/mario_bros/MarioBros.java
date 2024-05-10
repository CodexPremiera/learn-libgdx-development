package com.practice.mario_bros;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.practice.mario_bros.screens.PlayScreen;

public class MarioBros extends Game {
	public static final int V_WIDTH = 800;
	public static final int V_HEIGHT = 416;
	public static final float PPM = 100;

	public SpriteBatch spriteBatch;

	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}


