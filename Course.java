package model;
// Represents a Course in the educational system.
//  Manages course details, enrollment capacity, and student counts.

public class Course {

    // Core attributes of a course
    private String code ;
    private String name ;
    private int creditHours ;
    private int maxStudents ;
    private int currentStudents ;

    public Course(String code , String name , int creditHours , int maxStudents , int currentStudents) {
        this.code = code ;
        this.name = name ;
        this.creditHours = creditHours ;
        this.maxStudents = maxStudents ;
        this.currentStudents = currentStudents ;
    }
    public String getCode() {
        return code ;
    }
    public String getName() {
        return name ;
    }
    public int getCreditHours() {
        return creditHours ;
    }
    public int getMaxStudents() {
        return maxStudents ;
    }
    public int getCurrentStudents() {
        return currentStudents ;
    }

    //Increments the enrolled student count by 1.
    //Call this when a student successfully registers for the course.
    public void increaseStudents() {
        currentStudents++ ;
    }

    //Decrements the enrolled student count by 1.
    // Call this when a student drops the course.
    public void decreaseStudents() {
        currentStudents-- ;
    }

    //Checks if the course has reached its maximum enrollment capacity.
    public boolean isFull() {
        return currentStudents >= maxStudents ;
    }

    //Formats the course data into a clean, human-readable string.
    public String toString() {
        return code + "-" + name +"| Hours : " +creditHours + " | Students : " + currentStudents +"/" +maxStudents ;

    }
}