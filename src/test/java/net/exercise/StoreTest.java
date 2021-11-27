package net.exercise;

import java.util.Collection;
import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.exercise.model.Person;
import net.exercise.model.WorkedOn;
import net.exercise.store.impl.SqlLitePersonStore;

public class StoreTest {
    private SqlLitePersonStore sqlLitePersonStore;

    @Before
    public void Setup() {
        this.sqlLitePersonStore = new SqlLitePersonStore();
    }

    @Test
    public void idTest() {
        Person p = sqlLitePersonStore.Get("1");
        Assert.assertEquals("Troy McClure", p.getName());

        p = sqlLitePersonStore.Get("1000");
        Assert.assertNull(p);
    }

    @Test
    public void searchTestWithResults() {
        Collection<Person> peeps = sqlLitePersonStore.Search("ca");
        Assert.assertNotNull(peeps);
        Assert.assertEquals(2, peeps.size());

        // Find person with id 2 in the results to verify
        // field values
        Person p2 = peeps.stream().filter(new Predicate<Person>() {
            @Override
            public boolean test(Person t) {
                return t.getId().equals("2");
            }
        }).findAny().orElse(null);
        Assert.assertNotNull(p2);
        Assert.assertEquals("Rainier Wolfcastle", p2.getName());

        //
        // Spot check person 2 movies
        //
        Assert.assertEquals(5, p2.getWorkedOn().size());
        WorkedOn wo = p2.getWorkedOn().stream().filter(new Predicate<WorkedOn>() {
            @Override
            public boolean test(WorkedOn t) {
                return t.getMovie().getId().equals("5");
            }
        }).findAny().orElse(null);

        Assert.assertNotNull(wo);
        Assert.assertEquals("McBain II", wo.getMovie().getName());
        Assert.assertEquals("1", wo.getRole().getId());
        Assert.assertEquals("starred", wo.getRole().getName());

        // Find person with id 4 in the results to verify
        // field values
        Person p4 = peeps.stream().filter(new Predicate<Person>() {
            @Override
            public boolean test(Person t) {
                return t.getId().equals("4");
            }
        }).findAny().orElse(null);

        Assert.assertNotNull(p4);
        Assert.assertEquals("Antonio Calculon, Sr", p4.getName());

        //
        // Spot check person 4 movies
        //
        Assert.assertEquals(1, p4.getWorkedOn().size());
        wo = p4.getWorkedOn().iterator().next();

        Assert.assertNotNull(wo);
        Assert.assertEquals("All My Circuits", wo.getMovie().getName());
        Assert.assertEquals("3", wo.getRole().getId());
        Assert.assertEquals("stars", wo.getRole().getName());
    }

    @Test
    public void searchTestNoResults() {
        Collection<Person> peeps = sqlLitePersonStore.Search("xyz-123");
        Assert.assertEquals(0, peeps.size());
    }
}