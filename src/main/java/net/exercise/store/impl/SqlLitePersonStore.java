package net.exercise.store.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import net.exercise.model.Movie;
import net.exercise.model.Person;
import net.exercise.model.Role;
import net.exercise.model.WorkedOn;
import net.exercise.store.PersonStore;
import net.exercise.store.PersonStoreException;

public class SqlLitePersonStore implements PersonStore {
    private static String CONN_STR = "jdbc:sqlite:data/movie-people.db";
    private Connection connection;

    public SqlLitePersonStore() {
        try {
            this.connection = DriverManager.getConnection(CONN_STR);
        } catch (SQLException ex) {

        }
    }

    @Override
    public Person Get(String id) {
        final String sqlPerson = "SELECT id, name FROM Person WHERE ID = ?";
        final String sqlWorkedOn = "SELECT wo.id, m.id as movie_id, m.name as movie_name, "
                + "r.id as role_id, r.name as role_name "
                + "FROM WorkedOn wo "
                + "JOIN Movie m ON m.id = wo.movie_id "
                + "JOIN Role r ON r.id = wo.role_id " 
                + "WHERE wo.person_id = ?";

        try {
            PreparedStatement personPS = this.connection.prepareStatement(sqlPerson);
            personPS.setString(1, id);

            ResultSet personRS = personPS.executeQuery();
            if (personRS.next()) {

                PreparedStatement workedOnPS = this.connection.prepareStatement(sqlWorkedOn);
                workedOnPS.setString(1, id);
                ResultSet workedOnRS = workedOnPS.executeQuery();

                return newPerson(personRS, workedOnRS);
            }
        } catch (SQLException ex) {
            throw new PersonStoreException("Error performing get", ex);
        }

        return null;
    }

    @Override
    public Collection<Person> Search(String str) {
        final String sqlPerson = "SELECT id, name FROM Person WHERE name LIKE ?";
        final String sqlWorkedOn = "SELECT wo.id, m.id as movie_id, m.name as movie_name, "
                + "r.id as role_id, r.name as role_name, wo.person_id " + "FROM WorkedOn wo "
                + "JOIN Movie m ON m.id = wo.movie_id " + "JOIN Role r ON r.id = wo.role_id "
                + "WHERE wo.person_id = ?";

        LinkedList<Person> matches = new LinkedList<Person>();

        try {
            // Get all matching Person records
            PreparedStatement personPS = this.connection.prepareStatement(sqlPerson);
            personPS.setString(1, '%' + str + '%');

            ResultSet personRS = personPS.executeQuery();

            while (personRS.next()) {
                String person_id = personRS.getString("id");

                PreparedStatement workedOnPS = this.connection.prepareStatement(sqlWorkedOn);
                workedOnPS.setString(1, person_id);
                ResultSet workedOnRS = workedOnPS.executeQuery();

                matches.add(newPerson(personRS, workedOnRS));
            }

            return matches;
        } catch (SQLException ex) {
            throw new PersonStoreException("Error performing search", ex);
        }
    }

    private Person newPerson(ResultSet personRS, ResultSet workedOnRS) throws SQLException {

        Collection<WorkedOn> workedOn = new LinkedList<WorkedOn>();
        while (workedOnRS.next()) {
            workedOn.add(newWorkedOn(workedOnRS));
        }

        String id = personRS.getString("id");
        String name = personRS.getString("name");

        return new Person(id, name, workedOn);
    }

    private WorkedOn newWorkedOn(ResultSet rs) throws SQLException {
        String movie_id = rs.getString("movie_id");
        String movie_name = rs.getString("movie_name");
        Movie m = new Movie(movie_id, movie_name);

        String role_id = rs.getString("role_id");
        String role_name = rs.getString("role_name");
        Role r = new Role(role_id, role_name);

        String workedOn_id = rs.getString("id");
        WorkedOn wo = new WorkedOn(workedOn_id, m, r);

        return wo;
    }
}
