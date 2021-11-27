package net.exercise.model;

/**
 * Immutable role record
 */
public class Role {
    private String id;
    private String name;


    public Role(String id, String name) {
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
