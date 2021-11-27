package net.exercise.model;

import java.util.Collection;
import java.util.Collections;


/**
 * Immutable person record
 */
public class Person {
    private String id;
    private String name;
    private Collection<WorkedOn> workedOn;

    public Person(String id, String name, Collection<WorkedOn> workedOn) {
        this.id = id;
        this.name = name;
        this.workedOn = Collections.unmodifiableCollection(workedOn);
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }


    
    /** 
     * This collection is unmodifiable
     * @return Collection<WorkedOn>
     */
    public Collection<WorkedOn> getWorkedOn() {
        return this.workedOn;
    }
}
