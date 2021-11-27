package net.exercise.model;

/**
 * Immutable workedon record
 */
public class WorkedOn {
    private String id;
    private Role role;
    private Movie movie;

    public WorkedOn(String id, Movie movie, Role role) {
        this.id = id;
        this.role = role;
        this.movie = movie;
    }

    public String getId() {
        return this.id;
    }

    public Movie getMovie() {
        return this.movie;
    }

    public Role getRole() {
        return this.role;
    }
}
