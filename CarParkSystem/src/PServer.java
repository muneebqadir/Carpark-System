import java.net.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.*;

public class PServer {
    public static void main(String[] args) throws IOException {

        ServerSocket PServerSocket = null;
        boolean listening = true;
        String PServerName = "Parking Server";
        int ActionServerNumber = 4545;
        double SharedVariable = 5;

        //Access date to create log
        java.util.Date date = java.util.Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss-"); 
        String strDate = dateFormat.format(date);      
        // Make the server socket
    
        try {
            PServerSocket = new ServerSocket(ActionServerNumber);
        } catch (IOException e) {
          System.err.println("Could not start " + PServerName + " specified port.");
          System.exit(-1);
        }
        System.out.println(PServerName + " started");

        //Create Log File

        CreateLog myFilLog = new CreateLog(strDate);
        //System.out.println("TEST Log file created");
        myFilLog.CFileRun();

        //Create the shared object in the global scope...
        
        SharedParkingState ourPStateObject = new SharedParkingState(SharedVariable,myFilLog);
    
        //Got to do this in the correct order with only four clients!  Can automate this...
        
        while (listening){
          new CParkThread(PServerSocket.accept(), "EntryGate1", ourPStateObject,myFilLog).start();
          new CParkThread(PServerSocket.accept(), "EntryGate2", ourPStateObject,myFilLog).start();
          new CParkThread(PServerSocket.accept(), "ExitGate3", ourPStateObject,myFilLog).start();
          new CParkThread(PServerSocket.accept(), "ExitGate4", ourPStateObject,myFilLog).start();
          //System.out.println("New " + PServerName + " thread started.");
        }
        PServerSocket.close();
      }
}
