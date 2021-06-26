package com.bennyhuo.kotlin.update15.jvmrecord;

/**
 * Created by benny at 2021/6/18 8:06.
 */
public class JavaRecordSample {

    public record Person (String name, int age) {

        public Person {
            if (name == null) throw new IllegalArgumentException("name should not be null.");
            if (age < 0 || age > 100) throw new IllegalArgumentException("");
            count++;
        }

        public static int count = 0;

        public static int count() {
            return count;
        }

    }

    public static void main(String[] args) {
        var person = new Person("bennyhuo", 2);
        System.out.println(person);
        //var ktPerson = new KotlinPerson("bennyhuo", 10);
    }

}
