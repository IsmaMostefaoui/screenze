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

public class ExtensionScreen implements Screen {

    private static ExtensionScreen instance;

    final ApplicationCore app;
    private Stage stage;

    private CheckBox colorExtensionBox;
    private CheckBox loginExtensionBox;
    private CheckBox aiExtensionBox;
    private TextButton backButton;

    private Label descColor;

    private Color basicButtonColor;

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
        colorExtensionBox.setPosition((ApplicationCore.WIDTH/9),
                (3*(ApplicationCore.HEIGHT-backButton.getHeight())/4 - colorExtensionBox.getHeight())+backButton.getHeight());
        stage.addActor(colorExtensionBox);

        descColor = new Label("This game extension allows for lasers beams with different colours and intensities." +
                "The basic laser colours are red, green and blue. They require a specific type of source block emitting" +
                "laser beams of that particular colour. There is also a white laser beam, that is emitted by a multicolour" +
                "source block. A white laser beam actually carries the three colour components (red, green and blue) and can" +
                "be processed by any block accepting the corresponding colour."+
                "Laser beams become less intense in function of the distance they have travelled. The intensity of a" +
                "laser beam (a value between 0 and 100). Blocks do" +
                "not process laser beams with an intensity lower than a minimal threshold of 10% of its maximal" +
                "value. To succeed the level, target blocks also require the incoming laser beam to have at least the minimal" +
                "threshold for its intensity. Adding amplifers blocks, reducing blocks, filters blocks and converters blocks", app.getSkin());
        descColor.setWidth(7*stage.getWidth()/9);
        descColor.setPosition(ApplicationCore.WIDTH/9,
                3*(ApplicationCore.HEIGHT-backButton.getHeight())/4 - 4*descColor.getHeight()+backButton.getHeight());
        descColor.setWrap(true);
        stage.addActor(descColor);

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
        Gdx.gl.glClearColor(.160f, .300f, 1f, .7f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        this.update(delta);
        //updateCheckBoxText();

        MainMenuScreen.previewButton(backButton, basicButtonColor);
        MainMenuScreen.pressedButton(backButton);
    }

    /**
     * Allow one only one instance of the Extension Screen.
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
