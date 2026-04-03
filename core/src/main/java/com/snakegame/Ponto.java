package com.snakegame;

import java.util.Objects;

public class Ponto {
    public int x, y;

    public Ponto(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Ponto)) return false;
        Ponto p = (Ponto) o;
        return x == p.x && y == p.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}