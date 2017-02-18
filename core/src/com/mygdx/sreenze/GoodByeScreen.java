package com.mygdx.sreenze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class GoodByeScreen implements Screen{

    Stage stage;
    Label goodbye;

    final ApplicationCore app;

    public GoodByeScreen(final ApplicationCore app){
        this.app = app;
        stage = new Stage(new FitViewport(app.WIDTH, app.HEIGHT, app.camera));
        Gdx.input.setInputProcessor(stage);
        goodbye = new Label("GoodBye !", this.app.getSkin());
        stage.addActor(goodbye);
        goodbye.setPosition(stage.getWidth()/2 - goodbye.getWidth()/2, stage.getHeight()/2 - goodbye.getHeight());
        goodbye.addAction(sequence(alpha(0f), fadeIn(2f)));

    }

    @Override
    public void show() {
        System.exit(0);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.update(delta);
        stage.draw();
    }

    public void update(float delta){
        stage.act(delta);
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
