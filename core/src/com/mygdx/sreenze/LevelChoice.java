package com.mygdx.sreenze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sun.glass.ui.Window;
import com.sun.javaws.Main;

public class LevelChoice implements Screen {

    final ApplicationCore app;
    Stage stage;

    Color basicButtonColor;
    TextButton backButton;

    public LevelChoice(final ApplicationCore app){
        this.app = app;
        this.stage = new Stage(new FitViewport(app.WIDTH, app.HEIGHT, app.camera));
        Gdx.input.setInputProcessor(stage);

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
                LevelChoice.this.app.setScreen(new MainMenuScreen(LevelChoice.this.app));
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
        this.update(delta);

        MainMenuScreen.previewButton(backButton, basicButtonColor);
        MainMenuScreen.pressedButton(backButton);

        app.batch.begin();
        app.font.draw(app.batch, "Bienvenue dans le choix de niveau", app.WIDTH/2, app.HEIGHT/2);
        app.batch.end();
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
