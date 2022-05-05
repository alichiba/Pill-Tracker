package model;

import java.util.HashMap;

public class Day {
    private String name;
    private HashMap<String, Pill> map;

    public Day(String name) {
        this.name = name;
        this.map = new HashMap<>();
    }

    public HashMap<String, Pill> getMap() {
        return this.map;
    }

    public String getName() {
        return this.name;
    }
}
