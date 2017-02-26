package com.mygdx.sreenze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ApplicationCore extends Game {
	SpriteBatch batch;
	OrthographicCamera camera;
	BitmapFont font;

	//public static final float WIDTH = 1280;
	public static final float WIDTH = 1920;
	//public static final float HEIGHT = 720;
	public static final float HEIGHT = 1080;

	private Skin skin;

	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		font = new BitmapFont(Gdx.files.internal("misc/laser_rod.fnt"));
		skin = new Skin(Gdx.files.internal("defaultskin/defaultskin.json"));
		this.setScreen(new LoadingScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		this.getScreen().dispose();
	}

	public Skin getSkin(){
		return skin;
	}
}
