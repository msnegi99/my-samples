package com.msnegi.basicnestedrecyclerview.model;

import java.util.List;

public class Outer {
    private String name;
    private List<Inner> games;

    public Outer(String name, List<Inner> games) {
        this.name = name;
        this.games = games;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Inner> getGames() {
        return games;
    }

    public void setGames(List<Inner> games) {
        this.games = games;
    }
}
