import java.io.*;
import java.net.*;
//import java.util.LinkedList;
//import java.util.Queue;

public class EntryGate1Client {

    public static String EntryClientClientID = "EntryGate1";
    //private static Queue<Integer> q = new LinkedList<>();
    private static int CarCount = 0;

    public static void main(String[] args) throws IOException {

        // Set up the socket, in and out variables

        Socket EntryClientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int SeverSocket = 4545;
        String ParkingServerName = "localhost";
        // String EntryClientClientID = "EntryGate1";
        char UserInput;

        try {
            EntryClientSocket = new Socket(ParkingServerName, SeverSocket);
            out = new PrintWriter(EntryClientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(EntryClientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + SeverSocket);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;
        String ToServer;

        System.out.println("Initialised " + EntryClientClientID + " client and IO connections");
        
        // This is modified as it's the client that speaks first
        System.out.println("Press N for new car or T to terminate client");
        while (true) {
            fromUser = stdIn.readLine();
            UserInput = ValidateUinput(fromUser);
            if(UserInput=='T'){
                System.out.println("Client Terminated");
                System.exit(1);
            }
            else if(UserInput=='N'){
               System.out.println("A new car has arrived");
               //System.out.println(EntryClientClientID + " sending message to ParkingServer");
               ++CarCount;
                out.println("Car has arrived");
                //      ORPHAN CHECK 
                //System.exit(1);
                fromServer = in.readLine();
                System.out.println(EntryClientClientID + " received " + fromServer + " from Parking Server");
                if(fromServer.equals("No Space car has been added to queue")){
                    System.out.println("Car waiting as there is no space availible.");
                }
                else if(fromServer.equals("Space availible allow car to enter")){
                System.out.println("Car has entered the car park");
                }
            }
            else{
                System.out.println("Invalid input only N or T (Single Characters) are permitted.");
            }
            
        }
            
        
       // Tidy up - not really needed due to true condition in while loop
      //  out.close();
       // in.close();
       // stdIn.close();
       // EntryClientSocket.close();
    }
    public static char ValidateUinput(String inp){
        char Uchoice = 'F';
        if(inp.length()==1 && inp !=null){
            inp = inp.toUpperCase();
            Uchoice = inp.charAt(0);
            if(Uchoice=='T' || Uchoice=='N' ) {
                return Uchoice;
            }
        }
    return 'F';
    }
    
//     public static void ServerMessageRecieve(String fromServer){
//         if(fromServer.equals("No Space add car to queue")){
//             q.add(CarCount);
//             System.out.println("Car waiting as there is no space availible. There are "+ q.size()+ " cars waiting at "+EntryClientClientID);
//         }
//         else if(fromServer.equals("Space availible allow car to enter")){
//             System.out.println("Car has entered the car park");
//         }
//     }
 }
