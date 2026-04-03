package com.snakegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SnakeGame extends Game {

    // Recursos compartilhados entre telas
    public SpriteBatch     batch;
    public BitmapFont      font;
    public ShapeRenderer   shapes;

    public static final int COLUNAS    = 20;
    public static final int LINHAS     = 20;
    public static final int CELULA     = 32;  // pixels por célula
    public static final int LARGURA    = COLUNAS * CELULA;
    public static final int ALTURA     = LINHAS  * CELULA + 60;

    @Override
    public void create() {
        batch  = new SpriteBatch();
        font   = new BitmapFont();
        shapes = new ShapeRenderer();
        font.getData().setScale(1.4f);

        setScreen(new MenuScreen(this, false, 0));
    }

    @Override
    public void dispose() {
        shapes.dispose();
        batch.dispose();
        font.dispose();
    }
}