package com.mygdx.sreenze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class BoardScreen implements Screen{


    Stage stage;
    final ApplicationCore app;
    TiledMap map;
    OrthogonalTiledMapRenderer orthMap;

    TextButton backButton;
    Color basicButtonColor;

    public BoardScreen(final ApplicationCore app, String level){
        this.app = app;
        stage = new Stage(new FitViewport(ApplicationCore.WIDTH, ApplicationCore.HEIGHT, app.camera));
        map = new TmxMapLoader().load(level);
        orthMap = new OrthogonalTiledMapRenderer(map);
        ((OrthographicCamera) stage.getCamera()).translate((-stage.getWidth()/2)+75*4, (-stage.getHeight()/2)+75*4);
        System.out.println(stage.getCamera().position);

        backButton = new TextButton("Back", this.app.getSkin());

        basicButtonColor = new Color(backButton.getColor());

        backButton.setWidth(MainMenuScreen.BUTTON_WIDTH);
        backButton.setHeight(MainMenuScreen.BUTTON_HEIGHT);
        backButton.setPosition(backButton.getX()+stage.getWidth()/100,
                backButton.getY()+stage.getHeight()/100);
        stage.addActor(backButton);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                BoardScreen.this.app.setScreen(new MainMenuScreen(BoardScreen.this.app));
            }
        });
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

        MainMenuScreen.previewButton(backButton, basicButtonColor);
        MainMenuScreen.pressedButton(backButton);
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
