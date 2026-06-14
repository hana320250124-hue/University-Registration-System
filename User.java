package model;

// Demonstrates: Abstraction, Encapsulation, Inheritance base.

public abstract class User {

    // Encapsulated fields — accessible only via getters

    protected int id ;
    protected String name ;
    protected String password ;

    public User(int id , String name , String password) {

        // Constructor to initialize a user with id, name, and password.
        // Called by subclasses via super().

        this.id = id ;
        this.name = name ;
        this.password = password ;
    }

    // Getter for student ID

    public int getId() {
        return id ;
    }

    // Getter for name — used in login authentication

    public String getName() {
        return name ;
    }

    // Getter for password — used in login authentication

    public String getPassword() {
        return password ;
    }
}