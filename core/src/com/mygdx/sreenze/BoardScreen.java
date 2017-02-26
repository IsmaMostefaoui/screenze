package com.mygdx.sreenze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import java.util.ArrayList;

public class BoardScreen implements Screen{


    Stage stage;
    final ApplicationCore app;
    TiledMap map;
    OrthogonalTiledMapRenderer orthMap;

    ArrayList<Image> boardPiece;
    List<Image>playerPiece;

    int tileSize;

    DragListener dl;

    public BoardScreen(final ApplicationCore app, String level){
        this.app = app;
        this.boardPiece = new ArrayList<>();
        this.boardPiece.add(new Image(new Texture("banana.png")));
        this.tileSize = 75;

        this.dl = new MyDragListener();

        this.stage = new Stage(new FitViewport(ApplicationCore.WIDTH, ApplicationCore.HEIGHT, app.camera));
        this.boardPiece.get(0).addListener(dl);
        this.map = new TmxMapLoader().load(level);
        this.orthMap = new OrthogonalTiledMapRenderer(map);
        ((OrthographicCamera) stage.getCamera()).translate((-stage.getWidth()/2)+75*4, (-stage.getHeight()/2)+75*4);
        System.out.println(stage.getCamera().position);

        this.stage.addListener(new InputListener(){
            @Override
            public boolean keyTyped(InputEvent e, char c){
                if (c == 'q' || c == 'Q'){
                    BoardScreen.this.app.setScreen(new LevelChoice(BoardScreen.this.app));
                }
                return true;
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        for (int i=0; i < boardPiece.size(); i++){
            boardPiece.get(i).setSize(75,75);
            stage.addActor(boardPiece.get(i));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.160f, .300f, 1f, .7f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthMap.setView((OrthographicCamera)stage.getCamera());
        orthMap.render();

        stage.draw();
        stage.act(delta);
        stage.getCamera().update();

        //MainMenuScreen.previewButton(backButton, basicButtonColor);
        //MainMenuScreen.pressedButton(backButton);
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

    class MyDragListener extends DragListener{

        private MyDragListener(){
            this.setTapSquareSize(BoardScreen.this.tileSize);
        }

        @Override
        public void touchDragged(InputEvent e, float x, float y, int pointer){
            //System.out.println("(" + x + ", " + y + ")");
            boardPiece.get(0).moveBy(x, y);
        }

        @Override
        public boolean touchDown(InputEvent e, float x, float y, int pointer, int button){
            return true;
        }

        @Override
        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            float xPos= (float)Math.floor(boardPiece.get(0).getX()/75);
            float yPos = (float)Math.floor(boardPiece.get(0).getY()/75);
            boardPiece.get(0).setPosition(xPos*75,  yPos*75);
        }
    }
}
