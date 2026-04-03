package com.snakegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

import java.util.LinkedList;
import java.util.Random;

public class GameScreen implements Screen {

    // --- Referências ---
    private final SnakeGame jogo;

    // --- Grid ---
    private static final int C = SnakeGame.COLUNAS;
    private static final int L = SnakeGame.LINHAS;
    private static final int T = SnakeGame.CELULA;
    private static final int HUD = 60; // altura do HUD em pixels

    // --- Cores ---
    private static final Color COR_FUNDO  = new Color(0.067f, 0.067f, 0.067f, 1f);
    private static final Color COR_GRADE  = new Color(1f, 1f, 1f, 0.04f);
    private static final Color COR_CABECA = new Color(0.114f, 0.624f, 0.459f, 1f);
    private static final Color COR_CORPO  = new Color(0.365f, 0.792f, 0.647f, 1f);
    private static final Color COR_COMIDA = new Color(0.886f, 0.294f, 0.290f, 1f);
    private static final Color COR_HUD    = new Color(0.12f, 0.12f, 0.12f, 1f);

    // --- Estado ---
    private LinkedList<Ponto> cobra;
    private Ponto  comida;
    private int    dirX = 1, dirY = 0;
    private int    prDirX = 1, prDirY = 0;
    private int    pontos = 0;
    private int    nivel  = 1;
    private float  acumulador = 0f;
    private boolean rodando = true;
    private Random  random  = new Random();

    private static int recorde = 0;

    public GameScreen(SnakeGame jogo) {
        this.jogo = jogo;
        iniciar();
    }

    private void iniciar() {
        cobra = new LinkedList<>();
        cobra.add(new Ponto(10, 10));
        cobra.add(new Ponto(9,  10));
        cobra.add(new Ponto(8,  10));
        dirX = 1; dirY = 0;
        prDirX = 1; prDirY = 0;
        pontos = 0; nivel = 1;
        gerarComida();
        rodando = true;
        acumulador = 0f;
    }

    private void gerarComida() {
        Ponto c;
        do {
            c = new Ponto(random.nextInt(C), random.nextInt(L));
        } while (cobra.contains(c));
        comida = c;
    }

    /** Intervalo entre passos em segundos (diminui com o nível) */
    private float intervalo() {
        return Math.max(0.06f, 0.15f - nivel * 0.015f);
    }

    @Override
    public void render(float delta) {
        processarEntrada();

        if (rodando) {
            acumulador += delta;
            while (acumulador >= intervalo()) {
                acumulador -= intervalo();
                passo();
            }
        }

        desenhar();
    }

    private void processarEntrada() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)    && dirY != -1) { prDirX=0; prDirY=1; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)  && dirY !=  1) { prDirX=0; prDirY=-1; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)  && dirX !=  1) { prDirX=-1; prDirY=0; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && dirX != -1) { prDirX=1; prDirY=0; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)     && dirY != -1) { prDirX=0; prDirY=1; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)     && dirY !=  1) { prDirX=0; prDirY=-1; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)     && dirX !=  1) { prDirX=-1; prDirY=0; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)     && dirX != -1) { prDirX=1; prDirY=0; }
    }

    private void passo() {
        dirX = prDirX;
        dirY = prDirY;

        Ponto cabeca = cobra.getFirst();
        Ponto nova   = new Ponto(cabeca.x + dirX, cabeca.y + dirY);

        // Colisão com paredes
        if (nova.x < 0 || nova.x >= C || nova.y < 0 || nova.y >= L) {
            gameOver(); return;
        }
        // Colisão com o próprio corpo
        if (cobra.contains(nova)) {
            gameOver(); return;
        }

        cobra.addFirst(nova);

        if (nova.equals(comida)) {
            pontos += 10 * nivel;
            if (pontos > recorde) recorde = pontos;
            gerarComida();
            if (pontos >= nivel * 50) {
                nivel = Math.min(nivel + 1, 10);
            }
        } else {
            cobra.removeLast();
        }
    }

    private void gameOver() {
        rodando = false;
        final int p = pontos;
        final GameScreen self = this;
        Gdx.app.postRunnable(() -> {
            jogo.setScreen(new MenuScreen(jogo, true, p));
            self.dispose();
        });
    }

    private void desenhar() {
        Gdx.gl.glClearColor(0.12f, 0.12f, 0.12f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        desenharHUD();
        desenharTabuleiro();
        desenharComida();
        desenharCobra();
        desenharTextoHUD();
    }

    private void desenharHUD() {
        jogo.shapes.begin(ShapeRenderer.ShapeType.Filled);
        jogo.shapes.setColor(COR_HUD);
        jogo.shapes.rect(0, SnakeGame.ALTURA - HUD, SnakeGame.LARGURA, HUD);
        jogo.shapes.end();
    }

    private void desenharTabuleiro() {
        jogo.shapes.begin(ShapeRenderer.ShapeType.Filled);
        jogo.shapes.setColor(COR_FUNDO);
        jogo.shapes.rect(0, 0, SnakeGame.LARGURA, SnakeGame.ALTURA - HUD);
        jogo.shapes.end();

        jogo.shapes.begin(ShapeRenderer.ShapeType.Line);
        jogo.shapes.setColor(COR_GRADE);
        for (int c = 0; c <= C; c++)
            jogo.shapes.line(c * T, 0, c * T, SnakeGame.ALTURA - HUD);
        for (int l = 0; l <= L; l++)
            jogo.shapes.line(0, l * T, SnakeGame.LARGURA, l * T);
        jogo.shapes.end();
    }

    private void desenharComida() {
        jogo.shapes.begin(ShapeRenderer.ShapeType.Filled);
        jogo.shapes.setColor(COR_COMIDA);
        // Círculo centralizado na célula
        float cx = comida.x * T + T / 2f;
        float cy = comida.y * T + T / 2f;
        jogo.shapes.circle(cx, cy, T / 2f - 3);
        // Brilho branco pequeno
        jogo.shapes.setColor(1f, 1f, 1f, 0.8f);
        jogo.shapes.circle(cx - 3, cy + 3, 3);
        jogo.shapes.end();
    }

    private void desenharCobra() {
        jogo.shapes.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < cobra.size(); i++) {
            Ponto p = cobra.get(i);
            jogo.shapes.setColor(i == 0 ? COR_CABECA : COR_CORPO);
            float margem = i == 0 ? 2f : 3f;
            jogo.shapes.rect(
                p.x * T + margem,
                p.y * T + margem,
                T - margem * 2,
                T - margem * 2
            );
        }
        jogo.shapes.end();

        // Olhos da cabeça
        Ponto cab = cobra.getFirst();
        jogo.shapes.begin(ShapeRenderer.ShapeType.Filled);
        jogo.shapes.setColor(Color.WHITE);
        float ox = cab.x * T + T / 2f;
        float oy = cab.y * T + T / 2f;
        // Posiciona os olhos de acordo com a direção
        float ex1 = ox + dirY * 4 - dirX * 2;
        float ey1 = oy - dirX * 4 - dirY * 2;
        float ex2 = ox - dirY * 4 - dirX * 2;
        float ey2 = oy + dirX * 4 - dirY * 2;
        if (dirX != 0) {
            ex1 = ox + dirX * 5; ey1 = oy + 4;
            ex2 = ox + dirX * 5; ey2 = oy - 4;
        } else {
            ex1 = ox + 4; ey1 = oy + dirY * 5;
            ex2 = ox - 4; ey2 = oy + dirY * 5;
        }
        jogo.shapes.circle(ex1, ey1, 2.5f);
        jogo.shapes.circle(ex2, ey2, 2.5f);
        jogo.shapes.setColor(Color.BLACK);
        jogo.shapes.circle(ex1, ey1, 1.2f);
        jogo.shapes.circle(ex2, ey2, 1.2f);
        jogo.shapes.end();
    }

    private void desenharTextoHUD() {
        int base = SnakeGame.ALTURA - HUD;
        jogo.batch.begin();

        // Rótulos
        jogo.font.getData().setScale(0.85f);
        jogo.font.setColor(0.6f, 0.6f, 0.6f, 1f);
        jogo.font.draw(jogo.batch, "PONTOS",  20,  base + 52);
        jogo.font.draw(jogo.batch, "RECORDE", SnakeGame.LARGURA / 2f - 40, base + 52);
        jogo.font.draw(jogo.batch, "NIVEL",   SnakeGame.LARGURA - 90, base + 52);

        // Valores
        jogo.font.getData().setScale(1.5f);
        jogo.font.setColor(Color.WHITE);
        jogo.font.draw(jogo.batch, String.valueOf(pontos), 20, base + 28);
        jogo.font.draw(jogo.batch, String.valueOf(recorde),
            0, base + 28, SnakeGame.LARGURA, Align.center, false);
        jogo.font.draw(jogo.batch, String.valueOf(nivel),
            0, base + 28, SnakeGame.LARGURA - 10, Align.right, false);

        jogo.batch.end();
    }

    @Override public void show() {}
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {}
}