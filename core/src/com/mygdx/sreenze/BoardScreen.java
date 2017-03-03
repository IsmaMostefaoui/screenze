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
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import sun.security.krb5.internal.APOptions;

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
        this.boardPiece = new ArrayList<Image>();
        this.boardPiece.add(new Image(new Texture("banana.png")));
        this.tileSize = 75;

        this.dl = new MyDragListener();

        this.stage = new Stage(new FitViewport(ApplicationCore.WIDTH, ApplicationCore.HEIGHT, app.camera));
        this.boardPiece.get(0).addListener(dl);
        this.map = new TmxMapLoader().load(level);
        this.orthMap = new OrthogonalTiledMapRenderer(map);
        ((OrthographicCamera) stage.getCamera()).translate((-stage.getWidth()/2)+tileSize*4, (-stage.getHeight()/2)+tileSize*4);
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
            boardPiece.get(i).setSize(tileSize,tileSize);
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

        float fromX = -1;
        float fromY = -1;

        private MyDragListener(){
            this.setTapSquareSize(BoardScreen.this.tileSize);
        }

        @Override
        public void touchDragged(InputEvent e, float x, float y, int pointer){
            //System.out.println("(" + x + ", " + y + ")");
            boardPiece.get(0).moveBy(x- boardPiece.get(0).getWidth()/2, y- boardPiece.get(0).getHeight()/2);
        }

        @Override
        public boolean touchDown(InputEvent e, float x, float y, int pointer, int button){
            fromX = (float)Math.floor((boardPiece.get(0).getX()+ boardPiece.get(0).getWidth()/2)/tileSize);
            fromY = (float)Math.floor((boardPiece.get(0).getY()+ boardPiece.get(0).getHeight()/2)/tileSize);
            boardPiece.get(0).addAction(Actions.scaleBy(.1f,.1f,.2f, Interpolation.fade));
            return true;
        }

        @Override
        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            boardPiece.get(0).addAction(Actions.scaleBy(-.1f,-.1f,.2f, Interpolation.fade));

            float xPos= (float)Math.floor((boardPiece.get(0).getX()+ boardPiece.get(0).getWidth()/2)/tileSize);
            float yPos = (float)Math.floor((boardPiece.get(0).getY()+ boardPiece.get(0).getHeight()/2)/tileSize);
            if (fromX != -1 && fromY != -1) {
                if(xPos < 0 || yPos < 0 || xPos > 7 || yPos >7) {
                    boardPiece.get(0).addAction(Actions.moveTo(fromX*tileSize,  fromY*tileSize, .2f, Interpolation.fade));
                } else {
                    boardPiece.get(0).addAction(Actions.moveTo(xPos*tileSize,  yPos*tileSize, .2f, Interpolation.fade));
                }
            }
            fromX = -1;
            fromY = -1;
        }
    }
}
