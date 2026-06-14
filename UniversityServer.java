package Server;

import model.*;
import Service.*;
import Utils.*;
import java.net.*;
import java.util.*;

public class UniversityServer {
    public static void main(String[] args) {
        try {

            ArrayList<Student> students = FileManager.loadStudents("src/data/students.txt") ;
            ArrayList<Course> courses = FileManager.loadCourses("src/data/courses.txt") ;
            RegistrationService service = new RegistrationService(students , courses) ;

            ServerSocket serverSocket = new ServerSocket(5000) ;
            System.out.println("University Server Started..");

            // Infinite loop to continuously listen for and accept new client connections

            while (true) {

                // Blocks execution here until a client attempts to connect
                Socket socket = serverSocket.accept() ;
                System.out.println("New client connected..") ;

                ClientHandler handler = new ClientHandler(socket , service) ;

                // This prevents the server loop from freezing while talking to one user.
                new Thread(handler).start();
            }
        } catch (Exception e) {
            System.out.println("Server Error : " +e.getMessage()) ;
        }
    }
}