package model;
import java.util.* ;

public class Student extends User implements Registrable{

    // Holds the list of course codes the student is currently enrolled in

    private ArrayList<String> registeredCourses ;
    public Student(int id , String name , String password) {
        super(id , name , password);

        // Initializes an empty list for registered courses.
        registeredCourses = new ArrayList<>() ;
    }
    public ArrayList<String> getRegisteredCourses() {
        return registeredCourses ;
    }

    // Adds a new course to the student's registered list.

    public void registerCourse(String courseCode) {
        registeredCourses.add(courseCode) ;
    }

    // Removes a course from the student's registered list.

    public void dropCourse(String courseCode) {
        registeredCourses.remove(courseCode) ;
    }
}