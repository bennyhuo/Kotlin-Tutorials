package net.println.kt14;

/**
 * Created by benny on 12/17/16.
 */
public class PersonMain {
    public static void main(String... args) {
        Person person = new Person("benny", 27);
        System.out.println(person.getName() + " is " + person.age);
        person.setName("andy");
        person.age = 26;
        System.out.println(person.getName() + " is " + person.age);
    }
}
