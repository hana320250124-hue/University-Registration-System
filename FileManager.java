package Utils;
import model.* ;
import java.io.* ;
import java.util.* ;

public class FileManager {

    //Expected file format line: id,name,password
    //An ArrayList of loaded Student objects (empty if an error occurs)

    public static ArrayList<Student> loadStudents(String path) {
        ArrayList<Student> students = new ArrayList<>() ;

        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line ;

            // Read the file line by line until the end is reached

            while ((line = br.readLine()) != null) {

                // Split the comma-separated line into an array of strings
                String[] data = line.split(",") ;

                int id = Integer.parseInt(data[0]) ;
                String name = data[1] ;
                String password = data[2] ;

                students.add(new Student(id , name , password)) ;
            }

        }
        catch (Exception e) {
            System.out.println("Error loading students.");
        }
        return students ;
    }
    public static ArrayList<Course> loadCourses(String path) {
        ArrayList<Course> courses = new ArrayList<>() ;

        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line ;

            // Read the file line by line until the end is reached
            while ((line = br.readLine()) != null) {
                String[] data =line.split(",") ;

                String code = data[0] ;
                String name = data[1] ;
                int hours = Integer.parseInt(data[2]) ;
                int max = Integer.parseInt(data[3]) ;
                int current = Integer.parseInt(data[4]) ;

                courses.add(new Course(code , name , hours , max , current)) ;
            }

        }
        catch (Exception e) {
            System.out.println("Error loading courses.");
        }
        return courses ;
    }
    public static void saveRegistration(String path , int studentId , String courseCode) {

        //write new data from the client and keep the previous data without delete it
        try (PrintWriter pw = new PrintWriter(new FileWriter(path, true))) {
            pw.println(studentId + " , " +courseCode);
        }
        catch (Exception e) {
            System.out.println("Error saving registration.");
        }
    }
}