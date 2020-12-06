import java.net.*;
import java.io.*;

public class PServer {
    public static void main(String[] args) throws IOException {

        ServerSocket PServerSocket = null;
        boolean listening = true;
        String PServerName = "Parking Server";
        int ActionServerNumber = 4545;
        
        double SharedVariable = 5;
    
        //Create the shared object in the global scope...
        
        SharedParkingState ourPStateObject = new SharedParkingState(SharedVariable);
            
        // Make the server socket
    
        try {
            PServerSocket = new ServerSocket(ActionServerNumber);
        } catch (IOException e) {
          System.err.println("Could not start " + PServerName + " specified port.");
          System.exit(-1);
        }
        System.out.println(PServerName + " started");
    
        //Got to do this in the correct order with only four clients!  Can automate this...
        
        while (listening){
          new CParkThread(PServerSocket.accept(), "PServerThread1", ourPStateObject).start();
          new CParkThread(PServerSocket.accept(), "PServerThread2", ourPStateObject).start();
          new CParkThread(PServerSocket.accept(), "PServerThread3", ourPStateObject).start();
          new CParkThread(PServerSocket.accept(), "PServerThread4", ourPStateObject).start();
          System.out.println("New " + PServerName + " thread started.");
        }
        PServerSocket.close();
      }
}
