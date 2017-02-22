package com.mygdx.sreenze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sun.glass.ui.Window;
import com.sun.javaws.Main;

public class ExtensionScreen implements Screen {

    private static ExtensionScreen instance;

    final ApplicationCore app;
    Stage stage;

    CheckBox colorExtensionBox;
    CheckBox loginExtensionBox;
    CheckBox aiExtensionBox;
    TextButton backButton;

    Color basicButtonColor;

    /**
     * Default Constructor
     * Initialize the Extension Screen
     * @param app The Application core. The base of all screen.
     */
    private ExtensionScreen(final ApplicationCore app){
        this.app = app;
        this.stage = new Stage(new FitViewport(app.WIDTH, app.HEIGHT, app.camera));

        backButton = new TextButton("Back", this.app.getSkin());

        basicButtonColor = new Color(backButton.getColor());

        backButton.setWidth(MainMenuScreen.BUTTON_WIDTH);
        backButton.setHeight(MainMenuScreen.BUTTON_HEIGHT);
        stage.addActor(backButton);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ExtensionScreen.this.app.setScreen(new MainMenuScreen(ExtensionScreen.this.app));
            }
        });

        colorExtensionBox = new CheckBox("Activate color extension", this.app.getSkin());
        //TODO Factoriser les positions
        colorExtensionBox.setPosition((this.app.WIDTH/4),
                (3*(this.app.HEIGHT-backButton.getHeight())/4 - colorExtensionBox.getHeight())+backButton.getHeight());
        stage.addActor(colorExtensionBox);
        Label descColor;

        loginExtensionBox = new CheckBox("Activate login extension", this.app.getSkin());
        loginExtensionBox.setPosition((this.app.WIDTH/4),
                (2*(this.app.HEIGHT-backButton.getHeight())/4 - loginExtensionBox.getHeight())+backButton.getHeight());
        stage.addActor(loginExtensionBox);
        aiExtensionBox = new CheckBox("Activate artificial intelligence extension", this.app.getSkin());
        aiExtensionBox.setPosition((this.app.WIDTH/4),
                (1*(this.app.HEIGHT-backButton.getHeight())/4 - aiExtensionBox.getHeight())+backButton.getHeight());
        stage.addActor(aiExtensionBox);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        this.update(delta);

        updateCheckBoxText();

        MainMenuScreen.previewButton(backButton, basicButtonColor);
        MainMenuScreen.pressedButton(backButton);
    }

    /**
     * Allow one only instance of the Extension Screen.
     * @param app
     * @return
     */
    static ExtensionScreen getExtensionScreen(final ApplicationCore app){
        if (instance == null){
            instance = new ExtensionScreen(app);
        }
        return instance;
    }

    /**
     * Set the text of checkBox that are present on the screen
     */
    private void updateCheckBoxText() {
        if (loginExtensionBox.isChecked()) loginExtensionBox.setText("Inactivate login extension");
        if (aiExtensionBox.isChecked()) aiExtensionBox.setText("Inactivate artificial intelligence extension");
        if (colorExtensionBox.isChecked()) colorExtensionBox.setText("Inactivate color extension");

        if (!loginExtensionBox.isChecked()) loginExtensionBox.setText("Activate login extension");
        if (!aiExtensionBox.isChecked()) aiExtensionBox.setText("Activate artificial intelligence extension");
        if (!colorExtensionBox.isChecked()) colorExtensionBox.setText("Activate color extension");
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
