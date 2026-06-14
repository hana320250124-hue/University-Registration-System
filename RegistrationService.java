package Service;
import model.* ;
import java.util.* ;

public class RegistrationService {
    private ArrayList<Student> students ;
    private ArrayList<Course> courses ;

    public RegistrationService(ArrayList<Student> students , ArrayList<Course> courses) {
        this.students = students ;
        this.courses = courses ;
    }
    public Student login(String name , String password) {
        for(Student student : students) {

            // Check if both name and password match exactly

            if(student.getName().equals(name) && student.getPassword().equals(password)) {
                return student ;
            }
        }
        return null ;
    }
    public ArrayList<Course> getCourses() {
        return courses ;
    }

    //polymorphism
    public String registerCourse(Student student , String courseCode) {
        for(Course course : courses) {

            // Case-insensitive comparison allows users flexibility
            if(course.getCode().equalsIgnoreCase(courseCode)) {

                //Validate capacity limit
                if(course.isFull()) {
                    return "Course is full" ;
                }
                if(student.getRegisteredCourses().contains(courseCode)) {
                    return "Course already registered" ;
                }

                // If rules pass, update both sides of the relationship (Student and Course)
                student.registerCourse(courseCode);
                course.increaseStudents();
                return "Course registered successfully" ;
            }
        }
        return "Course not found" ;
    }
    public String dropCourse(Student student , String courseCode) {
        if(!student.getRegisteredCourses().contains(courseCode)) {
            return "You are not registered in this course" ;
        }

        // Remove the course from the student's personal tracking list
        student.dropCourse(courseCode);
        for (Course course : courses) {
            if(course.getCode().equalsIgnoreCase(courseCode)) {
                course.decreaseStudents();
            }
        }
        return "Course dropped successfully" ;
    }
}