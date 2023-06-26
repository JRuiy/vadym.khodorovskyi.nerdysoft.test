package test.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class User {
    private String name;
    private int age;

    private User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static User createUser(String name, int age) {
        return new User(name, age);
    }
}