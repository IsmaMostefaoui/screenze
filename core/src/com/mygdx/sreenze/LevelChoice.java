package com.mygdx.sreenze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

public class LevelChoice implements Screen {

    final ApplicationCore app;
    Stage stage;

    Color basicButtonColor;
    TextButton backButton;

    Table levelTable;

    int numberOfLevel;
    ArrayList<TextButton> levelList;

    final int LVL_BTN_WIDTH = 50;
    final int LVL_BTN_HEIGHT = 50;

    /**
     * Default constructor
     * @param app The application core, where the level choice has been initiate
     */
    //need a controller to pick the number of level available in the game and print them properly on the screen
    public LevelChoice(final ApplicationCore app){
        this.app = app;
        this.stage = new Stage(new FitViewport(app.WIDTH, app.HEIGHT, app.camera));
        Gdx.input.setInputProcessor(stage);

        levelList = new ArrayList<TextButton>();
        numberOfLevel = 5;
        for (int i = 0; i < numberOfLevel; i++){
            levelList.add(new TextButton(0+""+(i+1), app.getSkin()));
            levelList.get(i).setPosition(25 + (i*LVL_BTN_WIDTH+stage.getWidth()/numberOfLevel),
                        stage.getHeight()/2-LVL_BTN_HEIGHT/2);
            levelList.get(i).setWidth(LVL_BTN_WIDTH);
            levelList.get(i).setHeight(LVL_BTN_HEIGHT);
            stage.addActor(levelList.get(i));
        }

        //init back button
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

    /**
     * Initialize a button at the position (x, y) with width and height by default (BUTTON_WIDTH and BUTTON_HEIGHà
     * @param button the button to initialize
     * @param x the coordinate x of the position
     * @param y the coordinate y of the position
     */
    private void initialize(TextButton button, float x, float y){
        button.setPosition(x, y);
        levelTable.add(button).padBottom(MainMenuScreen.PAD_HEIGHT)
                .width(MainMenuScreen.BUTTON_WIDTH).height(MainMenuScreen.BUTTON_HEIGHT);
        //passe a la ligne de la table pour ajouter le prochain bouton
        levelTable.row();
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
