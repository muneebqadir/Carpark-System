import java.io.*;
import java.net.*;
public class ExitGate2Client {
    public static void main(String[] args) throws IOException {

        // Set up the socket, in and out variables

        Socket ExitClientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int PSocketNumber = 4545;
        String PServerName = "localhost";
        String ClientID = "ExitGate2";

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

        System.out.println("Initialised " + ClientID + " client and IO connections");
        
        // This is modified as it's the client that speaks first

        while (true) {
            
            fromUser = stdIn.readLine();
            if (fromUser != null) {
                System.out.println(ClientID + " sending " + fromUser + " to ActionServer");
                out.println(fromUser);
            }
            fromServer = in.readLine();
            System.out.println(ClientID + " received " + fromServer + " from ActionServer");
        }
            
        
       // Tidy up - not really needed due to true condition in while loop
      //  out.close();
       // in.close();
       // stdIn.close();
       // ActionClientSocket.close();
    }
}
