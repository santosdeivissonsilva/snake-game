# Snake Game

Jogo da cobrinha clássico em **Java** com **[libGDX](https://libgdx.com/)** e backend desktop **LWJGL3**.  
A classic **Snake** game in **Java** using **[libGDX](https://libgdx.com/)** and the **LWJGL3** desktop backend.

---

## Português

### O que é este projeto

É um Jogo de Cobrinha em grade **20×20** células: você controla uma cobra que se move em passos discretos (não é movimento contínuo livre). O objetivo é comer a comida, ganhar pontos e subir de nível sem bater nas paredes nem no próprio corpo.

### Como jogar

| Ação | Teclas |
|------|--------|
| Mover | **Setas** ou **W A S D** |
| Iniciar / jogar de novo (menu) | **Enter** |

- No **menu inicial**, pressione **Enter** para começar.
- **Game over** ao tocar a borda do tabuleiro ou qualquer parte do corpo da cobra.
- Após morrer, o menu mostra a pontuação e permite jogar novamente com **Enter**.

### Regras do jogo

1. **Pontuação**: cada comida vale `10 × nível atual` pontos.
2. **Recorde**: o melhor placar da sessão é mantido na parte superior (várias partidas até fechar o jogo).
3. **Níveis**: ao atingir `nível × 50` pontos, o nível sobe (até no máximo **10**). Com isso, a cobra **anda mais rápido** (o intervalo entre passos diminui).
4. **Área útil**: a grade ocupa a maior parte da janela; uma faixa no topo (**HUD**) exibe pontos, recorde e nível.

### Estrutura do código

| Módulo / classe | Função |
|-----------------|--------|
| **`lwjgl3`** | Aplicação desktop: janela, OpenGL, empacotamento JAR. Ponto de entrada: `Lwjgl3Launcher`. |
| **`core`** | Lógica compartilhada do jogo. |
| `SnakeGame` | Classe principal (`Game`): dimensões do grid (`COLUNAS`, `LINHAS`, `CELULA`), recursos compartilhados (`SpriteBatch`, `BitmapFont`, `ShapeRenderer`) e troca de telas. |
| `MenuScreen` | Tela de menu / fim de jogo. |
| `GameScreen` | Partida: cobra (`LinkedList<Ponto>`), comida, colisões, desenho e HUD. |
| `Ponto` | Par `(x, y)` no grid. |

### Requisitos

- **JDK 20** (conforme `sourceCompatibility` no `build.gradle`).
- Gradle via wrapper do projeto (`gradlew` / `gradlew.bat`).

### Como executar

Na raiz do repositório:

```bash
# Windows
gradlew.bat lwjgl3:run

# Linux / macOS
./gradlew lwjgl3:run
```

Para gerar o JAR executável:

```bash
# Windows
gradlew.bat lwjgl3:jar

# Linux / macOS
./gradlew lwjgl3:jar
```

O arquivo gerado fica em `lwjgl3/build/libs/`.

### Gradle (referência rápida)

- `build` — compila e monta artefatos de todos os projetos.
- `clean` — remove pastas `build`.
- `lwjgl3:run` — executa o jogo no desktop.
- `lwjgl3:jar` — gera o JAR da aplicação desktop.

Outras flags úteis: `--daemon`, `--offline`, `--refresh-dependencies`. Tarefas podem ser prefixadas com o módulo, por exemplo `core:clean`.

### Créditos

Projeto base em libGDX; gerado com a ferramenta [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

---

## English

### What this project is

A Snake game on a **20×20** cell grid: you control a snake that moves in discrete steps (not free continuous motion). The goal is to eat food, score points, and level up without hitting the walls or your own body.

### How to play

| Action | Keys |
|--------|------|
| Move | **Arrow keys** or **W A S D** |
| Start / play again (menu) | **Enter** |

- On the **main menu**, press **Enter** to start.
- **Game over** when you hit the board edge or any segment of the snake’s body.
- After death, the menu shows your score; press **Enter** to play again.

### Game rules

1. **Scoring**: each food item is worth `10 × current level` points.
2. **High score**: the best score in the current session is shown at the top (persists until you close the game).
3. **Levels**: when your score reaches `level × 50`, the level increases (capped at **10**). The snake **moves faster** (shorter time between steps).
4. **Playfield**: the grid fills most of the window; a strip at the top (**HUD**) shows score, high score, and level.

### Code layout

| Module / class | Role |
|----------------|------|
| **`lwjgl3`** | Desktop app: window, OpenGL, JAR packaging. Entry point: `Lwjgl3Launcher`. |
| **`core`** | Shared game logic. |
| `SnakeGame` | Main `Game` class: grid dimensions (`COLUNAS`, `LINHAS`, `CELULA`), shared assets (`SpriteBatch`, `BitmapFont`, `ShapeRenderer`), and screen switching. |
| `MenuScreen` | Menu / game-over screen. |
| `GameScreen` | Match: snake (`LinkedList<Ponto>`), food, collisions, rendering, HUD. |
| `Ponto` | Grid coordinate pair `(x, y)`. |

### Requirements

- **JDK 20** (as set by `sourceCompatibility` in `build.gradle`).
- Gradle via the project wrapper (`gradlew` / `gradlew.bat`).

### How to run

From the repository root:

```bash
# Windows
gradlew.bat lwjgl3:run

# Linux / macOS
./gradlew lwjgl3:run
```

To build the runnable JAR:

```bash
# Windows
gradlew.bat lwjgl3:jar

# Linux / macOS
./gradlew lwjgl3:jar
```

Output is under `lwjgl3/build/libs/`.

### Gradle (quick reference)

- `build` — compile and build all projects.
- `clean` — remove `build` directories.
- `lwjgl3:run` — run the desktop game.
- `lwjgl3:jar` — build the desktop application JAR.

Other useful flags: `--daemon`, `--offline`, `--refresh-dependencies`. Tasks can be scoped with a module prefix, e.g. `core:clean`.

### Credits

libGDX-based project; have been bootstrapped with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).
