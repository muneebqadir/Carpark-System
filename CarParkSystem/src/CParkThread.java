
import java.net.*;
import java.io.*;

public class CParkThread extends Thread{

    private Socket actionSocket = null;
    private SharedParkingState mySharedParkingStateObject;
    private String myPServerThreadName;
    private double mySharedVariable;
    CreateLog myLogFil;
     
    //Setup the thread
        public CParkThread(Socket ActionSocket, String PServerThreadName, SharedParkingState SharedObject,CreateLog LogfrmServ) {
            
        //super(PServerThreadName);
        this.actionSocket = ActionSocket;
        mySharedParkingStateObject = SharedObject;
        myPServerThreadName = PServerThreadName;
        myLogFil = LogfrmServ;
      }
  
    public void run() {
      try {
        System.out.println(myPServerThreadName + "initialising.");
        PrintWriter out = new PrintWriter(actionSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(actionSocket.getInputStream()));
        String inputLine, outputLine;
        
        while ((inputLine = in.readLine()) != null) {
            // Get a lock first
            try { 
                mySharedParkingStateObject.acquireLock();  
                outputLine = mySharedParkingStateObject.processInput(myPServerThreadName, inputLine);
                out.println(outputLine);
                mySharedParkingStateObject.releaseLock();  
                myLogFil.WriteFile("Transaction Complete PSpace Updated \n");
            } 
            catch(InterruptedException e) {
                System.err.println("Failed to get lock when reading:"+e);
            }
        }
  
         out.close();
         in.close();
         actionSocket.close();
  
      } catch (IOException e) {
        System.out.println(myPServerThreadName+ " Client connection lost");
        myLogFil.WriteFile("Client Connection Lost."+" Orphan Tread terminated \n");
        //Code to delete transaction
      }
    }

}
