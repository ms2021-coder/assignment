package net.exercise.store;

import java.util.Collection;

import net.exercise.model.Person;

/**
 * Interface to be implemented by storage systems
 * that can get and search for person records.
 */
public interface PersonStore {
    /** 
     * Return the Person identified by id or null if not found.
     * 
     * @param id
     * @return Person
     */
    Person Get(String id);

    /** 
     * Returns a collection of matching Person records. This
     * can be an emtpy collection if there were no matches.
     * 
     * @param str
     * @return Collection<Person>
     */
    Collection<Person> Search(String str);
}
