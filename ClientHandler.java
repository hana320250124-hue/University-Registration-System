package Server;
import model.*;
import Service.*;
import java.io.* ;
import java.net.* ;

//Implements Runnable so it can run concurrently on its own independent thread,
// allowing the server to process requests from multiple students at the same time.

public class ClientHandler implements Runnable{
    private Socket socket ;
    private RegistrationService service ;
    private Student currentStudent ;

    public ClientHandler(Socket socket , RegistrationService service) {
        this.socket = socket ;
        this.service = service ;
    }
    public void run() {
        try {

            //Recieve the data from the client and make it as a text (buffer to read it line by line)
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;

            // Setting the second parameter to 'true' enables autoflush, sending data immediately
            //write message to the client
            PrintWriter output = new PrintWriter(socket.getOutputStream() , true) ;
            output.println("Welcome To University System");

            // Server continuously waits for requests from this client
            while (true) {

                // Read request sent by the client
                String request = input.readLine() ;
                if(request == null) {
                    break ;    // Client closed their side of the connection stream safely
                }
                String[] parts = request.split(",") ;

                switch (parts[0]) {
                    case "LOGIN" :

                        // Request format: LOGIN,username,password
                        currentStudent = service.login(parts[1] , parts[2]) ;
                        if (currentStudent != null) {
                            output.println("LOGIN_SUCCESS");
                        }
                        else {
                            output.println("LOGIN_FAILED");
                        }
                        break;

                    case "VIEW_COURSES" :

                        // Loops through the shared catalog and prints every course data line
                        for (Course course : service.getCourses()) {
                            output.println(course) ;  // Relies on Course.toString() formatting
                        }
                        output.println("END") ;
                        break;

                    case "REGISTER" :
                        String result = service.registerCourse(currentStudent , parts[1]) ;
                        output.println(result) ;
                        break;

                    case "DROP" :
                        String dropResult = service.dropCourse(currentStudent , parts[1]) ;
                        output.println(dropResult) ;
                        break;

                    case "MY_COURSES" :

                        // Sends a list of course codes registered exclusively by this user session
                        for (String course : currentStudent.getRegisteredCourses()) {
                            output.println(course);
                        }
                        output.println("END");
                        break;
                }
            }
            socket.close();  // Clean up network resources on loop exit
        }
        catch (Exception e) {
            System.out.println("Client disconnected.") ;
        }
    }

}