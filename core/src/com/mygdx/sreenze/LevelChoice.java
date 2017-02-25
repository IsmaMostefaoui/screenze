package com.mygdx.sreenze;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

public class LevelChoice implements Screen {

    final ApplicationCore app;
    Stage stage;

    Color basicButtonColor;
    TextButton backButton;
    Label nameOfScreen;

    Table levelTable;

    int numberOfLevel;
    int levelByRow;
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

        nameOfScreen = new Label("Welcome to the Level Choice", this.app.getSkin());
        nameOfScreen.setPosition(ApplicationCore.WIDTH/2-nameOfScreen.getWidth(), 4*ApplicationCore.HEIGHT/5);
        nameOfScreen.setStyle(
                new Label.LabelStyle(new BitmapFont(Gdx.files.internal("misc/laser_rod.fnt")),
                        Color.BLACK));
        stage.addActor(nameOfScreen);

        levelTable = new Table();
        levelTable.setWidth(stage.getWidth());
        levelTable.align(Align.center|Align.top);

        levelList = new ArrayList<TextButton>();
        numberOfLevel = 10;
        levelByRow = 5;
        for (int i = 0; i < numberOfLevel; i++){
            if (i < 9){
                levelList.add(new TextButton(0+""+(i+1), app.getSkin()));
            }else{
                levelList.add(new TextButton(""+(i+1), app.getSkin()));
            }
            levelList.get(i).addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    LevelChoice.this.app.setScreen(new BoardScreen(LevelChoice.this.app, "levels/levelTest.tmx"));
                }
            });
            //levelList.get(i).setPosition(25 + (i*LVL_BTN_WIDTH+stage.getWidth()/numberOfLevel),
                        //stage.getHeight()/2-LVL_BTN_HEIGHT/2);
            initialize(levelList.get(i), i);
        }
        levelTable.setPosition(0, Gdx.graphics.getHeight());
        levelTable.padTop((stage.getHeight()/2)-((numberOfLevel/levelByRow) + (numberOfLevel-1)*MainMenuScreen.PAD_HEIGHT)/2);
        stage.addActor(levelTable);

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
     */
    private void initialize(TextButton button, int lvl){
        if (lvl % levelByRow == 0){
            levelTable.row();
        }
        levelTable.add(button).padBottom(MainMenuScreen.PAD_HEIGHT).padRight(MainMenuScreen.PAD_HEIGHT)
                .width(LVL_BTN_WIDTH).height(LVL_BTN_HEIGHT);
    }

    public void goToLevel(String level){
        LevelChoice.this.app.setScreen(new BoardScreen(LevelChoice.this.app, level));
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.160f, .300f, 1f, .7f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        this.update(delta);

        MainMenuScreen.previewButton(backButton, basicButtonColor);
        MainMenuScreen.pressedButton(backButton);

        app.batch.begin();
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
