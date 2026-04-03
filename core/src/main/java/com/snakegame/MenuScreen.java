package com.snakegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

public class MenuScreen implements Screen {

    private final SnakeGame jogo;
    private final boolean   morreu;
    private final int       pontuacao;

    public MenuScreen(SnakeGame jogo, boolean morreu, int pontuacao) {
        this.jogo      = jogo;
        this.morreu    = morreu;
        this.pontuacao = pontuacao;
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        // Limpa com fundo escuro
        Gdx.gl.glClearColor(0.07f, 0.07f, 0.07f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Painel central semitransparente
        jogo.shapes.begin(ShapeRenderer.ShapeType.Filled);
        jogo.shapes.setColor(new Color(0f, 0f, 0f, 0.6f));
        jogo.shapes.rect(
            SnakeGame.LARGURA / 2f - 180,
            SnakeGame.ALTURA  / 2f - 100,
            360, 200
        );
        jogo.shapes.end();

        // Texto
        jogo.batch.begin();
        jogo.font.getData().setScale(2.2f);
        jogo.font.setColor(Color.WHITE);

        String titulo = morreu ? "Fim de jogo!" : "Snake";
        jogo.font.draw(jogo.batch, titulo,
            0, SnakeGame.ALTURA / 2f + 60,
            SnakeGame.LARGURA, Align.center, false);

        jogo.font.getData().setScale(1.3f);
        jogo.font.setColor(new Color(0.8f, 0.8f, 0.8f, 1f));

        if (morreu) {
            jogo.font.draw(jogo.batch, "Pontuacao: " + pontuacao,
                0, SnakeGame.ALTURA / 2f + 10,
                SnakeGame.LARGURA, Align.center, false);
        }

        jogo.font.draw(jogo.batch, "Pressione ENTER para jogar",
            0, SnakeGame.ALTURA / 2f - 30,
            SnakeGame.LARGURA, Align.center, false);

        jogo.batch.end();

        // Aguarda ENTER — troca de tela no próximo ciclo evita alocar GL no meio do render
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            final MenuScreen self = this;
            Gdx.app.postRunnable(() -> {
                jogo.setScreen(new GameScreen(jogo));
                self.dispose();
            });
        }
    }

    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {}
}
