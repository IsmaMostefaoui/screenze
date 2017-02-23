package com.mygdx.sreenze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainMenuScreen implements Screen{

    final ApplicationCore app;

    private Stage stage;
    Table buttonTable;
    private Image img;

    private final int WIDTH_IMG = 250;
    private final int HEIGHT_IMG = 150;

    static final int BUTTON_WIDTH = 200;
    static final int BUTTON_HEIGHT = 50;

    static final int PAD_HEIGHT = 10;

    TextButton playButton, practiceButton, loadButton, extensionButton, helpButton, quitButton;
    Color colorNotOver;

    /**
     * Default Constructor
     * Initialize the Main Menu Screen
     * @param app The Application core. The base of all screen.
     */
    MainMenuScreen(final ApplicationCore app){
        this.app = app;
        this.stage = new Stage(new FitViewport(ApplicationCore.WIDTH, ApplicationCore.HEIGHT, app.camera));

        Gdx.input.setInputProcessor(stage);

        img = new Image(new Texture(Gdx.files.internal("banana.png")));
        img.setWidth(WIDTH_IMG);
        img.setHeight(HEIGHT_IMG);
        stage.addActor(img);

        //chargement du skin par defaut pour les boutons
        ToLevelScreenListener tlsl = new ToLevelScreenListener();

        //mise en place du layout pour aligner les boutons
        buttonTable = new Table();
        buttonTable.setWidth(stage.getWidth());
        buttonTable.align(Align.center|Align.top);

        //création des boutons
        playButton = new TextButton("PLAY", this.app.getSkin());
        this.initialize(playButton);
        playButton.addListener(tlsl);

        //recup color de base
        colorNotOver = new Color(playButton.getColor());

        practiceButton = new TextButton("PRACTICE", this.app.getSkin());
        this.initialize(practiceButton);
        practiceButton.addListener(tlsl);

        loadButton = new TextButton("LOAD", this.app.getSkin());
        this.initialize(loadButton);
        loadButton.addListener(tlsl);

        extensionButton = new TextButton("EXTENSIONS", this.app.getSkin());
        this.initialize(extensionButton);
        extensionButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MainMenuScreen.this.app.setScreen(ExtensionScreen.getExtensionScreen(MainMenuScreen.this.app));
            }
        });

        helpButton = new TextButton("HELP", this.app.getSkin());
        this.initialize(helpButton);
        helpButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.open(new File("../assets/misc/HelpDocument"));
                }catch (IOException e){
                    MainMenuScreen.this.app.font.draw(MainMenuScreen.this.app.batch, "Aucune aide disponible !",
                            stage.getWidth()/2 - 20, stage.getHeight()/2);
                }
            }
        });

        quitButton = new TextButton("QUIT", this.app.getSkin());
        this.initialize(quitButton);
        quitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MainMenuScreen.this.app.setScreen(new GoodByeScreen(MainMenuScreen.this.app));
            }
        });

        buttonTable.setPosition(0, Gdx.graphics.getHeight());
        buttonTable.padTop((stage.getHeight()/2)-(6*BUTTON_HEIGHT + 5*PAD_HEIGHT)/2);
        stage.addActor(buttonTable);

    }

    /**
     * Initialize a button with width and height by default (BUTTON_WIDTH and BUTTON_HEIGH)
     * @param button the button to initialize
     */
    private void initialize(TextButton button){
        buttonTable.add(button).padBottom(PAD_HEIGHT).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);
        //passe a la ligne de la table pour ajouter le prochain bouton
        buttonTable.row();
    }

    @Override
    public void show() {
        animation(img);
    }

    /**
     * Make a certain animation for an image
     * @param image the image to move
     */
    private void animation(Image image) {
        image.setPosition(ApplicationCore.WIDTH / 2 - (WIDTH_IMG /2), ApplicationCore.HEIGHT + 100);
        //anmiation image apparition
        image.addAction(sequence(alpha(0f), scaleTo(.1f,.1f),
                parallel(fadeIn(2f, Interpolation.pow2),
                        scaleTo(2f, 2f, 2f, Interpolation.pow5),
                        moveTo(stage.getWidth()/2-(WIDTH_IMG /2), stage.getHeight()/2-(HEIGHT_IMG),
                                1.5f, Interpolation.swing))));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));

        update(delta);
        stage.draw();

        previewButton(playButton, colorNotOver);
        previewButton(practiceButton, colorNotOver);
        previewButton(loadButton, colorNotOver);
        previewButton(extensionButton, colorNotOver);
        previewButton(helpButton, colorNotOver);
        previewButton(quitButton, colorNotOver);

        pressedButton(playButton);
        pressedButton(practiceButton);
        pressedButton(loadButton);
        pressedButton(extensionButton);
        pressedButton(helpButton);
        pressedButton(quitButton);

        app.batch.begin();
        //app.font.draw(app.batch, "Laser Challenge", ApplicationCore.WIDTH/2-app.font.getSpaceWidth(), 4*ApplicationCore.HEIGHT/5);
        app.batch.end();
    }

    /**
     * Allow the preveiw of all the button in the screen
     * @param button
     */
    static void previewButton(TextButton button, Color basicColor) {
        if (button.isOver()) button.setColor(.85f, .25f, .25f, 1f);
        if (!button.isOver()) button.setColor(basicColor);
    }

    static void pressedButton(TextButton button){
        if (button.isPressed()) button.setColor(.90f, .25f, .25f, 1f);
    }

    private void update(float delta){
        //don't know why
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

    public class ToLevelScreenListener extends ClickListener{

        @Override
        public void clicked(InputEvent event, float x, float y){
            MainMenuScreen.this.app.setScreen(new LevelChoice(MainMenuScreen.this.app));
        }
    }
}
