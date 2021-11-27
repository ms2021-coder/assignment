package net.exercise.model;

/**
 * Immutable movie record
 */
public class Movie {
    private String id;
    private String name;

    public Movie(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
