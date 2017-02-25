package com.mygdx.sreenze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.w3c.dom.html.HTMLLabelElement;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class LoadingScreen implements Screen{

    final ApplicationCore app;

    private Stage stage;
    private Image background;
    private TextButton backButton;
    private Color basicButtonColor;
    Label homeText;

    public LoadingScreen(final ApplicationCore app){
        this.app = app;
        stage = new Stage(new FitViewport(ApplicationCore.WIDTH, ApplicationCore.HEIGHT, app.camera));
        background = new Image(new Texture(Gdx.files.internal("background_laser.jpg")));
        homeText = new Label("Click to begin !",
                new Label.LabelStyle(new BitmapFont(Gdx.files.internal("misc/laser_rod.fnt")), Color.WHITE));
        System.out.println(homeText.getWidth()/2);
        homeText.setPosition(ApplicationCore.WIDTH/2-(homeText.getWidth()/2), ApplicationCore.HEIGHT/3);

        stage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x , float y){
                LoadingScreen.this.app.setScreen(new MainMenuScreen(LoadingScreen.this.app));
            }
        });
        stage.addActor(background);
        stage.addActor(homeText);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        animation();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));

        stage.act(delta);
        stage.draw();
    }

    private void animation() {
        background.addAction(sequence(alpha(0f), fadeIn(.5f)));
        homeText.addAction(sequence(alpha(0f), fadeIn(.7f), fadeOut(.7f), fadeIn(.7f), fadeOut(.7f),
                fadeIn(.7f), fadeOut(.7f), fadeIn(.7f), fadeOut(.7f), fadeIn(.7f), fadeOut(.7f),
                fadeIn(.7f), fadeOut(.7f), fadeIn(.7f), fadeOut(.7f), fadeIn(.7f), fadeOut(.7f)));
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
