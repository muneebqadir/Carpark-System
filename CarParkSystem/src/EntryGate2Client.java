import java.io.*;
import java.net.*;
public class EntryGate2Client {
    public static void main(String[] args) throws IOException {

        // Set up the socket, in and out variables

        Socket EntryClientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int ActionSocketNumber = 4545;
        String ActionServerName = "localhost";
        String EntryClientClientID = "EntryGate2";

        try {
            EntryClientSocket = new Socket(ActionServerName, ActionSocketNumber);
            out = new PrintWriter(EntryClientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(EntryClientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: "+ ActionSocketNumber);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        System.out.println("Initialised " + EntryClientClientID + " client and IO connections");
        
        // This is modified as it's the client that speaks first

        while (true) {
            
            fromUser = stdIn.readLine();
            
            if (fromUser != null) {
                System.out.println(EntryClientClientID + " sending " + fromUser + " to ActionServer");
                out.println(fromUser);
            }
            fromServer = in.readLine();
            System.out.println(EntryClientClientID + " received " + fromServer + " from ActionServer");
        }
            
        
       // Tidy up - not really needed due to true condition in while loop
      //  out.close();
       // in.close();
       // stdIn.close();
       // ActionClientSocket.close();
    }
    
    // public static char ValidateUinput(String inp){
    //     char Uchoice = 'F';
    //     if(inp.length()==1 && inp !=null){
    //         inp = inp.toUpperCase();
    //         Uchoice = inp.charAt(0);
    //         if(Uchoice=='E' || Uchoice=='N' ) {
    //             return Uchoice;
    //         }
    //     }
    // return 'F';
}
