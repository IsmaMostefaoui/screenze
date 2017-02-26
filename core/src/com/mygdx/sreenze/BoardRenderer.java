package com.mygdx.sreenze;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sena.
 */
public class BoardRenderer extends Actor {
    List<BlockRenderer> blockRenderers = new ArrayList<>();

    public BoardRenderer(IBoard board) {

        this.board = board;
    }

    public void update() {
        for(BlockRenderer blockRenderer: blockRenderers) {
            blockRenderer.update();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        update();
        for(BlockRenderer blockRenderer: blockRenderers) {
            blockRenderer.draw(batch, parentAlpha);
        }
    }
}
