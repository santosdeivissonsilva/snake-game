package com.snakegame.teavm;

import com.github.xpenatan.gdx.teavm.backends.web.WebApplicationConfiguration;
import com.github.xpenatan.gdx.teavm.backends.web.WebApplication;
import com.snakegame.SnakeGame;

/**
 * Launches the TeaVM/HTML application.
 */
public class TeaVMLauncher {
    public static void main(String[] args) {
        WebApplicationConfiguration config = new WebApplicationConfiguration("canvas");
        config.width = SnakeGame.LARGURA;
        config.height = SnakeGame.ALTURA;
        new WebApplication(new SnakeGame(), config);
    }
}