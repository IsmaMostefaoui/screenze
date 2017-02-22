package com.mygdx.sreenze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class BoardScreen implements Screen{


    Stage stage;
    final ApplicationCore app;
    TiledMap map;
    OrthogonalTiledMapRenderer orthMap;
    int count = 0;

    public BoardScreen(final ApplicationCore app){
        this.app = app;
        stage = new Stage(new FitViewport(ApplicationCore.WIDTH, ApplicationCore.HEIGHT, app.camera));
        map = new TmxMapLoader().load("levels/sans-titre.tmx");
        orthMap = new OrthogonalTiledMapRenderer(map);
        ((OrthographicCamera) stage.getCamera()).translate((-stage.getWidth()/2)+75*4, (-stage.getHeight()/2)+75*4);
        System.out.println(stage.getCamera().position);
        
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        stage.act(delta);
        stage.getCamera().update();
        orthMap.setView((OrthographicCamera)stage.getCamera());
        orthMap.render();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
