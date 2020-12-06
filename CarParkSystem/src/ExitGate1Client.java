import java.io.*;
import java.net.*;
public class ExitGate1Client {
    public static void main(String[] args) throws IOException {

        // Set up the socket, in and out variables

        Socket ExitClientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int PSocketNumber = 4545;
        String PServerName = "localhost";
        String ClientID = "ExitGate1";

        try {
            ExitClientSocket = new Socket(PServerName, PSocketNumber);
            out = new PrintWriter(ExitClientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(ExitClientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: "+ PSocketNumber);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;
        char UserInput;

        System.out.println("Initialised " + ClientID + " client and IO connections");
        
        // This is modified as it's the client that speaks first
        System.out.println("Please press E for car to exit and T to Terminate Client");
        while (true) {
            
            fromUser = stdIn.readLine();
            UserInput = ValidateUinput(fromUser);
            if(UserInput=='E'){
                System.out.println(ClientID + " sending message to ParkingServer");
                out.println("Car has Left");
                fromServer = in.readLine();
                System.out.println(ClientID + " received " + fromServer + " from ParkingServer");
            }
            else if (UserInput=='T'){
                System.out.println("Client Terminating");
                System.exit(1);
            }
            else{
                System.out.println("Invalid input only E or T (Single Characters) are permitted.");
            }
            
            
        }
            
        
       // Tidy up - not really needed due to true condition in while loop
      //  out.close();
       // in.close();
       // stdIn.close();
       // ActionClientSocket.close();
    }

    public static char ValidateUinput(String inp){
        char Uchoice = 'F';
        if(inp.length()==1 && inp !=null){
            inp = inp.toUpperCase();
            Uchoice = inp.charAt(0);
            if(Uchoice=='E' ||Uchoice=='T') {
                return Uchoice;
            }
        }
    return 'F';
    }
}
