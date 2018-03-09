package com.fepeprog.test.database;

/**
 * Created by fepeprog on 3/8/18.
 */

public class User {
    private String email;
    private String name;
    private String number;
    private String password;
    private int age;

    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.number = "null";
        this.age = 0;
    }

    public User(String email, String name, String number, String password, int age) {
        this.email = email;
        this.name = name;
        this.number = number;
        this.password = password;
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
