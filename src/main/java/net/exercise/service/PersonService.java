package net.exercise.service;

import java.util.ArrayList;
import java.util.Collection;

import com.google.inject.Inject;

import net.exercise.model.Person;
import net.exercise.store.PersonStore;

public class PersonService {
    private final PersonStore personStore;

    @Inject
    public PersonService(PersonStore personStore)
    {
        this.personStore = personStore;
    }
    
    /** 
     * Return the Person identified by id or null if not found.
     * 
     * @param id
     * @return Person
     */
    public Person Get(String id)
    {
        return this.personStore.Get(id);
    }

    /** 
     * Returns a collection of matching Person records. This
     * can be an emtpy collection if there were no matches.
     * 
     * @param str
     * @return Collection<Person>
     */
    public Collection<Person> Search(String str)
    {
        Collection<Person> results = this.personStore.Search(str);
        if (results == null)
        {
            results = new ArrayList<Person>();
        }

        return results;
    }    
}
